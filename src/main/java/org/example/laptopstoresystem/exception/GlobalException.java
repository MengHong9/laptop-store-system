package org.example.laptopstoresystem.exception;

import org.example.laptopstoresystem.common.response.Response;
import org.example.laptopstoresystem.exception.model.CustomAuthenticationException;
import org.example.laptopstoresystem.exception.model.DuplicateException;
import org.example.laptopstoresystem.exception.model.ResourceNotFoundException;
import org.example.laptopstoresystem.exception.model.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response>  resourceNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("404" , "fail" ,  ex.getMessage()));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Response>  duplicate(DuplicateException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.error("409" , "fail" ,  ex.getMessage()));
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Response>  unprocessableEntity(UnprocessableEntityException ex){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Response.error("422" , "fail" ,  ex.getMessage()));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<Response>  customAuthentication(CustomAuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error("401" , "fail" ,  ex.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response>  handleValidation(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();


            errors.put(fieldName , message);

        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.badRequest("400","fail" , "validation failed" , errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGenericException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error("500" , "fail" ,"something went wrong : " +  ex.getMessage()));
    }
}
