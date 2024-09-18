package com.example.note.model;

import jakarta.persistence.*;

@Entity // Bu sınıfın bir JPa entity olduğunu belirtir,yani bir veritabanı tablosuna karşılık gelir
@Table(name = "notes")// Bu entity'nin veritabanındaki tablo adının "notes" olduğunu belirtir
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

    // Boş constructor
    public Note() {
    }

    // Parametreli constructor
    public Note(User user, String baslik, String aciklama) {
        this.user = user;
        this.baslik = baslik;
        this.aciklama = aciklama;
    }

    // Getter ve Setter metotları
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
