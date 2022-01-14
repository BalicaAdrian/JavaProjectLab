package demo;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Disease;
import demo.models.Patient;
import demo.repository.AccountRepository;
import demo.repository.DiseaseRepository;
import demo.repository.PatientRepository;
import demo.services.AccountService;
import demo.services.DiseaseService;
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
public class DiseaseServiceTest {

    @InjectMocks
    private DiseaseService diseaseService;

    @Mock
    private DiseaseRepository diseaseRepository;


    @Test
    @DisplayName("Get Account when is NUll")
    public void testGetAccountNull() {

        long idDisease = 1L;

        when(diseaseRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class,
                () -> diseaseService.findById(idDisease));

        assertEquals(Exceptions.ErrorCode.DISEASE_NOT_FOUND, exception.getError());
    }
    @Test
    @DisplayName("Get Account when is Not NUll")
    public void testGetAccountNotNull() {

        long idDeasese = 1L;
        Disease disease = new Disease();
        disease.setId(idDeasese);
        when(diseaseRepository.findById(anyLong())).thenReturn(Optional.of(disease));

        Disease diseaseResponse = diseaseService.findById(idDeasese);

        assertNotNull(diseaseResponse);
        assertEquals(disease.getId(), diseaseResponse.getId());
    }

}