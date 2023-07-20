package com.chapslock.silver.rabbit.adapter.web.person;

import com.chapslock.silver.rabbit.core.person.RegisterPerson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRegistrationResponseDto {
    UUID personId;
    String name;
    Long professionCategoryId;
    Boolean hasAgreedToTerms;

    static PersonRegistrationResponseDto of(RegisterPerson.Response response) {
        return PersonRegistrationResponseDto
                .builder()
                .personId(response.getPersonId().getValue())
                .name(response.getName())
                .professionCategoryId(response.getProfessionCategoryId())
                .hasAgreedToTerms(response.getHasAgreedToTerms())
                .build();
    }
}
