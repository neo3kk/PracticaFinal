package com.rest.vue.repos;


import com.rest.vue.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryBySlug(String slug);

}

