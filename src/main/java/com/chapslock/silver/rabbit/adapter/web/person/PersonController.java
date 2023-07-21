package com.chapslock.silver.rabbit.adapter.web.person;

import com.chapslock.silver.rabbit.core.person.RegisterPerson;
import com.chapslock.silver.rabbit.core.person.UpdateRegisteredPerson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final RegisterPerson registerPerson;
    private final UpdateRegisteredPerson updateRegisteredPerson;

    @PostMapping
    public PersonRegistrationResponseDto registerPerson(@RequestBody PersonRegistrationDto personRegistrationDto) {
        return Optional.ofNullable(registerPerson.execute(PersonRegistrationDto.toRegisterPersonRequest(personRegistrationDto)))
                .map(PersonRegistrationResponseDto::ofRegisterPerson)
                .orElseThrow();
    }

    @PutMapping("/{personId}")
    public PersonRegistrationResponseDto updateRegisteredPerson(@RequestBody PersonRegistrationDto personRegistrationDto,
                                                                @PathVariable UUID personId) {
        return Optional.ofNullable(updateRegisteredPerson.execute(PersonRegistrationDto.toUpdateRegisteredPersonRequest(personRegistrationDto, personId)))
                .map(PersonRegistrationResponseDto::ofUpdateRegisteredPerson)
                .orElseThrow();
    }
}
