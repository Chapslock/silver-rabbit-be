package com.chapslock.silver.rabbit.core.person;

import com.chapslock.silver.rabbit.core.person.validation.PersonRegistrationValidation;
import com.chapslock.silver.rabbit.core.validation.ValidationErrorCode;
import com.chapslock.silver.rabbit.core.validation.ValidationException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UpdateRegisteredPerson {

    private final PersonRegistrationValidation personRegistrationValidation;
    private final UpdatePerson updatePerson;

    public Response execute(Request request) {
        validateRequest(request);

        Person updatedPerson = updatePerson.execute(UpdatePerson.Request.of(Person
                .builder()
                .id(request.getPersonId())
                .name(request.getName())
                .professionCategoryId(request.getProfessionCategoryId())
                .hasAgreedToTerms(request.getHasAgreedToTerms())
                .build()));
        return Response.builder()
                .personId(updatedPerson.getId())
                .name(updatedPerson.getName())
                .professionCategoryId(updatedPerson.getProfessionCategoryId())
                .hasAgreedToTerms(updatedPerson.getHasAgreedToTerms())
                .build();
    }

    private void validateRequest(Request request) {
        Set<ValidationErrorCode> errorCodes = personRegistrationValidation.execute(PersonRegistrationValidation.Request
                .builder()
                .name(request.getName())
                .professionCategoryId(request.getProfessionCategoryId())
                .hasAgreedToTerms(request.getHasAgreedToTerms())
                .build());

        if (request.getPersonId() == null) {
            errorCodes.add(UpdateRegisteredPersonErrorCode.PERSON_ID_NOT_PROVIDED);
        }

        if (!errorCodes.isEmpty()) {
            throw ValidationException.of(errorCodes);
        }
    }

    @Value
    @Builder
    public static class Response {
        Person.Id personId;
        String name;
        Long professionCategoryId;
        Boolean hasAgreedToTerms;
    }

    @Value
    @Builder
    public static class Request {
        Person.Id personId;
        String name;
        Long professionCategoryId;
        Boolean hasAgreedToTerms;
    }

    public enum UpdateRegisteredPersonErrorCode implements ValidationErrorCode {
        PERSON_ID_NOT_PROVIDED,
    }
}
