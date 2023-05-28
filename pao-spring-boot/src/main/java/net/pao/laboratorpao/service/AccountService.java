package net.pao.laboratorpao.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.pao.laboratorpao.model.Account;
import net.pao.laboratorpao.model.Client;
import net.pao.laboratorpao.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getAccountById(UUID id) {
        Optional<Account> account =  accountRepository.findById(id);
        return account;
    }

    public void deleteAccountById(UUID id){
        accountRepository.deleteById(id);
    }

    public void updateAccountById(UUID id, Account acc){
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()) {
            Account updated_account = account.get();
            updated_account.setId(acc.getId());
            updated_account.setCreationDate(acc.getCreationDate());
            updated_account.setDisableDate(acc.getDisableDate());
            updated_account.setUpdatedDate(acc.getUpdatedDate());
            updated_account.setBalance(acc.getBalance());
            updated_account.setAlias(acc.getAlias());
            updated_account.setClient(acc.getClient());
            updated_account.setAccountNumber(acc.getAccountNumber());
            accountRepository.save(updated_account);
        }
    }

    public void addAccount(Account account){
        accountRepository.save(account);
    }
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public void partialUpdateAccountById(UUID id, String key, String value) throws JsonProcessingException {
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()) {
            Account updated_account = account.get();
            if (key.equalsIgnoreCase("id")) {
                updated_account.setId(UUID.fromString(value));
            } else if (key.equalsIgnoreCase("creationDate")) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                updated_account.setCreationDate(LocalDate.parse(value, formatter));
            } else if (key.equalsIgnoreCase("disableDate")) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                updated_account.setDisableDate(LocalDate.parse(value, formatter));
            } else if (key.equalsIgnoreCase("updateDate")) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                updated_account.setUpdatedDate(LocalDate.parse(value, formatter));
            } else if (key.equalsIgnoreCase("balance")) {
                updated_account.setBalance(new BigDecimal(value));
            } else if(key.equalsIgnoreCase("alias")){
                updated_account.setAlias(value);
            } else if(key.equalsIgnoreCase("client")){
                ObjectMapper mapper = new ObjectMapper();
                updated_account.setClient(mapper.readValue(value, Client.class));
            } else if(key.equalsIgnoreCase("accountNumber")){
                updated_account.setAccountNumber(new BigDecimal(value));
            }
            accountRepository.save(updated_account);
        }
    }

}