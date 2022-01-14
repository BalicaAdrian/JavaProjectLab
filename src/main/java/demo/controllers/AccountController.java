package demo.controllers;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Appointment;
import demo.models.Doctor;
import demo.models.Patient;
import demo.services.AccountService;
import demo.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Account>> getAccounts() {
        List<Account> accounts = (List<Account>) accountService.findAll();
        if (!accounts.isEmpty()) {
            return ResponseEntity.ok(accounts);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/accounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAccount(@PathVariable("id") long id) {
        Account account;
        try{
            account = accountService.findById(id);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.ok(account);

    }

    @PostMapping(path = "/accounts")
    public ResponseEntity<Account> addAccount( @RequestBody Account account) {
        Account createdAccount = accountService.saveAccount(account);
        if(createdAccount == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping(path="/accounts/{id}")
    public ResponseEntity<Object> changeAccount(@PathVariable("id") long id, @RequestBody Account entity) {
        Optional<Account> updatedEntity;
        try{
            updatedEntity = accountService.updateAccountById(id, entity);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
            return ResponseEntity.noContent().build();

    }

}
