package net.pao.laboratorpao.model.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.pao.laboratorpao.model.Account;
import net.pao.laboratorpao.model.PatchDto;
import net.pao.laboratorpao.service.AccountService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account-id/{id}") //http://localhost:8080/api/accounts/account-id/{id}
    public Optional<Account> getById(@PathVariable(name = "id") UUID id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/accounts") // http://localhost:8080/api/accounts/accounts
    public List<Account> getAllAcounts() {
        return accountService.getAccounts();
    }
    @PostMapping("/create-account")
    public void createObject(@RequestBody Account account) {
        accountService.addAccount(account);
    }

    @PutMapping("/update-account/{id}")
    public void updateObject(@PathVariable(name = "id") UUID id, @RequestBody Account account) {
        accountService.updateAccountById(id, account);
    }
    @DeleteMapping("/delete-account/{id}")
    public void deleteObjectById(@PathVariable(name = "id") UUID id) {
        accountService.deleteAccountById(id);
    }
    @PatchMapping("/partial-update-account/{id}")
    public void partiallyUpdateObject(@PathVariable(name = "id") UUID id, @RequestBody PatchDto accountDto) throws JsonProcessingException {
        accountService.partialUpdateAccountById(id, accountDto.getKey(), accountDto.getValue());
    }
}