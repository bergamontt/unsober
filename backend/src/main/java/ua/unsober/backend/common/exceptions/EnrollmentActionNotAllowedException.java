package ua.unsober.backend.common.exceptions;

public class EnrollmentActionNotAllowedException extends RuntimeException {
    public EnrollmentActionNotAllowedException(String message) {
        super(message);
    }
}
