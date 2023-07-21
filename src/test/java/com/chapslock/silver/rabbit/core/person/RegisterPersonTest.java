package com.chapslock.silver.rabbit.core.person;

import com.chapslock.silver.rabbit.core.person.validation.PersonRegistrationValidation;
import com.chapslock.silver.rabbit.core.person.validation.PersonRegistrationValidation.PersonRegistrationErrorCode;
import com.chapslock.silver.rabbit.core.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterPersonTest {

    private static final String SAMPLE_NAME = "name";
    private static final long SAMPLE_PROFESSION_CATEGORY_ID = 1L;
    private static final Person.Id SAMPLE_PERSON_ID = Person.Id.of(UUID.randomUUID());
    @Mock
    private SavePerson savePerson;
    @Mock
    private PersonRegistrationValidation personRegistrationValidation;

    private RegisterPerson registerPerson;

    @BeforeEach
    void setup() {
        registerPerson = new RegisterPerson(
                savePerson,
                personRegistrationValidation
        );
    }

    @Test
    void registerPerson_formValid_personGetsSaved() {
        Person person = composePersonBuilder().build();
        Mockito.when(savePerson.execute(SavePerson.Request.of(person)))
                .thenReturn(composePersonBuilder().id(SAMPLE_PERSON_ID).build());

        RegisterPerson.Response result = registerPerson.execute(RegisterPerson.Request
                .builder()
                .name("name")
                .professionCategoryId(1L)
                .hasAgreedToTerms(true)
                .build());

        verify(savePerson).execute(SavePerson.Request.of(person));
        assertThat(result)
                .extracting(RegisterPerson.Response::getPersonId)
                .isEqualTo(SAMPLE_PERSON_ID);
    }

    @Test
    void registerPerson_formInvalid_exceptionIsThrown() {
        Mockito.when(personRegistrationValidation.execute(PersonRegistrationValidation.Request
                        .builder()
                        .name(SAMPLE_NAME)
                        .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                        .hasAgreedToTerms(false)
                        .build()))
                .thenReturn(Set.of(PersonRegistrationErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS));
        var request = RegisterPerson.Request
                .builder()
                .name(SAMPLE_NAME)
                .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                .hasAgreedToTerms(false)
                .build();

        var exception = assertThrows(ValidationException.class, () -> registerPerson.execute(request));

        assertThat(exception.getErrors())
                .singleElement()
                .isEqualTo(PersonRegistrationErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS);
    }


    private static Person.PersonBuilder composePersonBuilder() {
        return Person
                .builder()
                .name(SAMPLE_NAME)
                .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                .hasAgreedToTerms(true);
    }

}
