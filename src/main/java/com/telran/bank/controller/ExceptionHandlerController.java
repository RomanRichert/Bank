package com.telran.bank.controller;

import com.telran.bank.dto.ErrorResponseDTO;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.EntityNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDTO handleEntityNotFoundException(EntityNotFoundException ex){
        return ErrorResponseDTO.builder()
                .status(NOT_FOUND)
                .message(ex.getMessage())
                .statusCode(NOT_FOUND.value())
                .errors(ex.hashCode())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponseDTO handleValidationErrors(ConstraintViolationException e){
        Map<String, List<String>> errors =  e.getConstraintViolations().stream()
                .collect(groupingBy(c -> c.getPropertyPath().toString(),
                        mapping(ConstraintViolation::getMessageTemplate,
                                toList())));

        return ErrorResponseDTO.builder()
                .message("Input validation error")
                .status(BAD_REQUEST)
                .statusCode(BAD_REQUEST.value())
                .errors(errors)
                .build();


    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponseDTO handleBadRequestException(BadRequestException ex){
        return ErrorResponseDTO.builder()
                .status(BAD_REQUEST)
                .message(ex.getMessage())
                .statusCode(BAD_REQUEST.value())
                .errors(ex.hashCode())
                .build();
    }
}
