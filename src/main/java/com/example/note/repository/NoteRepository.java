package com.example.note.repository;

import com.example.note.model.Note;
import com.example.note.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Bu arayüzün bir Spring Data repository olduğunu belirtir ve Spring'in bu arayüzü otomatik olarak implement etmesini sağlar
public interface NoteRepository extends JpaRepository<Note, Long> {

    // JpaRepository, CRUD işlemleri ve daha fazlası için gerekli metodları sağlar

    // Kullanıcıya ait notları bulmak için özel bir sorgu metodu tanımlar

    List<Note> findByUser(User user);
}
