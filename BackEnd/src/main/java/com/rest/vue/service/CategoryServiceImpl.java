package com.rest.vue.service;

import com.google.gson.Gson;
import com.rest.vue.entities.*;
import com.rest.vue.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    Gson gson = new Gson();
    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list;
    }

    @Override
    public Category findBySlug(String slug) {
        Category category = categoryRepository.findCategoryBySlug(slug);
        return category;
    }


    @Override
    public Category findCategory(String category) {
        Category cat = categoryRepository.findCategoryBySlug(category);
        return cat;
    }

    @Override
    public String randomColor() {
        int r = Math.toIntExact(Math.round(Math.floor(Math.random() * 255)));
        int u = Math.toIntExact(Math.round(Math.floor(Math.random() * 100)));
        int y = Math.toIntExact(Math.round(Math.floor(Math.random() * 100)));
        String color = "hsl(" + r + "," + u + "%," + y + "%)";
        return color;
    }

    @Override
    public Category createCategory(String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        Category category = new Category();
        category.setTitle(map.get("title"));
        category.setDescription(map.get("description"));
        category.setColor(randomColor());
        Category category1 = categoryRepository.save(category);
        category1.setSlug(category1.getId().toString());
        Category category2 = categoryRepository.save(category);
        return category2;
    }

    @Override
    public CategoryDTO makeCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.set_id(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setSlug(category.getSlug());
        categoryDTO.setColor(category.getColor());
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> createListCategoryDTO(List<Category> list) {
        List<CategoryDTO> listDTO = new ArrayList<>();
        list.forEach(category -> {
            CategoryDTO categoryDTO = makeCategoryDTO(category);
            listDTO.add(categoryDTO);
        });
        return listDTO;
    }

    @Override
    public Category updateCategory(String slug, String payload) {
        Category category = findBySlug(slug);
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String title = map.get("title");
        String description = map.get("description");
        category.setTitle(title);
        category.setDescription(description);
        Category category1 = categoryRepository.save(category);
        return category1;
    }

    @Override
    public Boolean removeCategory(Category category) {
        try {
            categoryRepository.delete(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
