package com.example.note.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity // Bu sınıfın bir JPa entity olduğunu belirtir,yani bir veritabanı tablosuna karşılık gelir
@Table(name = "notes")// Bu entity'nin veritabanındaki tablo adının "notes" olduğunu belirtir
@JsonIgnoreProperties({"user.password"})
public class Note {

    @Id // Bu alanın birincil anahtar olduğunu belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Bu alanın otomatik olarak artan bir değerle üretileceğini belirtir
    private Long id; // Notun benzersiz kimliği

    @ManyToOne // Her notun bir kullanıcıya ait olduduğunu belirtir
    @JoinColumn(name = "user_id") // Veritabanı tablosunda kullanıcı ile ilişkilendirilmiş kolonun adını belirtir
    private User user; // Notu ile ilişkilendirilen kullanıcı

    @Column(name = "baslik") // Bu alanın veritabanı tablosunda "baslik" olarak adlandırılacağını belirtir
    private String baslik; // Notun başlığı

    @Column(name = "aciklama")
    private String aciklama;

}
