package com.chapslock.silver.rabbit.adapter.web.professioncategory;

import com.chapslock.silver.rabbit.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfessionCategoryControllerTest extends BaseIntegrationTest {

    public static final String BASE_URL = "/api/profession-categories";

    @Test
    void findProfessionCategories_validRequest_returnsCategories() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(79));
    }

}
