package org.example.transactionservice.AppFeignClient;
import org.example.transactionservice.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "http://localhost:8081")
public interface AccountClient {

    @GetMapping("/accounts/{accountId}")
    Account getAccount(@PathVariable("accountId") Long accountId);

    @PutMapping("/accounts/{accountId}")
    Account updateAccount(@PathVariable("accountId") Long accountId, @RequestBody Account account);
}