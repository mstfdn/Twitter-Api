package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.UserRepository;
import com.workintech.twitterapi.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Kullanıcıyı veritabanına ekleme metodu
    public void addUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        userRepository.save(user);
        System.out.println("Kullanıcı başarıyla eklendi.");
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public boolean authenticate(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

}