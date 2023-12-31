package org.digit.exchange.controllers;

import java.util.Optional;

import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.models.Account;
import org.digit.exchange.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account/v1")
public class AccountController {
    private final AccountService service;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(AccountService service){
        this.service = service;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account result = service.createAccount(account);
            return ResponseEntity.ok(result);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error saving identity", e);
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<Optional<Account>> get(@RequestBody String accountId) {
        try {
            Optional<Account> result = service.getAccountById(accountId);
            return ResponseEntity.ok(result);            
        } catch (ResourceNotFoundException e) {
            throw new CustomException("Error retrieving account", e);
        }
    }
    
}
