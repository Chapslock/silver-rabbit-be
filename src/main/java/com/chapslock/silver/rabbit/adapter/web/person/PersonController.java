package com.chapslock.silver.rabbit.adapter.web.person;

import com.chapslock.silver.rabbit.core.person.RegisterPerson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final RegisterPerson registerPerson;

    @PostMapping
    public PersonRegistrationResponseDto registerPerson(@RequestBody PersonRegistrationDto personRegistrationDto) {
        return Optional.ofNullable(registerPerson.execute(PersonRegistrationDto.of(personRegistrationDto)))
                .map(PersonRegistrationResponseDto::of)
                .orElseThrow();
    }
}
