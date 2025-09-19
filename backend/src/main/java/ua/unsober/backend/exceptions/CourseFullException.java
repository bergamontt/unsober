package ua.unsober.backend.exceptions;

public class CourseFullException extends RuntimeException {
  public CourseFullException(String message) {
    super(message);
  }
}
