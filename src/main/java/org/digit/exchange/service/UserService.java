package org.digit.exchange.service;

import java.util.List;
import java.util.Optional;

import org.digit.exchange.models.User;
import org.digit.exchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    // @Autowired
    // private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    // Create
    public User createUser(User user) {
        // //Encrypt the pin
        // if(user.getPin() != null && user.getPin().isEmpty())
        //     user.setPin(passwordEncoder.encode(user.getPin()));
        //verify the user
        return userRepository.save(user);
    }

    // Read
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update
    public User updateUser(User user) {
        // Assuming the user has a valid ID
        return userRepository.save(user);
    }

    // Delete
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    
}
