package com.chapslock.silver.rabbit.adapter.database.person;

import com.chapslock.silver.rabbit.BaseIntegrationTest;
import com.chapslock.silver.rabbit.core.person.Person;
import com.chapslock.silver.rabbit.core.person.SavePerson;
import com.chapslock.silver.rabbit.core.person.UpdatePerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PersonRepositoryAdapterTest extends BaseIntegrationTest {

    public static final Person.Id SAMPLE_PERSON_ID = Person.Id.of(UUID.randomUUID());
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonRepositoryAdapter personRepositoryAdapter;

    @Test
    void savePerson_mappingToEntityAndBack_mapsCorrectly() {
        var person = Person.builder()
                .name("person")
                .professionCategoryId(1L)
                .hasAgreedToTerms(true)
                .build();

        Person result = personRepositoryAdapter.execute(SavePerson.Request.of(person));

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(person);
    }

    @Test
    void savePerson_verifyEntity_entityGetsSavedCorrectly() {
        var person = Person.builder()
                .name("person")
                .professionCategoryId(1L)
                .hasAgreedToTerms(true)
                .build();

        Person result = personRepositoryAdapter.execute(SavePerson.Request.of(person));

        PersonEntity entity = personRepository.getReferenceById(result.getId().getValue());
        assertThat(entity.getId()).isNotNull();
        assertThat(entity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(person);
    }

    @Test
    void updatePerson_personExists_personGetUpdated() {
        var savedPerson = personRepository.save(PersonEntity.builder()
                .name("person")
                .professionCategoryId(1L)
                .hasAgreedToTerms(true)
                .build());

        Person result = personRepositoryAdapter.execute(UpdatePerson.Request.of(Person
                .builder()
                .id(Person.Id.of(savedPerson.getId()))
                .name("changed")
                .build()));

        assertThat(personRepository.getReferenceById(savedPerson.getId()))
                .extracting(PersonEntity::getName)
                .isEqualTo("changed");
    }

    @Test
    void updatePerson_personDoesNotExist_shouldThrowException() {
        UpdatePerson.Request request = UpdatePerson.Request.of(Person
                .builder()
                .id(SAMPLE_PERSON_ID)
                .build());

        Assertions.assertThrows(NoSuchElementException.class,
                () -> personRepositoryAdapter.execute(request));
    }

}
