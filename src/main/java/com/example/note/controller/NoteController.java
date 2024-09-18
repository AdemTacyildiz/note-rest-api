package com.example.note.controller;

import com.example.note.model.Note;
import com.example.note.model.User;
import com.example.note.service.NoteService;
import com.example.note.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {


    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);


    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;


    @PostMapping
    public Note createNote(@RequestParam String username, @RequestBody Note note) {
        logger.info("Yeni not oluşturma isteği alındı.");
        User user = userService.findByUsername(username);
        if (user != null) {
            note.setUser(user);

            return noteService.save(note);
        }

        return null;
    }


    @GetMapping
    public List<Note> getNotes(@RequestParam String username) {
        logger.info("Kullanıcıya ait notları getirme isteği alındı.");
        User user = userService.findByUsername(username);
        if (user != null) {
            return noteService.getNotesByUser(user);
        }
        return null;
    }


    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id, @RequestParam String username) {
        logger.info("Not bilgilerini getirme isteği alındı.");

        User user = userService.findByUsername(username);
        if (user != null) {
            Note note = noteService.findById(id);
            if (note != null && note.getUser().equals(user)) {
                return note;
            }
        }
        return null;
    }

    // Bu metod, var olan bir notu güncellemek için PUT isteklerini karşılar.
    // 'username' ile doğrulama yapılır ve notun sahibi ile kullanıcı eşleşirse, notun başlık ve açıklama bilgileri güncellenir ve kaydedilir.
    // Kullanıcı notun sahibi değilse 'null' döner.

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestParam String username, @RequestBody Note updatedNote) {
        logger.info("Not güncelleme isteği alındı.");
        User user = userService.findByUsername(username);
        if (user != null) {
            Note existingNote = noteService.findById(id);
            if (existingNote != null && existingNote.getUser().equals(user)) {
                existingNote.setBaslik(updatedNote.getBaslik());
                existingNote.setAciklama(updatedNote.getAciklama());
                return noteService.save(existingNote);
            }
        }
        return null;
    }

    // Bu metod, bir notu silmek için DELETE isteklerini karşılar.
    // 'username' ile kullanıcı doğrulaması yapılır, ardından notun sahibi doğrulanırsa not silinir. Aksi takdirde işlem yapılmaz.

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id, @RequestParam String username) {
        logger.info("Not silme isteği alındı.");
        User user = userService.findByUsername(username);
        if (user != null) {
            Note existingNote = noteService.findById(id);
            if (existingNote != null && existingNote.getUser().equals(user)) {
                noteService.delete(id);
            }
        }
    }


}
