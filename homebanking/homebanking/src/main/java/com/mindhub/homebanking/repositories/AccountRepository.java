package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long>  {

    Optional<Account> findByNumber(String number);

    Optional<Account> findById(Long id);

}
