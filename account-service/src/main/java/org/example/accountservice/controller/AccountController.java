package org.example.accountservice.controller;


import lombok.*;
import org.example.accountservice.model.Account;
import org.example.accountservice.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account account) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        existing.setBalance(account.getBalance());
        existing.setOwner(account.getOwner());
        existing.setCurrency(account.getCurrency());

        return accountRepository.save(existing);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }
}
