package org.digit.exchange.service;

import org.digit.exchange.constant.Role;
import org.digit.exchange.model.Individual;
import org.digit.exchange.repository.IndividualRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collection;


@Service
public class AdminInitializationService {

    private final IndividualRepository individualRepository; // Assuming you have a repository
    private final PasswordEncoder passwordEncoder;

    public AdminInitializationService(IndividualRepository individualRepository, PasswordEncoder passwordEncoder) {
        this.individualRepository = individualRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void initializeAdmin() {
        // Check if admin exists
        boolean adminExists = individualRepository.existsById("admin"); // Adjust based on your ID type

        if (!adminExists) {
            // Generate a 4-digit PIN
            String pin = generateRandomPin();

            Individual admin = new Individual();
            admin.setId("admin");
            admin.setName("Administrator");
            admin.setPin(passwordEncoder.encode(pin));

            Collection<Role> roles = new ArrayList<Role>();
            roles.add(Role.ADMIN);
            admin.setRoles(roles);

            individualRepository.save(admin); // Save to the database

            // Output to console
            System.out.println("Admin initialized with PIN: " + pin);
        }
    }

    private String generateRandomPin() {
        Random random = new Random();
        int num = 1000 + random.nextInt(9000);
        return String.valueOf(num);
    }
}
