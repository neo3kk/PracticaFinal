package com.rest.vue.service;

import com.rest.vue.entities.Category;
import com.rest.vue.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        categoryRepository.findAll().forEach(c -> list.add(c));
        return list;
    }

    @Override
    public Category findBySlug(String slug) {
        Category category = categoryRepository.findCategoryBySlug(slug);
        return category;
    }
}
