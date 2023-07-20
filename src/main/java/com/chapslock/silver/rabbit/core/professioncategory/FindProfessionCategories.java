package com.chapslock.silver.rabbit.core.professioncategory;

import lombok.Value;

import java.util.Set;

@FunctionalInterface
public interface FindProfessionCategories {

    Set<ProfessionCategory> execute();

    @Value(staticConstructor = "of")
    class ProfessionCategory {
        Long id;
        String name;
        Long parentId;
    }
}
