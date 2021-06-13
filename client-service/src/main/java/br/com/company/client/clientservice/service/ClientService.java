package br.com.company.client.clientservice.service;

import br.com.company.client.clientservice.ObjectUtils;
import br.com.company.client.clientservice.model.Client;
import br.com.company.client.clientservice.repository.ClientRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Page<Client> find(Client client, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues()
                .withIgnoreCase();
        Example<Client> exampleQuery = Example.of(client, matcher);
        return clientRepository.findAll(exampleQuery, pageable);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Client client) {
        Client clientToSave = clientRepository.findById(client.getId()).get();
        ObjectUtils.copyNonNullProperties(client, clientToSave);
        return clientRepository.save(clientToSave);
    }
}
