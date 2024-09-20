package com.example.note.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Gizli bir anahtar, JWT'nin imzalanması ve doğrulanması için kullanılır
    private final SecretKey secretKey;

    // Constructor'da güvenli bir anahtar oluşturuluyor
    public JwtUtil() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Güvenli anahtar oluşturuluyor
    }

    public String generateToken(String username) {
        // JWT'nin oluşturulma süreci
        return Jwts.builder()
                .setSubject(username)// Token'ın konusu (subject) kullanıcı adı olarak belirlenir
                .setIssuedAt(new Date())// Token'ın oluşturulma tarihi ayarlanır
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token geçerlilik süresi 10 saat
                .signWith(secretKey)// Token, gizli anahtar ile imzalanır
                .compact();// Token oluşturulur ve sıkıştırılır (compact), döndürülmeye hazır hale getirilir
    }

    public Claims extractClaims(String token) {
        // Token çözme işlemi
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        // Token'dan çıkarılan Claims'den kullanıcı adı elde edilir
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        // Token'dan çıkarılan Claims'den kullanıcı adı elde edilir
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        // Kullanıcı adı token'dan çıkarılan kullanıcı adıyla eşleşiyor mu ve token süresi dolmamış mı kontrol edilir
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
