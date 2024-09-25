package com.example.note.controller;
import com.example.note.model.Note;
import com.example.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/note")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    public final NoteService noteService;


    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        logger.info("Yeni not oluşturma isteği alındı.");

        Note savedNote = noteService.save(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes() {
        logger.info("Kullanıcıya ait notları getirme isteği alındı.");

        List<Note> notes = noteService.getNotesByUser();

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        logger.info("Not bilgilerini getirme isteği alındı.");

        Note note = noteService.findById(id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        logger.info("Not güncelleme isteği alındı.");

        Note savedNote = noteService.updateNote(id, updatedNote);
        return new ResponseEntity<>(savedNote, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        logger.info("Not silme isteği alındı.");

        noteService.delete(id);
        return new ResponseEntity<>("Silindi.",HttpStatus.OK);
    }
}
