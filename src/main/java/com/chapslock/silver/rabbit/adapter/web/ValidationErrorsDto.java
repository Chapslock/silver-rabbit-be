package com.chapslock.silver.rabbit.adapter.web;

import com.chapslock.silver.rabbit.core.validation.ValidationErrorCode;
import com.chapslock.silver.rabbit.core.validation.ValidationException;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class ValidationErrorsDto {
    Set<ValidationErrorCode> errorCodes;

    public static ValidationErrorsDto of(ValidationException value) {
        return ValidationErrorsDto.of(value.getErrors());
    }
}
