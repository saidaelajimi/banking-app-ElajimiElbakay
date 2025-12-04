package org.example.accountservice.repository;

import org.example.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
}