package demo;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Doctor;
import demo.models.Med;
import demo.models.Patient;
import demo.repository.AccountRepository;
import demo.repository.DoctorRepository;
import demo.repository.MedRepository;
import demo.repository.PatientRepository;
import demo.services.AccountService;
import demo.services.DoctorService;
import demo.services.MedService;
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
public class MedServiceTest {

    @InjectMocks
    private MedService medService;

    @Mock
    private MedRepository medRepository;


    @Test
    @DisplayName("Get Med when is NUll")
    public void testGetMedNull() {

        long idMed = 1L;

        when(medRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class,
                () -> medService.findById(idMed));

        assertEquals(Exceptions.ErrorCode.MED_NOT_FOUND, exception.getError());
    }
    @Test
    @DisplayName("Get Med when is Not NUll")
    public void testGetMedNotNull() {

        long idMed = 1L;
        Med med = new Med();
        med.setId(idMed);
        when(medRepository.findById(anyLong())).thenReturn(Optional.of(med));

        Med medResponse = medService.findById(idMed);

        assertNotNull(medResponse);
        assertEquals(med.getId(), medResponse.getId());
    }

}