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
public class RegisterPerson {

    private final SavePerson savePerson;
    private final PersonRegistrationValidation personRegistrationValidation;

    public Response execute(Request request) {
        validateRequest(request);

        Person person = savePerson.execute(SavePerson.Request.of(Person
                .builder()
                .name(request.getName())
                .professionCategoryId(request.getProfessionCategoryId())
                .hasAgreedToTerms(request.getHasAgreedToTerms())
                .build()));
        return Response
                .builder()
                .personId(person.getId())
                .name(person.getName())
                .professionCategoryId(person.getProfessionCategoryId())
                .hasAgreedToTerms(person.getHasAgreedToTerms())
                .build();
    }

    private void validateRequest(Request request) {
        Set<ValidationErrorCode> errorCodes = personRegistrationValidation.execute(PersonRegistrationValidation.Request
                .builder()
                .name(request.getName())
                .professionCategoryId(request.getProfessionCategoryId())
                .hasAgreedToTerms(request.getHasAgreedToTerms())
                .build());

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
        String name;
        Long professionCategoryId;
        Boolean hasAgreedToTerms;
    }

}
