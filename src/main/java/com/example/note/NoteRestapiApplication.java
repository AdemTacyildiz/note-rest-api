package com.example.note;

import com.example.note.model.Note;
import com.example.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoteRestapiApplication  {

	public static void main(String[] args) {
		SpringApplication.run(NoteRestapiApplication.class, args);
	}
// implements CommandLineRunner
//	@Autowired
//	private NoteRepository noteRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//		Note note = new Note();
//		note.setKullaniciAdi("adoyaga");
//		note.setBaslik("Lorem");
//		note.setAciklama("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
//		noteRepository.save(note);
//
//		Note note1 = new Note();
//		note1.setKullaniciAdi("babayaga");
//		note1.setBaslik("merol");
//		note1.setAciklama("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
//		noteRepository.save(note1);
//	}
}
