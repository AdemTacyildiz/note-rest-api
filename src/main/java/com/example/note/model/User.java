package com.example.note.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
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
}
