package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
//            log.error("Error occurred for {} : ", user.getUserName(), e);
            log.trace("helooooooooo");
            log.debug("helooooooooo");
            log.info("helooooooooo");
            log.warn("helooooooooo");
            log.error("helooooooooo");
        }
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId Id){
        return userRepository.findById(Id);
    }

    public void deleteById(ObjectId Id){
        userRepository.deleteById(Id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
}
