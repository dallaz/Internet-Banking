package com.api.internetbanking.repository;

import com.api.internetbanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByNumeroConta(String numeroConta);
}
