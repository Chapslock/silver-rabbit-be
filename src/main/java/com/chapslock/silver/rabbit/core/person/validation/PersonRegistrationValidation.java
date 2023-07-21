package com.chapslock.silver.rabbit.core.person.validation;

import com.chapslock.silver.rabbit.core.validation.ValidationErrorCode;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PersonRegistrationValidation {

    public Set<ValidationErrorCode> execute(Request request) {
        Set<ValidationErrorCode> errorCodes = new HashSet<>();

        if (request.getName() == null || StringUtils.isEmpty(request.getName())) {
            errorCodes.add(PersonRegistrationErrorCode.NAME_IS_MANDATORY);
        }
        if (request.getProfessionCategoryId() == null) {
            errorCodes.add(PersonRegistrationErrorCode.PROFESSION_CATEGORY_IS_MANDATORY);
        }
        if (request.getHasAgreedToTerms() == null || !request.getHasAgreedToTerms()) {
            errorCodes.add(PersonRegistrationErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS);
        }

        return errorCodes;
    }

    @Value
    @Builder
    public static class Request {
        String name;
        Long professionCategoryId;
        Boolean hasAgreedToTerms;
    }

    public enum PersonRegistrationErrorCode implements ValidationErrorCode {
        NAME_IS_MANDATORY,
        PROFESSION_CATEGORY_IS_MANDATORY,
        YOU_HAVE_TO_AGREE_TO_TERMS
    }
}
