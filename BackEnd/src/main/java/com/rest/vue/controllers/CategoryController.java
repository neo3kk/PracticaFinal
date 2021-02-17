package com.rest.vue.controllers;


import com.google.gson.Gson;
import com.rest.vue.entities.Category;
import com.rest.vue.entities.Topic;
import com.rest.vue.repos.CategoryRepository;
import com.rest.vue.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    Gson gson = new Gson();

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TopicRepository topicRepository;


    @GetMapping("/categories")
    public ResponseEntity<String> getCategories() {
        List<Category> list = new ArrayList<>();
        categoryRepository.findAll().forEach(c -> list.add(c));
        return new ResponseEntity<>(gson.toJson(list), HttpStatus.OK);
    }


   @GetMapping("categories/{slug}")
    public ResponseEntity<String> getCategory(@PathVariable String slug) {
        Category category = categoryRepository.findCategoryBySlug(slug);
        return new ResponseEntity<>(gson.toJson(category), HttpStatus.OK);
    }

    @GetMapping("categories/{slug}/topics")
    public ResponseEntity<String> getTopics(@PathVariable String slug) {
        List<Topic> list = topicRepository.findTopicsByCategory(slug);
        return new ResponseEntity<>(gson.toJson(list), HttpStatus.OK);
    }


}
