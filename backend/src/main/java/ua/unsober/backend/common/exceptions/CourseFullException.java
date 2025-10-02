package ua.unsober.backend.common.exceptions;

public class CourseFullException extends RuntimeException {
  public CourseFullException(String message) {
    super(message);
  }
}
