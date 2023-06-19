package com.api.internetbanking.controller;

import com.api.internetbanking.dto.ClientDTO;
import com.api.internetbanking.dto.TransactionRequestDTO;
import com.api.internetbanking.models.Client;
import com.api.internetbanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.findAllClients());
    }

    @GetMapping("{numeroConta}")
    public ResponseEntity<ClientDTO> getClientByNumeroConta(@PathVariable String numeroConta) {
        return ResponseEntity.ok(clientService.findByNumeroConta(numeroConta));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDto) {
        clientService.saveClient(clientDto);
        return ResponseEntity.ok(clientDto);
    }

    @PostMapping("/deposito/{numeroConta}")
    public ResponseEntity<Client> deposit(@PathVariable String numeroConta, @RequestBody TransactionRequestDTO valor) {
        Client client = clientService.deposit(numeroConta, valor);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/saque/{numeroConta}")
    public ResponseEntity<Client> withdraw(@PathVariable String numeroConta, @RequestBody TransactionRequestDTO valor) {
            Client client = clientService.withdraw(numeroConta, valor);
            return ResponseEntity.ok(client);
    }

}
