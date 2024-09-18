package com.example.note.service;

import com.example.note.model.User;
import com.example.note.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {



    @Autowired
    private UserRepository userRepository;

    // 'PasswordEncoder', şifreleri güvenli bir şekilde şifrelemek için kullanılır. Şifreleme işlemlerini gerçekleştirmek için 'passwordEncoder' nesnesi otomatik olarak enjekte edilir.

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Bu metod, yeni bir kullanıcının kayıt edilmesini sağlar.
    // Kullanıcının şifresi, 'passwordEncoder' kullanılarak güvenli bir şekilde şifrelenir ve ardından şifrelenmiş şifre ile kullanıcı veritabanına kaydedilir.

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Bu metod, bir kullanıcının giriş yapmasını sağlar. Önce, 'username' ile veritabanından kullanıcı bulunur.
    // Eğer kullanıcı mevcutsa, girilen şifrenin şifrelenmiş hali veritabanındaki şifre ile karşılaştırılır.
    // Şifre doğruysa, kullanıcıyı geri döner. Eğer kullanıcı bulunamaz veya şifre hatalıysa 'null' döner.

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    // Bu metod, verilen 'username' (kullanıcı adı) ile veritabanından kullanıcıyı bulur ve döner. Eğer kullanıcı bulunamazsa 'null' döner.

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
