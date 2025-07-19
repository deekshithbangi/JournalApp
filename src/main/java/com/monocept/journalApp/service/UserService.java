package com.monocept.journalApp.service;

import com.monocept.journalApp.entity.User;
import com.monocept.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User saveEntry(User user) {
        userRepository.save(user);
        return user;
    }

    public User findById(User user) {
        userRepository.findById(user.getId());
        return user;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }
}
