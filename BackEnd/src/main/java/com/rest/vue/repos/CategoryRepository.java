package com.rest.vue.repos;


import com.rest.vue.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryBySlug(String slug);

    Category findCategoryByTitle(String title);

/*    @Query("select * from category where color=:color");
   List<Category> findCategoriesByColor(String color);
    List<Category> findCategoriesBySlug(String slug);*/

}

