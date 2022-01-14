package demo;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Appointment;
import demo.models.Patient;
import demo.repository.AccountRepository;
import demo.repository.AppointmentRepository;
import demo.repository.PatientRepository;
import demo.services.AccountService;
import demo.services.AppointmentService;
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
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;


    @Test
    @DisplayName("Get Account when is NUll")
    public void testGetAccountNull() {

        long idApp = 1L;

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class,
                () -> appointmentService.findById(idApp));

        assertEquals(Exceptions.ErrorCode.APPOINTMENT_NOT_FOUND, exception.getError());
    }
    @Test
    @DisplayName("Get Account when is Not NUll")
    public void testGetAccountNotNull() {

        long idApp = 1L;
        Appointment app = new Appointment();
        app.setId(idApp);
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(app));

        Appointment appointmentResponse = appointmentService.findById(idApp);

        assertNotNull(appointmentResponse);
        assertEquals(app.getId(), appointmentResponse.getId());
    }

}