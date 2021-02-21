package com.rest.vue.controllers;


import com.google.gson.Gson;
import com.rest.vue.entities.*;
import com.rest.vue.service.CategoryService;
import com.rest.vue.service.TokenService;
import com.rest.vue.service.TopicService;
import com.rest.vue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    Gson gson = new Gson();

    @Autowired
    CategoryService categoryService;

    @Autowired
    TopicService topicService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;


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

    @GetMapping("/getprofile")
    public ResponseEntity<String> getProfile(HttpServletRequest request) {

        String header = request.getHeader("authorization");
        String token = header.replace("Bearer ", "");
        String email = tokenService.getSubject(token);
        User user = userService.findUserByemail(email);
        UserDTO userDTO = userService.makeUserDTO(user);
        return new ResponseEntity<>(gson.toJson(userDTO), HttpStatus.OK);
    }

    @PostMapping("/topics")
    public ResponseEntity<String> login(@RequestBody String payload, HttpServletRequest request) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        Integer random  = categoryService.randomId();

        String header = request.getHeader("authorization");
        String token = header.replace("Bearer ", "");
        String email = tokenService.getSubject(token);
        User user = userService.findUserByemail(email);
        Topic topic = new Topic();
        topic.set_id(random);
        topic.setTitle(map.get("title"));
        topic.setContent(map.get("content"));
        topic.setUser(user.getName());
        topic.setCategory(map.get("category"));
        topic.setCreated_at(dateFormat.format(date));
        topic.setUpdated_at(dateFormat.format(date));
        topic.setViews(0);
        topic.setNumber_of_replies(0);

        if(topicService.createTopic(topic)){
            TopicDTO topicDTO = topicService.makeTopicDTO(topic);
            return new ResponseEntity<>(gson.toJson(topicDTO), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }


}
