package org.example.user.contollerAdvices;

import org.example.user.dtos.ExceptionDTO;
import org.example.user.exceptions.FieldNotFoundException;
import org.example.user.exceptions.ResourceNotFoundException;
import org.example.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleProductNotExistsException(UserNotFoundException exception) {
        ExceptionDTO dto = new ExceptionDTO();
        dto.setMessage(exception.getMessage());
        dto.setDetail("User Not Present In DB");
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleFieldNotPassedException(FieldNotFoundException exception){
        ExceptionDTO dto = new ExceptionDTO();
        dto.setMessage(exception.getMessage());
        dto.setDetail("Oops! Something Went Wrong");
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException){
        ExceptionDTO dto = new ExceptionDTO();
        dto.setMessage(resourceNotFoundException.getMessage());
        return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }
}
