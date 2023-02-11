package com.telran.bank.controller;

import com.telran.bank.dto.ErrorResponseDTO;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDTO handleEntityNotFoundException(EntityNotFoundException ex){
        return ErrorResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errors(ex.hashCode())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponseDTO handleValidationErrors(ConstraintViolationException e){

        Map<String, List<String>> errors =   e.getConstraintViolations().stream()
                .collect(Collectors.groupingBy(c -> c.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessageTemplate,
                                Collectors.toList())));

        return ErrorResponseDTO.builder()
                .message("Input validation error")
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build();


    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponseDTO handleBadRequestException(BadRequestException ex){
        return ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errors(ex.hashCode())
                .build();
    }
}
