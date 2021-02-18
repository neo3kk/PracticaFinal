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
    CategoryRepository categoryRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    CategoryService categoryService;


    @GetMapping("/categories")
    public ResponseEntity<String> getCategories() {
        List<Category> list = categoryService.findAll();
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
        List<TopicDTO> listDTO = new ArrayList<>();
        list.forEach(topic->{
            User userTopic = userRepository.findUserByName(topic.getUser());
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.set_id(topic.get_id());
            topicDTO.setCategory(topic.getCategory());
            topicDTO.setContent(topic.getContent());
            topicDTO.setCreatedAt(topic.getCreatedAt());
            topicDTO.setUpdatedAt(topic.getUpdatedAt());
            topicDTO.setViews(topic.getViews());
            topicDTO.setUser(userTopic);
            listDTO.add(topicDTO);
        });

        return new ResponseEntity<>(gson.toJson(listDTO), HttpStatus.OK);
    }

    @GetMapping("topics/{topicParam}")
    public ResponseEntity<String> getReplies(@PathVariable Integer topicParam) {
        Topic topic = topicRepository.findById(topicParam).get();
        User userTopic = userRepository.findUserByName(topic.getUser());
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.set_id(topic.get_id());
        topicDTO.setCategory(topic.getCategory());
        topicDTO.setContent(topic.getContent());
        topicDTO.setCreatedAt(topic.getCreatedAt());
        topicDTO.setUpdatedAt(topic.getUpdatedAt());
        topicDTO.setViews(topic.getViews());
        topicDTO.setUser(userTopic);

        return new ResponseEntity<>(gson.toJson(topicDTO), HttpStatus.OK);
    }


}
