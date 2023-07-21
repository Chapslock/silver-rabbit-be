package com.chapslock.silver.rabbit.core.person;

import com.chapslock.silver.rabbit.core.person.validation.PersonRegistrationValidation;
import com.chapslock.silver.rabbit.core.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UpdateRegisteredPersonTest {

    private static final String SAMPLE_NAME = "name";
    private static final long SAMPLE_PROFESSION_CATEGORY_ID = 1L;
    private static final Person.Id SAMPLE_PERSON_ID = Person.Id.of(UUID.randomUUID());

    @Mock
    private PersonRegistrationValidation personRegistrationValidation;
    @Mock
    private UpdatePerson updatePerson;

    private UpdateRegisteredPerson updateRegisteredPerson;

    @BeforeEach
    void setup() {
        updateRegisteredPerson = new UpdateRegisteredPerson(
                personRegistrationValidation,
                updatePerson
        );
    }

    @Test
    void updateRegisteredPerson_forValid_updateGetsCalled() {
        Person person = composePersonBuilder().build();
        Mockito.when(personRegistrationValidation.execute(PersonRegistrationValidation.Request
                        .builder()
                        .name(SAMPLE_NAME)
                        .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                        .hasAgreedToTerms(true)
                        .build()))
                .thenReturn(emptySet());
        Mockito.when(updatePerson.execute(UpdatePerson.Request.of(person)))
                .thenReturn(person);

        var result = updateRegisteredPerson.execute(UpdateRegisteredPerson.Request
                .builder()
                .personId(SAMPLE_PERSON_ID)
                .name(SAMPLE_NAME)
                .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                .hasAgreedToTerms(true)
                .build());

        Mockito.verify(updatePerson).execute(UpdatePerson.Request.of(person));
    }

    @Test
    void updateRegisteredPerson_formInvalid_exceptionIsThrown() {
        Person person = composePersonBuilder().build();
        Mockito.when(personRegistrationValidation.execute(PersonRegistrationValidation.Request
                        .builder()
                        .name(SAMPLE_NAME)
                        .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                        .hasAgreedToTerms(false)
                        .build()))
                .thenReturn(new HashSet<>(List.of(PersonRegistrationValidation.PersonRegistrationErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS)));

        UpdateRegisteredPerson.Request request = UpdateRegisteredPerson.Request
                .builder()
                .personId(null)
                .name(SAMPLE_NAME)
                .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                .hasAgreedToTerms(false)
                .build();
        var exception = assertThrows(ValidationException.class, () -> updateRegisteredPerson.execute(request));

        assertThat(exception.getErrors())
                .contains(
                        UpdateRegisteredPerson.UpdateRegisteredPersonErrorCode.PERSON_ID_NOT_PROVIDED,
                        PersonRegistrationValidation.PersonRegistrationErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS
                );
    }

    private static Person.PersonBuilder composePersonBuilder() {
        return Person
                .builder()
                .id(SAMPLE_PERSON_ID)
                .name(SAMPLE_NAME)
                .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                .hasAgreedToTerms(true);
    }

}
