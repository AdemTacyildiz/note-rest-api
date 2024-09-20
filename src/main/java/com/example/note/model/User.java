package com.example.note.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {

    /*
    * @Entity: Bu anotasyon, sınıfın bir JPA entity olduğunu ve bir veritabanı tablosuna karşılık geldiğini belirtir.
    * @Table(name = "users"): Veritabanındaki tablonun adını belirtir. Bu durumda, User sınıfı users tablosuna karşılık gelir.
    *
    * @Column(name = "username", unique = true, nullable = false): username alanının veritabanındaki tablodaki adını belirtir
    * ve bu alanın benzersiz olmasını (unique = true) ve boş olamayacağını (nullable = false) ifade eder.
    *
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;


    @Column(name = "password", nullable = false)
    private String password;

    // Boş constructor
    public User() {
    }

    public User( Long id,String username){
        this.username = username;
    }

    // Parametreli constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter ve Setter metotları
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
