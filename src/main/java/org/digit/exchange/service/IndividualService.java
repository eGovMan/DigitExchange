package org.digit.exchange.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.digit.exchange.model.Individual;
import org.digit.exchange.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IndividualService implements UserDetailsService{


    @Autowired
    private PasswordEncoder passwordEncoder;

    private final IndividualRepository individualRepository;

    public IndividualService(IndividualRepository individualRepository){
        this.individualRepository = individualRepository;
    }
    
    // Create
    public Individual createIndividual(Individual individual) {
        //Generate PIN and set the PIN. Send SMS to phone specified.
        //TODO:For now hardcoding. This needs to be removed.
        individual.setPin("1111");
        //Encrypt the pin
        if(individual.getPin() != null && individual.getPin().isEmpty())
            individual.setPin(passwordEncoder.encode(individual.getPin()));
        //verify the individual
        return individualRepository.save(individual);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Individual individual = individualRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with individualname: " + id));

        return new org.springframework.security.core.userdetails.User(individual.getId(), individual.getPin(), getAuthority(individual));
    }

    private Collection<? extends GrantedAuthority> getAuthority(Individual individual) {
        return individual.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }

    // Read
    public Optional<Individual> getIndividualById(String id) {
        return individualRepository.findById(id);
    }

    // public List<User> getAllUsers() {
    //     return userRepository.findAll();
    // }

    // Update
    public Individual updateIndividual(Individual individual) {
        // Assuming the individual has a valid ID
        return individualRepository.save(individual);
    }

    // // Delete
    // public void deleteUser(String id) {
    //     userRepository.deleteById(id);
    // }
    
}
