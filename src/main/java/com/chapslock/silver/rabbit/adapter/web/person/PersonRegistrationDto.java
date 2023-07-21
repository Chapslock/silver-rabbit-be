package com.chapslock.silver.rabbit.adapter.web.person;

import com.chapslock.silver.rabbit.core.person.Person;
import com.chapslock.silver.rabbit.core.person.RegisterPerson;
import com.chapslock.silver.rabbit.core.person.UpdateRegisteredPerson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRegistrationDto {
    private String name;
    private Long professionCategoryId;
    private Boolean hasAgreedToTerms;

    static RegisterPerson.Request toRegisterPersonRequest(PersonRegistrationDto dto) {
        return RegisterPerson.Request.builder()
                .name(dto.getName())
                .professionCategoryId(dto.getProfessionCategoryId())
                .hasAgreedToTerms(dto.getHasAgreedToTerms())
                .build();
    }

    static UpdateRegisteredPerson.Request toUpdateRegisteredPersonRequest(PersonRegistrationDto dto, UUID personId) {
        return UpdateRegisteredPerson.Request.builder()
                .personId(Person.Id.of(personId))
                .name(dto.getName())
                .professionCategoryId(dto.getProfessionCategoryId())
                .hasAgreedToTerms(dto.getHasAgreedToTerms())
                .build();
    }
}
