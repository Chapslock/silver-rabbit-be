package com.chapslock.silver.rabbit.adapter.web.person;

import com.chapslock.silver.rabbit.core.person.RegisterPerson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRegistrationDto {
    private String name;
    private Long professionCategoryId;
    private Boolean hasAgreedToTerms;

    static RegisterPerson.Request of(PersonRegistrationDto dto) {
        return RegisterPerson.Request.builder()
                .name(dto.getName())
                .professionCategoryId(dto.getProfessionCategoryId())
                .hasAgreedToTerms(dto.getHasAgreedToTerms())
                .build();
    }
}
