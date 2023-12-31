package org.digit.exchange.service;

import java.util.List;
import java.util.Optional;

import org.digit.exchange.models.Account;
import org.digit.exchange.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // Create
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Read
    public Optional<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    // Update
    public Account updateAccount(Account account) {
        // Assuming the account has a valid ID
        return accountRepository.save(account);
    }

    // Delete
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }
    
}
