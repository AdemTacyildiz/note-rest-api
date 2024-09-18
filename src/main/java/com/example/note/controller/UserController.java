package com.example.note.controller;

import com.example.note.model.User;
import com.example.note.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;


    // Bu metod, '/register' yoluna yapılan POST isteklerini karşılar.
    // Gelen istek gövdesindeki ('@RequestBody') kullanıcı verilerini alır ve bu kullanıcıyı kaydetmek için 'userService.register()' metodunu çağırır.
    // Kayıt edilen kullanıcıyı döner.

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        logger.info("Kullanıcı kayıt isteği alındı.");
        return userService.register(user);
    }

    // Bu metod, '/login' yoluna yapılan POST isteklerini karşılar.
    // İstek parametreleri olarak kullanıcı adı ('username') ve şifre ('password') bekler.
    // 'userService.login()' metodunu çağırarak kullanıcı giriş işlemini gerçekleştirir.
    // Giriş başarılıysa kullanıcıyı döner, başarısızsa 'null' döner.

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        logger.info("Kullanıcı giriş isteği alındı.");
        return userService.login(username, password);
    }
}
