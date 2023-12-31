package org.digit.exchange.controllers;

import java.util.Optional;

import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.models.User;
import org.digit.exchange.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/v1")
public class UserController {

    private final UserService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service){
        this.service = service;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody User user) {
        try {                
            User result = service.createUser(user);
            return ResponseEntity.ok(result);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving user", e);
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<Optional<User>> get(@RequestBody String userId) {
        try {
            Optional<User> result = service.getUserById(userId);
            return ResponseEntity.ok(result);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error retrieving user", e);
        }
    }
    
}
