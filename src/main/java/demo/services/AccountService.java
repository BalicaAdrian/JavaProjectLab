package demo.services;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Patient;
import demo.repository.AccountRepository;
import demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private Account getAccount(String name) {
        Optional<Account> account = accountRepository.findByName(name);
        if (account.isEmpty()) {
            throw Exceptions.accountNotFound();
        }
        return account.get();
    }
    @Transactional
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()) {
            return account.get();
        } else {
            throw Exceptions.accountNotFound();
        }
    }
    @Transactional
    public Optional<Account> updateAccountById(long id, Account entity) {

        Optional<Account> existingAccount = accountRepository.findById(id);
        if(existingAccount.isPresent()) {
            existingAccount.get().update(entity);
            accountRepository.save(existingAccount.get());
            return existingAccount;
        } else {
            throw Exceptions.accountNotFound();
        }
    }
}
