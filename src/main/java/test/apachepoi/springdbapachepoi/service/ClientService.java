package test.apachepoi.springdbapachepoi.service;


import java.util.List;

import org.springframework.stereotype.Service;
import test.apachepoi.springdbapachepoi.model.Client;
import test.apachepoi.springdbapachepoi.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

