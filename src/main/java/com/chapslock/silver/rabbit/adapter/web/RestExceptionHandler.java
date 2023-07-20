package com.chapslock.silver.rabbit.adapter.web;

import com.chapslock.silver.rabbit.core.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ValidationErrorsDto handleCalendarValidationException(ValidationException e) {
        return ValidationErrorsDto.of(e);
    }
}
