package com.chapslock.silver.rabbit.adapter.web.person;

import com.chapslock.silver.rabbit.BaseIntegrationTest;
import com.chapslock.silver.rabbit.adapter.database.person.PersonRepository;
import com.chapslock.silver.rabbit.core.person.validation.PersonRegistrationValidation.PersonRegistrationErrorCode;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonControllerTest extends BaseIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void registerPerson_validForm_shouldSaveData() throws Exception {
        var result = mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                PersonRegistrationDto
                                        .builder()
                                        .name("name")
                                        .professionCategoryId(1L)
                                        .hasAgreedToTerms(true)
                                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").exists())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.professionCategoryId").value("1"))
                .andExpect(jsonPath("$.hasAgreedToTerms").value("true"))
                .andReturn();

        UUID personId = UUID.fromString(JsonPath.read(result.getResponse().getContentAsString(), "$.personId"));
        assertThat(personRepository.existsById(personId)).isTrue();
    }

    @Test
    void registerPerson_invalidForm_shouldThrowException() throws Exception {
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                PersonRegistrationDto
                                        .builder()
                                        .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCodes.length()").value(3))
                .andExpect(jsonPath("$.errorCodes", hasItem(PersonRegistrationErrorCode.NAME_IS_MANDATORY.name())))
                .andExpect(jsonPath("$.errorCodes", hasItem(PersonRegistrationErrorCode.PROFESSION_CATEGORY_IS_MANDATORY.name())))
                .andExpect(jsonPath("$.errorCodes", hasItem(PersonRegistrationErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS.name())));
    }

    @Test
    @Sql("/testdata/person/update_registered_person_data.sql")
    void updateRegisteredPerson_validForm_shouldUpdateData() throws Exception {
        var result = mockMvc.perform(put("/api/persons/034de748-e580-40d7-ae42-ebd8d514fa14")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                PersonRegistrationDto
                                        .builder()
                                        .name("name")
                                        .professionCategoryId(1L)
                                        .hasAgreedToTerms(true)
                                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").exists())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.professionCategoryId").value("1"))
                .andExpect(jsonPath("$.hasAgreedToTerms").value("true"))
                .andReturn();
    }

    @Test
    void updateRegisteredPerson_invalidForm_shouldThrowException() throws Exception {
        mockMvc.perform(put("/api/persons/034de748-e580-40d7-ae42-ebd8d514fa14")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                PersonRegistrationDto
                                        .builder()
                                        .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCodes.length()").value(3))
                .andExpect(jsonPath("$.errorCodes", hasItem(PersonRegistrationErrorCode.NAME_IS_MANDATORY.name())))
                .andExpect(jsonPath("$.errorCodes", hasItem(PersonRegistrationErrorCode.PROFESSION_CATEGORY_IS_MANDATORY.name())))
                .andExpect(jsonPath("$.errorCodes", hasItem(PersonRegistrationErrorCode.YOU_HAVE_TO_AGREE_TO_TERMS.name())));
    }

}
