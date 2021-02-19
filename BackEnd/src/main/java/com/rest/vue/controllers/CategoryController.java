package com.rest.vue.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rest.vue.entities.Category;
import com.rest.vue.entities.Topic;
import com.rest.vue.entities.TopicDTO;
import com.rest.vue.entities.User;
import com.rest.vue.repos.CategoryRepository;
import com.rest.vue.repos.TopicRepository;
import com.rest.vue.repos.UserRepository;
import com.rest.vue.service.CategoryService;
import com.rest.vue.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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
    CategoryService categoryService;

    @Autowired
    TopicService topicService;


    @GetMapping("/categories")
    public ResponseEntity<String> getCategories() {
        List<Category> list = categoryService.findAll();
        return new ResponseEntity<>(gson.toJson(list), HttpStatus.OK);
    }


    @GetMapping("categories/{slug}")
    public ResponseEntity<String> getCategory(@PathVariable String slug) {
        Category category = categoryService.findBySlug(slug);
        return new ResponseEntity<>(gson.toJson(category), HttpStatus.OK);
    }

    @GetMapping("categories/{slug}/topics")
    public ResponseEntity<String> getTopics(@PathVariable String slug) {
        List<Topic> list = topicService.findTopicsByCategory(slug);
        List<TopicDTO> listDTO = topicService.createListTopicDTO(list);
        return new ResponseEntity<>(gson.toJson(listDTO), HttpStatus.OK);
    }

    @GetMapping("topics/{topicParam}")
    public ResponseEntity<String> getReplies(@PathVariable Integer topicParam) {

        Topic topic = topicService.findById(topicParam);
        TopicDTO topicDTO = topicService.makeTopicDTO(topic);
        return new ResponseEntity<>(gson.toJson(topicDTO), HttpStatus.OK);
    }


}
