package com.chapslock.silver.rabbit.core.person.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PersonRegistrationValidationTest {

    private PersonRegistrationValidation personRegistrationValidation;

    @BeforeEach
    void setup() {
        personRegistrationValidation = new PersonRegistrationValidation();
    }


    @Test
    void registerPerson_nameMandatory_shouldThrowException() {
        var request = PersonRegistrationValidation.Request
                .builder()
                .professionCategoryId(1L)
                .hasAgreedToTerms(true)
                .build();

        var errorCodes = personRegistrationValidation.execute(request);

        assertThat(errorCodes)
                .singleElement()
                .isEqualTo(PersonRegistrationValidation.PersonRegistrationErrorCode.NAME_IS_MANDATORY);
    }

    @Test
    void registerPerson_professionCategoryMandatory_shouldThrowException() {
        var request = PersonRegistrationValidation.Request
                .builder()
                .name("name")
                .hasAgreedToTerms(true)
                .build();

        var errorCodes = personRegistrationValidation.execute(request);

        assertThat(errorCodes)
                .singleElement()
                .isEqualTo(PersonRegistrationValidation.PersonRegistrationErrorCode.PROFESSION_CATEGORY_IS_MANDATORY);
    }

    @Test
    void registerPerson_hasAgreedToTermsMandatory_shouldThrowException() {
        var request = PersonRegistrationValidation.Request
                .builder()
                .name("name")
                .professionCategoryId(1L)
                .build();

        var errorCodes = personRegistrationValidation.execute(request);

        assertThat(errorCodes)
                .singleElement()
                .isEqualTo(PersonRegistrationValidation.PersonRegistrationErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS);
    }

}
