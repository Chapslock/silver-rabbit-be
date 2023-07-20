package com.chapslock.silver.rabbit.core.person;

import com.chapslock.silver.rabbit.core.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterPersonTest {

    public static final String SAMPLE_NAME = "name";
    public static final long SAMPLE_PROFESSION_CATEGORY_ID = 1L;
    public static final Person.Id SAMPLE_PERSON_ID = Person.Id.of(UUID.randomUUID());
    @Mock
    private SavePerson savePerson;

    private RegisterPerson registerPerson;

    @BeforeEach
    void setup() {
        registerPerson = new RegisterPerson(
                savePerson
        );
    }

    @Test
    void registerPerson_formValid_personGetsSaved() {
        Person person = composePersonBuilder().build();
        Mockito.when(savePerson.execute(person))
                .thenReturn(composePersonBuilder().id(SAMPLE_PERSON_ID).build());

        RegisterPerson.Response result = registerPerson.execute(RegisterPerson.Request
                .builder()
                .name("name")
                .professionCategoryId(1L)
                .hasAgreedToTerms(true)
                .build());

        verify(savePerson).execute(person);
        assertThat(result)
                .extracting(RegisterPerson.Response::getPersonId)
                .isEqualTo(SAMPLE_PERSON_ID);
    }

    @Test
    void registerPerson_nameMandatory_shouldThrowException() {
        var request = RegisterPerson.Request
                .builder()
                .professionCategoryId(1L)
                .hasAgreedToTerms(true)
                .build();

        var exception = assertThrows(ValidationException.class, () -> registerPerson.execute(request));

        assertThat(exception.getErrors())
                .singleElement()
                .isEqualTo(RegisterPerson.RegisterPersonErrorCode.NAME_IS_MANDATORY);
    }

    @Test
    void registerPerson_professionCategoryMandatory_shouldThrowException() {
        var request = RegisterPerson.Request
                .builder()
                .name("name")
                .hasAgreedToTerms(true)
                .build();

        var exception = assertThrows(ValidationException.class, () -> registerPerson.execute(request));

        assertThat(exception.getErrors())
                .singleElement()
                .isEqualTo(RegisterPerson.RegisterPersonErrorCode.PROFESSION_CATEGORY_IS_MANDATORY);
    }

    @Test
    void registerPerson_hasAgreedToTermsMandatory_shouldThrowException() {
        var request = RegisterPerson.Request
                .builder()
                .name("name")
                .professionCategoryId(1L)
                .build();

        var exception = assertThrows(ValidationException.class, () -> registerPerson.execute(request));

        assertThat(exception.getErrors())
                .singleElement()
                .isEqualTo(RegisterPerson.RegisterPersonErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS);
    }

    private static Person.PersonBuilder composePersonBuilder() {
        return Person
                .builder()
                .name(SAMPLE_NAME)
                .professionCategoryId(SAMPLE_PROFESSION_CATEGORY_ID)
                .hasAgreedToTerms(true);
    }

}
