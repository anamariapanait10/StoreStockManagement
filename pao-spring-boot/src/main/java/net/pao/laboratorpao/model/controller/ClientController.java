package net.pao.laboratorpao.model.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.pao.laboratorpao.model.Client;
import net.pao.laboratorpao.model.PatchDto;
import net.pao.laboratorpao.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client-id/{id}") //http://localhost:8080/api/clients/client-id/{id}
    public Optional<Client> getById(@PathVariable(name = "id") UUID id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/clients") // http://localhost:8080/api/clients/clients
    public List<Client> getAllAcounts() {
        return clientService.getClients();
    }
    @PostMapping("/create-client")
    public void createObject(@RequestBody Client client) {
        clientService.addClient(client);
    }

    @PutMapping("/update-client/{id}")
    public void updateObject(@PathVariable(name = "id") UUID id, @RequestBody Client client) {
        clientService.updateClientById(id, client);
    }
    @DeleteMapping("/delete-client/{id}")
    public void deleteObjectById(@PathVariable(name = "id") UUID id) {
        clientService.deleteClientById(id);
    }
    @PatchMapping("/partial-update-client/{id}")
    public void partiallyUpdateObject(@PathVariable(name = "id") UUID id, @RequestBody PatchDto clientDto) throws JsonProcessingException {
        clientService.partialUpdateClientById(id, clientDto.getKey(), clientDto.getValue());
    }
}
