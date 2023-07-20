package com.chapslock.silver.rabbit.core.person;

import com.chapslock.silver.rabbit.core.validation.ValidationErrorCode;
import com.chapslock.silver.rabbit.core.validation.ValidationException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RegisterPerson {

    private final SavePerson savePerson;

    public Response execute(Request request) {
        validateRequest(request);

        Person person = savePerson.execute(Person
                .builder()
                .name(request.getName())
                .professionCategoryId(request.getProfessionCategoryId())
                .hasAgreedToTerms(request.getHasAgreedToTerms())
                .build());
        return Response
                .builder()
                .personId(person.getId())
                .name(person.getName())
                .professionCategoryId(person.getProfessionCategoryId())
                .hasAgreedToTerms(person.getHasAgreedToTerms())
                .build();
    }

    private void validateRequest(Request request) {
        Set<ValidationErrorCode> errorCodes = new HashSet<>();

        if (request.getName() == null || StringUtils.isEmpty(request.getName())) {
            errorCodes.add(RegisterPersonErrorCode.NAME_IS_MANDATORY);
        }
        if (request.getProfessionCategoryId() == null) {
            errorCodes.add(RegisterPersonErrorCode.PROFESSION_CATEGORY_IS_MANDATORY);
        }
        if (request.getHasAgreedToTerms() == null || !request.getHasAgreedToTerms()) {
            errorCodes.add(RegisterPersonErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS);
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
        String name;
        Long professionCategoryId;
        Boolean hasAgreedToTerms;
    }

    public enum RegisterPersonErrorCode implements ValidationErrorCode {
        NAME_IS_MANDATORY,
        PROFESSION_CATEGORY_IS_MANDATORY,
        YOU_HAVE_TO_AGREE_TO_TERMS
    }
}
