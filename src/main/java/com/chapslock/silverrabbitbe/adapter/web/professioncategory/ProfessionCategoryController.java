package com.chapslock.silverrabbitbe.adapter.web.professioncategory;

import com.chapslock.silverrabbitbe.core.professioncategory.FindProfessionCategories;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profession-categories")
@RequiredArgsConstructor
public class ProfessionCategoryController {

    private final FindProfessionCategories findProfessionCategories;

    @GetMapping
    public Set<ProfessionCategoryDto> findProfessionCategories() {
        return findProfessionCategories.execute()
                .stream()
                .map(ProfessionCategoryDto::of)
                .collect(Collectors.toUnmodifiableSet());
        //TODO: charl integration tests!
    }

}
