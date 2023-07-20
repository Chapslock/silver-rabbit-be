package com.chapslock.silver.rabbit.core.person;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Person {

    Id id;
    String name;
    Long professionCategoryId;
    Boolean hasAgreedToTerms;


    @Value(staticConstructor = "of")
    public static class Id {
        UUID value;
    }
}
