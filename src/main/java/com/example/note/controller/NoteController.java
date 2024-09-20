package com.example.note.controller;

import com.example.note.model.Note;
import com.example.note.model.User;
import com.example.note.service.NoteService;
import com.example.note.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }

    @PostMapping
    public ResponseEntity<Object> createNote(@RequestBody Note note) {
        logger.info("Yeni not oluşturma isteği alındı.");
        User user = getCurrentUser();
        if (user != null) {
            note.setUser(user);
            Note savedNote = noteService.save(note);
            return  new ResponseEntity<>(savedNote, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Kullanıcı bulunamadı.", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<Object> getNotes() {
        logger.info("Kullanıcıya ait notları getirme isteği alındı.");
        User user = getCurrentUser();
        if (user != null) {
            List<Note> notes = noteService.getNotesByUser(user);
            return new ResponseEntity<>(notes, HttpStatus.OK);
        }
        return new ResponseEntity<>("Kullanıcı bulunamadı.", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNoteById(@PathVariable  Long id) {
        logger.info("Not bilgilerini getirme isteği alındı.");
        User user = getCurrentUser();
        if (user != null) {
            Note note = noteService.findById(id);

            if (note != null) {
                if (note.getUser().equals(user)) {
                    return new ResponseEntity<>(note, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Aranan id bulunamadı.", HttpStatus.FORBIDDEN);
                }
            }
            else{
                     return new ResponseEntity<>("Not bulunamadı.", HttpStatus.NOT_FOUND);
                }
            }
        return new ResponseEntity<>("Kullanıcı bulunamadı.", HttpStatus.UNAUTHORIZED);
        }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        logger.info("Not güncelleme isteği alındı.");

        User user = getCurrentUser();
        if (user != null) {
            Note existingNote = noteService.findById(id);
            if (existingNote != null) {
                if (existingNote.getUser().equals(user)) {
                    existingNote.setBaslik(updatedNote.getBaslik());
                    existingNote.setAciklama(updatedNote.getAciklama());
                    Note savedNote = noteService.save(existingNote);
                    return new ResponseEntity<>(savedNote, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Aranan id bulunamadı.", HttpStatus.FORBIDDEN);
                }
            }
            else{
                return new ResponseEntity<>("Not bulunamadı.", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("Kullanıcı bulunamadı.", HttpStatus.UNAUTHORIZED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNote(@PathVariable Long id) {
        logger.info("Not silme isteği alındı.");
        User user = getCurrentUser();
        if(user != null){
            Note existinNote = noteService.findById(id);
            if (existinNote != null) {
                if (existinNote.getUser().equals(user)) {

                    noteService.delete(id);
                    return new ResponseEntity<>("Not başarıyla silindi.", HttpStatus.OK);

                }
                else {
                    return new ResponseEntity<>("Aranan id bulunamadı.", HttpStatus.FORBIDDEN);
                }
            }else {
                return new ResponseEntity<>("Not bulunamadı.", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("Kullanıcı bulunamadı.", HttpStatus.UNAUTHORIZED);
    }
}
