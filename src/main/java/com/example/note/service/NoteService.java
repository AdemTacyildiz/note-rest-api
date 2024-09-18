package com.example.note.service;

import com.example.note.model.Note;
import com.example.note.model.User;
import com.example.note.repository.NoteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class NoteService {



    @Autowired
    private NoteRepository noteRepository;


    public Note save(Note note) {

        return noteRepository.save(note);
    }

    public Note findById(Long id) {

        return noteRepository.findById(id).orElse(null);
    }

    public List<Note> getNotesByUser(User user) {

        return noteRepository.findByUser(user);
    }

    public void delete(Long id) {

        noteRepository.deleteById(id);
    }
}

