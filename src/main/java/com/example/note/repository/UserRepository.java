package com.example.note.repository;

import com.example.note.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Kullanıcı adını kullanarak bir kullanıcıyı bulmak için özel bir sorgu metodu tanımlar

    User findByUsername(String username);
}
