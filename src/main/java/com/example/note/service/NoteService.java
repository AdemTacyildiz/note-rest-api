package com.example.note.service;

import com.example.note.exception.NoteNotFoundException;
import com.example.note.exception.UnauthorizedAccessException;
import com.example.note.model.Note;
import com.example.note.model.User;
import com.example.note.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.findByUsername(userDetails.getUsername());
    }


    public Note save(Note note) {
        User user = getCurrentUser();
        note.setUser(user);
        try {
            return noteRepository.save(note);
        } catch (Exception ex) {
            throw new RuntimeException("Not kayıt olurken hata oluştu.");
        }
    }

    public List<Note> getNotesByUser() {

        try {
            User user = getCurrentUser();
            return noteRepository.findByUser(user);
        } catch (Exception ex) {
            throw new NoteNotFoundException("Kullanıcıya ait not bulunamadı.");
        }
    }

    public Note findById(Long id) {
        User currentUser = getCurrentUser();
        return noteRepository.findById(id)
                .filter(note -> note.getUser().equals(currentUser))
                .orElseThrow(() -> new NoteNotFoundException("Bu not bulunamadı veya erişim yetkiniz yok. ID: " + id));
    }

    public Note updateNote(Long id, Note updatedNote) {
        Note existingNote = findById(id);
        User currentUser = getCurrentUser();

        if (!existingNote.getUser().equals(currentUser)) {
            throw new UnauthorizedAccessException("Bu notu güncelleme yetkiniz yok.");
        }

        existingNote.setBaslik(updatedNote.getBaslik());
        existingNote.setAciklama(updatedNote.getAciklama());

        try {
            return noteRepository.save(existingNote);
        } catch (Exception ex) {
            throw new RuntimeException("Not güncellenirken hata oluştu.");
        }
    }

    public void delete(Long id) {
        Note note = findById(id);
        User currentUser = getCurrentUser();

        if (!note.getUser().equals(currentUser)) {
            throw new UnauthorizedAccessException("Bu notu silme yetkiniz yok.");
        }

        try {
            noteRepository.delete(note);
        } catch (Exception ex) {
            throw new RuntimeException("Not silinirken hata oluştu.");
        }
    }
}
