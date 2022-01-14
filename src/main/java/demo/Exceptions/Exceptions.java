package demo.Exceptions;

public class Exceptions extends RuntimeException {

    public enum ErrorCode {
        ACCOUNT_NOT_FOUND,
        APPOINTMENT_NOT_FOUND,
        DISEASE_NOT_FOUND,
        DOCTOR_NOT_FOUND,
        MED_NOT_FOUND,
        PATIENT_NOT_FOUND,
        PAYMENT_NOT_FOUND,
        NOT_ENOUGH_FOUNDS,
        ALREADY_UNDO,
        MED_ALREADY_EXIST,
        PRICE_OR_QTY_CANT_BE_ZERO,
        MED_NOT_IN_TREATMENT
    }

    private ErrorCode error;

    private Exceptions(ErrorCode error) {
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }

    public static Exceptions alreadyUndo() {
        return new Exceptions(ErrorCode.ALREADY_UNDO);
    }

    public static Exceptions medAlreadyExist() {
        return new Exceptions(ErrorCode.MED_ALREADY_EXIST);
    }

    public static Exceptions medPriceZero() {
        return new Exceptions(ErrorCode.PRICE_OR_QTY_CANT_BE_ZERO);
    }

    public static Exceptions notEnoughFounds() {
        return new Exceptions(ErrorCode.NOT_ENOUGH_FOUNDS);
    }
    public static Exceptions patientNotFound() {
        return new Exceptions(ErrorCode.PATIENT_NOT_FOUND);
    }
    public static Exceptions accountNotFound() {
        return new Exceptions(ErrorCode.ACCOUNT_NOT_FOUND);
    }
    public static Exceptions appointmentNotFound() {
        return new Exceptions(ErrorCode.APPOINTMENT_NOT_FOUND);
    }
    public static Exceptions diseaseNotFound() {
        return new Exceptions(ErrorCode.DISEASE_NOT_FOUND);
    }
    public static Exceptions doctorNotFound() {
        return new Exceptions(ErrorCode.DOCTOR_NOT_FOUND);
    }
    public static Exceptions medNotFound() {
        return new Exceptions(ErrorCode.MED_NOT_FOUND);
    }
    public static Exceptions medNotInTreatment() {
        return new Exceptions(ErrorCode.MED_NOT_IN_TREATMENT);
    }

    public static Exceptions paymentNotFound() {
        return new Exceptions(ErrorCode.PAYMENT_NOT_FOUND);
    }

}