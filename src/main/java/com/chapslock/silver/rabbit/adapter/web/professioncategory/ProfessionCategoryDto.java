package com.chapslock.silver.rabbit.adapter.web.professioncategory;

import com.chapslock.silver.rabbit.core.professioncategory.FindProfessionCategories;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProfessionCategoryDto {
    Long id;
    String name;
    Long parentId;

    public static ProfessionCategoryDto of(FindProfessionCategories.ProfessionCategory response) {
        return ProfessionCategoryDto
                .builder()
                .id(response.getId())
                .name(response.getName())
                .parentId(response.getParentId())
                .build();
    }
}
