package com.chapslock.silver.rabbit.adapter.database.person;

import com.chapslock.silver.rabbit.core.person.Person;
import com.chapslock.silver.rabbit.core.person.SavePerson;
import com.chapslock.silver.rabbit.core.person.UpdatePerson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonRepositoryAdapter implements
        SavePerson,
        UpdatePerson {

    private final PersonRepository personRepository;

    @Override
    public Person execute(SavePerson.Request request) {
        return toDomain(personRepository.save(toEntity(request.getPerson())));
    }

    @Override
    public Person execute(UpdatePerson.Request request) {
        PersonEntity entity = personRepository.findById(request.getPerson().getId().getValue())
                .orElseThrow();
        return toDomain(personRepository.save(getUpdatedEntity(entity, request.getPerson())));
    }

    private PersonEntity getUpdatedEntity(PersonEntity entity, Person person) {
        return entity.toBuilder()
                .name(person.getName())
                .professionCategoryId(person.getProfessionCategoryId())
                .hasAgreedToTerms(person.getHasAgreedToTerms())
                .build();
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
