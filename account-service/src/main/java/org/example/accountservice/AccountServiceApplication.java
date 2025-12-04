package org.example.accountservice;

import org.example.accountservice.model.Account;
import org.example.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
    @Bean

    CommandLineRunner start(AccountRepository accountRepository) {
        return args -> {
            Account account1 = Account.builder()
                    .owner("Alice")
                    .balance(5000.0)
                    .currency("USD")
                    .build();

            Account account2 = Account.builder()
                    .owner("Bob")
                    .balance(3000.0)
                    .currency("EUR")
                    .build();

            accountRepository.save(account1);
            accountRepository.save(account2);
        };
    }
}
