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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @GetMapping("/getprofile")
    public ResponseEntity<String> getProfile(HttpServletRequest request) {

        String header = request.getHeader("authorization");
        String token = header.replace("Bearer ", "");
        String email = tokenService.getSubject(token);
        User user = userService.findUserByemail(email);
        UserDTO userDTO = userService.makeUserDTO(user);
        return new ResponseEntity<>(gson.toJson(userDTO), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<String> getCategories() {
        List<Category> list = categoryService.findAll();
        if(list==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CategoryDTO> categoryDTOS = categoryService.createListCategoryDTO(list);
        return new ResponseEntity<>(gson.toJson(categoryDTOS), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/categories")
    public ResponseEntity<String> postCategories(@RequestBody String payload, HttpServletRequest request) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String header = request.getHeader("authorization");
        String token = header.replace("Bearer ", "");
        String email = tokenService.getSubject(token);
        User user = userService.findUserByemail(email);
        Category category = new Category();
        category.setTitle(map.get("title"));
        category.setDescription(map.get("description"));
        category.setColor(categoryService.randomColor());
        Category category1 = categoryService.createCategory(category);
        category1.setSlug(category1.getId().toString());

        return new ResponseEntity<>(gson.toJson(category1), HttpStatus.OK);
    }


    @GetMapping("categories/{slug}")
    public ResponseEntity<String> getCategory(@PathVariable String slug) {
        Category category = categoryService.findBySlug(slug);
        CategoryDTO categoryDTO = categoryService.makeCategoryDTO(category);
        return new ResponseEntity<>(gson.toJson(categoryDTO), HttpStatus.OK);
    }

    @GetMapping("categories/{slug}/topics")
    public ResponseEntity<String> getTopics(@PathVariable String slug) {
        List<Topic> list = topicService.findTopicsByCategory(slug);
        List<TopicDTO> listDTO = topicService.createListTopicDTO(list);

        return new ResponseEntity<>(gson.toJson(listDTO), HttpStatus.OK);
    }

   @GetMapping("topics/{topicParam}")
    public ResponseEntity<String> getReplies(@PathVariable Long topicParam) {

        Topic topic = topicService.findById(topicParam);
        topic.setViews(topic.getViews()+1);
        topicService.updateTopic(topic);
        TopicDTO topicDTO = topicService.makeTopicDTO(topic);
        return new ResponseEntity<>(gson.toJson(topicDTO), HttpStatus.OK);
    }



    @PostMapping("/topics")
    public ResponseEntity<String> login(@RequestBody String payload, HttpServletRequest request) {
        System.out.println("SHOW TOPICS");
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        Integer random  = categoryService.randomId();

        String header = request.getHeader("authorization");
        String token = header.replace("Bearer ", "");
        String email = tokenService.getSubject(token);
        User user = userService.findUserByemail(email);
        System.out.println(user.getEmail());
        Topic topic = new Topic();
        //topic.set_id(random);
        topic.setTitle(map.get("title"));
        topic.setContent(map.get("content"));
        topic.setUser(user);

        Category category = categoryService.findBySlug(map.get("category"));

        topic.setCategory(category);
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
