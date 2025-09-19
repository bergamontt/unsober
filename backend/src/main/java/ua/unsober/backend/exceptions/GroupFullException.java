package ua.unsober.backend.exceptions;

public class GroupFullException extends RuntimeException {
    public GroupFullException(String message) {
        super(message);
    }
}
