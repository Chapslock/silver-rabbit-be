package com.chapslock.silver.rabbit.adapter.database.person;

import com.chapslock.silver.rabbit.core.person.Person;
import com.chapslock.silver.rabbit.core.person.SavePerson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonRepositoryAdapter implements
        SavePerson {

    private final PersonRepository personRepository;

    @Override
    public Person execute(Person person) {
        return toDomain(personRepository.save(toEntity(person)));
    }

    private static PersonEntity toEntity(Person person) {
        return PersonEntity
                .builder()
                .name(person.getName())
                .professionCategoryId(person.getProfessionCategoryId())
                .hasAgreedToTerms(person.getHasAgreedToTerms())
                .build();
    }

    private static Person toDomain(PersonEntity entity) {
        return Person
                .builder()
                .id(Person.Id.of(entity.getId()))
                .name(entity.getName())
                .professionCategoryId(entity.getProfessionCategoryId())
                .hasAgreedToTerms(entity.getHasAgreedToTerms())
                .build();
    }
}
