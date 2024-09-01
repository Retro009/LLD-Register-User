package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public User signupUser(String name, String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent())
            throw new Exception("User already registered with the EmailId");
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        password = passwordEncoder.encode(password);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public boolean login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
            throw new Exception("User is not registered with us");
        User user = optionalUser.get();
        return passwordEncoder.matches(password, user.getPassword());
    }
}
