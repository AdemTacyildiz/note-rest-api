package com.example.note.controller;

import com.example.note.exception.NoteNotFoundException;
import com.example.note.exception.UnauthorizedAccessException;
import com.example.note.model.Note;
import com.example.note.model.User;
import com.example.note.service.NoteService;
import com.example.note.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/note")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;

    private final UserService userService;

    //Geçerli kullanıcıyı döndür
    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }

    @PostMapping
    public ResponseEntity<Object> createNote(@RequestBody Note note) {
        logger.info("Yeni not oluşturma isteği alındı.");

        User user = getCurrentUser();
        note.setUser(user);
        Note savedNote = noteService.save(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes() {
        logger.info("Kullanıcıya ait notları getirme isteği alındı.");

        User user = getCurrentUser();
        List<Note> notes = noteService.getNotesByUser(user);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNoteById(@PathVariable  Long id) {
        logger.info("Not bilgilerini getirme isteği alındı.");

        try {
            User user = getCurrentUser();
            Note note = noteService.findById(id);

            if (note != null) {
                throw new NoteNotFoundException("Not ID: " + id + "bulunamadı.");
            }
            if (!note.getUser().equals(user)) {
                throw new UnauthorizedAccessException("Bu nota erişim izniniz yok.");
            }
            return new ResponseEntity<>(note, HttpStatus.OK);

        }catch (NoteNotFoundException e) {
            return new ResponseEntity<>("Not bulunamadı", HttpStatus.NOT_FOUND);
        }catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>("Yetkisiz giriş", HttpStatus.UNAUTHORIZED);
        }  catch (Exception e) {
        return new ResponseEntity<>("Bir hata oluştu.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        logger.info("Not güncelleme isteği alındı.");

        try {
            User user = getCurrentUser();
            Note existingNote = noteService.findById(id);

            if (existingNote == null) {
                throw new NoteNotFoundException("Not ID: " + id + " bulunamadı.");
            }
            if (!existingNote.getUser().equals(user)) {
                throw new UnauthorizedAccessException("Bu nota erişim izniniz yok.");
            }

            existingNote.setBaslik(updatedNote.getBaslik());
            existingNote.setAciklama(updatedNote.getAciklama());
            Note savedNote = noteService.save(existingNote);

            return new ResponseEntity<>(savedNote, HttpStatus.OK);
        }catch (NoteNotFoundException e) {
            return new ResponseEntity<>("Not bulunamadı", HttpStatus.NOT_FOUND);
        }catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>("Yetkisiz giriş", HttpStatus.UNAUTHORIZED);
        }  catch (Exception e) {
            return new ResponseEntity<>("Bir hata oluştu.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
