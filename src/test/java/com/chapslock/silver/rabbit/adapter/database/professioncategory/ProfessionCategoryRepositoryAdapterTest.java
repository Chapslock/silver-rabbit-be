package com.chapslock.silver.rabbit.adapter.database.professioncategory;

import com.chapslock.silver.rabbit.BaseIntegrationTest;
import com.chapslock.silver.rabbit.core.professioncategory.FindProfessionCategories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class ProfessionCategoryRepositoryAdapterTest extends BaseIntegrationTest {

    public static final long SAMPLE_ID = 1234567L;
    public static final String SAMPLE_NAME = "name";
    public static final long SAMPLE_PARENT_ID = 2987654321L;

    @Autowired
    private ProfessionCategoryRepository professionCategoryRepository;

    @Autowired
    private ProfessionCategoryRepositoryAdapter adapter;

    @Test
    void findProfessionCategories_customEntry_mapsCorrectly() {
        professionCategoryRepository.save(ProfessionCategoryEntity
                .builder()
                .id(SAMPLE_ID)
                .name(SAMPLE_NAME)
                .parentId(SAMPLE_PARENT_ID)
                .build());

        var result = adapter.execute();
        assertThat(result)
                .contains(FindProfessionCategories.ProfessionCategory.of(SAMPLE_ID, SAMPLE_NAME, SAMPLE_PARENT_ID));
    }
}
