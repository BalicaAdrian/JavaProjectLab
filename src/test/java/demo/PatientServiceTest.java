package demo;

import demo.Exceptions.Exceptions;
import demo.models.Patient;
import demo.repository.PatientRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    private static Optional<Patient> patient;


    @BeforeAll
    public static void setup() {
        patient = Optional.of(new Patient(1L,"Adrian", "Balica", "adirancbalica@gmail.com", "parola", "07453490201", "20", 241,"MALE"));
    }

    @Test
    @DisplayName("Get Patient when is NUll")
    public void testGetPatientNull() {

        long idPatient = 1L;

        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class,
                () -> patientService.findById(idPatient));

        assertEquals(Exceptions.ErrorCode.PATIENT_NOT_FOUND, exception.getError());
    }
    @Test
    @DisplayName("Get Patient when is Not NUll")
    public void testGetPatientNotNull() {

        long idPatient = 1L;
        Patient patient = new Patient();
        patient.setId(idPatient);
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        Patient patientResponse = patientService.findById(idPatient);

        assertNotNull(patientResponse);
        assertEquals(patient.getId(), patientResponse.getId());
    }

    @Test
    @DisplayName("Get Patient when is Not NUll")
    public void savePatient() {

        Patient patient = new Patient();
        Patient saved = new Patient();
        when(patientService.savePatient(any())).thenReturn(saved);

        Patient result = patientService.savePatient(patient);

        assertNotNull(result);

    }

}