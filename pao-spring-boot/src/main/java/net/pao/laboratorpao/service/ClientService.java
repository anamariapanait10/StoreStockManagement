package net.pao.laboratorpao.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.pao.laboratorpao.model.Account;
import net.pao.laboratorpao.model.Client;
import net.pao.laboratorpao.model.enums.ClientType;
import net.pao.laboratorpao.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> getClientById(UUID id) {
        Optional<Client> client =  clientRepository.findById(id);
        return client;
    }

    public void deleteClientById(UUID id){
        clientRepository.deleteById(id);
    }

    public void updateClientById(UUID id, Client cl){
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) {
            Client updated_client = client.get();
            updated_client.setId(cl.getId());
            updated_client.setCreationDate(cl.getCreationDate());
            updated_client.setDisableDate(cl.getDisableDate());
            updated_client.setUpdatedDate(cl.getUpdatedDate());
            updated_client.setFirstName(cl.getFirstName());
            updated_client.setLastName(cl.getLastName());
            updated_client.setYearOfBirth(cl.getYearOfBirth());
            updated_client.setPlaceOfBirth(cl.getPlaceOfBirth());
            updated_client.setPhoneNumber(cl.getPhoneNumber());
            updated_client.setEmail(cl.getEmail());
            updated_client.setClientType(cl.getClientType());
            updated_client.setAccounts(cl.getAccounts());
            clientRepository.save(updated_client);
        }
    }

    public void addClient(Client client){
        clientRepository.save(client);
    }
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void partialUpdateClientById(UUID id, String key, String value) throws JsonProcessingException {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) {
            Client updated_client = client.get();
            if (key.equalsIgnoreCase("id")) {
                updated_client.setId(UUID.fromString(value));
            } else if (key.equalsIgnoreCase("creationDate")) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                updated_client.setCreationDate(LocalDate.parse(value, formatter));
            } else if (key.equalsIgnoreCase("disableDate")) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                updated_client.setDisableDate(LocalDate.parse(value, formatter));
            } else if (key.equalsIgnoreCase("updateDate")) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                updated_client.setUpdatedDate(LocalDate.parse(value, formatter));
            } else if (key.equalsIgnoreCase("firstName")) {
                updated_client.setFirstName(value);
            } else if(key.equalsIgnoreCase("lastName")){
                updated_client.setLastName(value);
            }  else if (key.equalsIgnoreCase("yearOfBirth")) {
                updated_client.setYearOfBirth(Integer.parseInt(value));
            }  else if (key.equalsIgnoreCase("phoneNumber")) {
                updated_client.setPhoneNumber(Long.parseLong(value));
            } else if(key.equalsIgnoreCase("email")){
                updated_client.setEmail(value);
            } else if(key.equalsIgnoreCase("clientType")){
                updated_client.setClientType(ClientType.valueOf(value));
            } else if(key.equalsIgnoreCase("client")){
                ObjectMapper mapper = new ObjectMapper();
                updated_client.setAccounts(mapper.readValue(value, new TypeReference<List<Account>>() {}));
            }

            clientRepository.save(updated_client);
        }
    }

}