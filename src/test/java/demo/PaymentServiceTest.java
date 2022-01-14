package demo;

import demo.Exceptions.Exceptions;
import demo.models.*;
import demo.repository.*;
import demo.services.*;
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
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;


    @Test
    @DisplayName("Get Payment when is NUll")
    public void testGetPaymentNull() {

        long idPayment = 1L;

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class,
                () -> paymentService.findById(idPayment));

        assertEquals(Exceptions.ErrorCode.PAYMENT_NOT_FOUND, exception.getError());
    }
    @Test
    @DisplayName("Get Payment when is Not NUll")
    public void testGetPaymentNotNull() {

        long idPayment = 1L;
        Payment payment = new Payment();
        payment.setId(idPayment);
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        Payment paymentResponse = paymentService.findById(idPayment);

        assertNotNull(paymentResponse);
        assertEquals(payment.getId(), paymentResponse.getId());
    }

}