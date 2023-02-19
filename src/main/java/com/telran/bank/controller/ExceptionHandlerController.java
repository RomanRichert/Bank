package com.telran.bank.controller;

import com.telran.bank.dto.ErrorResponseDTO;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.EntityNotFoundException;
import com.telran.bank.exception.InvalidDateException;
import com.telran.bank.exception.InvalidTransactionTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDTO handleEntityNotFoundException(EntityNotFoundException ex){
        return buildErrorResponseDTO(ex, NOT_FOUND);
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
        return buildErrorResponseDTO(ex, BAD_REQUEST);
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(InvalidTransactionTypeException.class)
    public ErrorResponseDTO handleInvalidTransactionTypeException(InvalidTransactionTypeException ex){
        return buildErrorResponseDTO(ex, NOT_ACCEPTABLE);
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(InvalidDateException.class)
    public ErrorResponseDTO handleInvalidTransactionTypeException(InvalidDateException ex){
        return buildErrorResponseDTO(ex, NOT_ACCEPTABLE);
    }

    private ErrorResponseDTO buildErrorResponseDTO(Exception ex, HttpStatus status){
        return ErrorResponseDTO.builder()
                .status(status)
                .message(ex.getMessage())
                .statusCode(status.value())
                .errors(ex.hashCode())
                .build();
    }
}