package com.deekshith.journal.controller;

import com.deekshith.journal.entity.User;
import com.deekshith.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userSerivce;

    // Get
    @GetMapping
    public List<User> getAllUsers() {
        return userSerivce.getAll();
    }

    // Post -- to create User
    @PostMapping
    public void createUser(@RequestBody User user) {
        userSerivce.saveUser(user);
    }

    // Put -- to update user
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User userInDb = userSerivce.findByUserName(userName);
        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userSerivce.saveUser(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
