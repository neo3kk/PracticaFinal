package com.rest.vue.service;

import com.rest.vue.entities.*;
import com.rest.vue.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CategoryServiceImpl implements CategoryService {

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
    public Integer randomId() {
        Random rand = new Random();
        int random = rand.nextInt(1000000000);
        return random;
    }

    @Override
    public Category findCategory(String category) {
        Category cat = categoryRepository.findCategoryByTitle(category);
        return cat;
    }

    @Override
    public String randomColor() {
        Double r = Math.floor(Math.random() * 255);
        Double u = Math.floor(Math.random() * 100);
        Double y = Math.floor(Math.random() * 100);
        String color = "hsl(" + r.toString() + "," + u.toString() + "," + y.toString() + ")";
        return color;
    }

    @Override
    public Category createCategory(Category category) {

        Category category1 = categoryRepository.save(category);
        return category1;
    }

    @Override
    public CategoryDTO makeCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.set_id(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setSlug(category.getSlug());
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
    public Category updateCategory(Category category) {
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
