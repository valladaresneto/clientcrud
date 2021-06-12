package br.com.company.client.clientservice.controller;

import br.com.company.client.clientservice.model.Client;
import br.com.company.client.clientservice.service.ClientService;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/clients")
public class ClientsController {

    private final ClientService clientService;

    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Page<Client> find(Client client, Pageable pageable) {
        return clientService.find(client, pageable);
    }

    @PostMapping
    public Client save(@Valid @RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping
    public Client update(@Valid @RequestBody Client client) {
        return clientService.save(client);
    }
}
