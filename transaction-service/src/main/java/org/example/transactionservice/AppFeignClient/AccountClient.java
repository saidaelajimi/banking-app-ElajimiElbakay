package org.example.transactionservice.AppFeignClient;

import org.example.transactionservice.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "http://localhost:8081")
public interface AccountClient {

    @GetMapping("/accounts/{id}")
    Account getAccount(@PathVariable Long id);

    @PatchMapping("/accounts/{id}")
    Account updateAccount(@PathVariable Long id, @RequestBody Account account);
}