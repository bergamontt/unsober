package ua.unsober.backend.common.exceptions;

public class GroupFullException extends RuntimeException {
    public GroupFullException(String message) {
        super(message);
    }
}
