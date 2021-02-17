package com.rest.vue.repos;


import com.rest.vue.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Category findCategoryBySlug(String slug);
/*    @Query("select * from category where color=:color");
   List<Category> findCategoriesByColor(String color);
    List<Category> findCategoriesBySlug(String slug);*/

}

