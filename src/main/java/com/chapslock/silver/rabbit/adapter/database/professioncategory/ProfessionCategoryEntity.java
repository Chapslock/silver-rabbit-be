package com.chapslock.silver.rabbit.adapter.database.professioncategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROFESSION_CATEGORY")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionCategoryEntity {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "parent_id")
    private Long parentId;

}
