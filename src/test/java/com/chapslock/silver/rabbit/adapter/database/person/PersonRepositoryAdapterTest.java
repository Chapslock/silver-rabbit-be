package com.chapslock.silver.rabbit.adapter.database.person;

import com.chapslock.silver.rabbit.BaseIntegrationTest;
import com.chapslock.silver.rabbit.core.person.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PersonRepositoryAdapterTest extends BaseIntegrationTest {

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

        Person result = personRepositoryAdapter.execute(person);

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

        Person result = personRepositoryAdapter.execute(person);

        PersonEntity entity = personRepository.getReferenceById(result.getId().getValue());
        assertThat(entity.getId()).isNotNull();
        assertThat(entity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(person);
    }

}
