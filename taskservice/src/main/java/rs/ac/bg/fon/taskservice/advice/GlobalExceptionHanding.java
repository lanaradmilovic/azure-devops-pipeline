package rs.ac.bg.fon.taskservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rs.ac.bg.fon.taskservice.exception.BadRequest;
import rs.ac.bg.fon.taskservice.exception.TaskNotFoundException;
import rs.ac.bg.fon.taskservice.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHanding {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<BadRequest> handleTaskNotFoundException(TaskNotFoundException taskNotFoundException){
        BadRequest badRequest = new BadRequest(taskNotFoundException.getMessage());
        return new ResponseEntity<>(badRequest, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BadRequest> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        BadRequest badRequest = new BadRequest(userNotFoundException.getMessage());
        return new ResponseEntity<>(badRequest, HttpStatus.NOT_FOUND);
    }
}
