package com.chapslock.silverrabbitbe.adapter.database.professioncategory;

import com.chapslock.silverrabbitbe.core.professioncategory.FindProfessionCategories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfessionCategoryRepositoryAdapter implements
        FindProfessionCategories {

    private final ProfessionCategoryRepository professionCategoryRepository;

    @Override
    public Set<FindProfessionCategories.ProfessionCategory> execute() {
        return professionCategoryRepository.findAll()
                .stream()
                .map(entity -> FindProfessionCategories.ProfessionCategory.of(
                        entity.getId(),
                        entity.getName(),
                        entity.getParentId()
                ))
                .collect(Collectors.toUnmodifiableSet());
    }
}
