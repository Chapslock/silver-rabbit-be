package com.chapslock.silverrabbitbe.adapter.database.professioncategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "PROFESSION_CATEGORY")
@Getter
public class ProfessionCategoryEntity {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "parent_id")
    private Long parentId;

}
