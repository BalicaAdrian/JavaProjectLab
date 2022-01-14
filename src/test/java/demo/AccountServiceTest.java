package demo;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Patient;
import demo.repository.AccountRepository;
import demo.repository.PatientRepository;
import demo.services.AccountService;
import demo.services.PatientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;


    @Test
    @DisplayName("Get Account when is NUll")
    public void testGetAccountNull() {

        long idAccount = 1L;

        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class,
                () -> accountService.findById(idAccount));

        assertEquals(Exceptions.ErrorCode.ACCOUNT_NOT_FOUND, exception.getError());
    }
    @Test
    @DisplayName("Get Account when is Not NUll")
    public void testGetAccountNotNull() {

        long idAccount = 1L;
        Account account = new Account();
        account.setId(idAccount);
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));

        Account accountResponse = accountService.findById(idAccount);

        assertNotNull(accountResponse);
        assertEquals(account.getId(), accountResponse.getId());
    }

}