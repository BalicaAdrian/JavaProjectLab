package demo;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Doctor;
import demo.models.Patient;
import demo.repository.AccountRepository;
import demo.repository.DoctorRepository;
import demo.repository.PatientRepository;
import demo.services.AccountService;
import demo.services.DoctorService;
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
public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;


    @Test
    @DisplayName("Get Account when is NUll")
    public void testGetAccountNull() {

        long idDoctor = 1L;

        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class,
                () -> doctorService.findById(idDoctor));

        assertEquals(Exceptions.ErrorCode.DOCTOR_NOT_FOUND, exception.getError());
    }
    @Test
    @DisplayName("Get Account when is Not NUll")
    public void testGetAccountNotNull() {

        long idDoctor = 1L;
        Doctor Doctor = new Doctor();
        Doctor.setId(idDoctor);
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(Doctor));

        Doctor DoctorResponse = doctorService.findById(idDoctor);

        assertNotNull(DoctorResponse);
        assertEquals(Doctor.getId(), DoctorResponse.getId());
    }

}