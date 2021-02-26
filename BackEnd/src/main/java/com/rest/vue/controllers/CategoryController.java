package com.rest.vue.controllers;


import com.google.gson.Gson;
import com.rest.vue.entities.*;
import com.rest.vue.service.*;
import com.rest.vue.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    ReplyService replyService;

    @GetMapping("/getprofile")
    public ResponseEntity<String> getProfile(HttpServletRequest request) {
        String email = tokenService.getSubject(request);
        User user = userService.findUserByemail(email);
        UserDTO userDTO = userService.makeUserDTO(user);
        return new ResponseEntity<>(gson.toJson(userDTO), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> putProfile(@RequestBody String payload, HttpServletRequest request) {
        User user = userService.updateUser(payload, request);
        UserDTO userDTO = userService.makeUserDTO(user);
        String token = tokenService.newToken(userDTO.getEmail());
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("token", token);
        restMap.put("user", userDTO);
        return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.OK);
    }

    @PutMapping("/profile/password")
    public ResponseEntity<String> putPassword(@RequestBody String payload, HttpServletRequest request) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String currentPassword = map.get("currentPassword");
        String newPassword = map.get("newPassword");
        boolean checkPassword = userService.checkpassword(request, currentPassword);
        if(checkPassword){
            userService.updatePassword(newPassword, request);
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
        return new ResponseEntity<>("message:Your courrent password is wrong!", HttpStatus.UNAUTHORIZED);


    }

    @GetMapping("/categories")
    public ResponseEntity<String> getCategories() {
        List<Category> list = categoryService.findAll();
        if (list == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CategoryDTO> categoryDTOS = categoryService.createListCategoryDTO(list);
        return new ResponseEntity<>(gson.toJson(categoryDTOS), HttpStatus.OK);
    }


    @PostMapping("/categories")
    public ResponseEntity<String> postCategories(@RequestBody String payload) {
        Category category1 = categoryService.createCategory(payload);
        return new ResponseEntity<>(gson.toJson(category1), HttpStatus.OK);
    }


    @GetMapping("categories/{slug}")
    public ResponseEntity<String> getCategory(@PathVariable String slug) {
        Category category = categoryService.findBySlug(slug);
        CategoryDTO categoryDTO = categoryService.makeCategoryDTO(category);
        return new ResponseEntity<>(gson.toJson(categoryDTO), HttpStatus.OK);
    }


    @PutMapping("categories/{slug}")
    public ResponseEntity<String> putCategory(@PathVariable String slug, @RequestBody String payload) {
        Category updatedCat = categoryService.updateCategory(slug, payload);
        CategoryDTO categoryDTO = categoryService.makeCategoryDTO(updatedCat);
        return new ResponseEntity<>(gson.toJson(categoryDTO), HttpStatus.OK);
    }


    @DeleteMapping("categories/{slug}")
    public ResponseEntity<String> deleteCategory(@PathVariable String slug) {
        Category category = categoryService.findBySlug(slug);
        Boolean remove = categoryService.removeCategory(category);
        if (remove) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
        return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("categories/{slug}/topics")
    public ResponseEntity<String> getTopics(@PathVariable String slug) {
        List<Topic> list = topicService.findTopicsByCategory(slug);
        List<TopicDTO> listDTO = topicService.createListTopicDTO(list);
        return new ResponseEntity<>(gson.toJson(listDTO), HttpStatus.OK);
    }


    @GetMapping("topics/{id}")
    public ResponseEntity<String> getReplies(@PathVariable Long id) {
        Topic topic = topicService.findById(id);
        topic.setViews(topic.getViews() + 1);
        topicService.updateTopic(topic);
        TopicDTO topicDTO = topicService.makeTopicDTO(topic);
        List<ReplyDTO> replyDTOS = replyService.createListReplyDTO(topic.getReplies());
        topicDTO.setReplies(replyDTOS);
        return new ResponseEntity<>(gson.toJson(topicDTO), HttpStatus.OK);
    }


    @PutMapping("topics/{id}")
    public ResponseEntity<String> putTopic(@PathVariable Long id, @RequestBody String payload, HttpServletRequest request) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String title = map.get("title");
        String category = map.get("category");
        String content = map.get("content");
        Topic topic = topicService.findById(id);
        topic.setTitle(title);
        topic.setContent(content);
        topic.setCategory(categoryService.findCategory(category));
        topic.setUpdated_at(utils.getToday());
        Topic updatedTopic = topicService.updateTopic(topic);
        TopicDTO topicDTO = topicService.makeTopicDTO(updatedTopic);
        List<ReplyDTO> replyDTOS = replyService.createListReplyDTO(topic.getReplies());
        topicDTO.setReplies(replyDTOS);
        return new ResponseEntity<>(gson.toJson(topicDTO), HttpStatus.OK);
    }

    @DeleteMapping("topics/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id,HttpServletRequest request) {
        boolean delete = topicService.deleteTopic(id, request);
        if(delete){
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
        return new ResponseEntity<>("false", HttpStatus.UNAUTHORIZED);
    }




    @PostMapping("/topics")
    public ResponseEntity<String> login(@RequestBody String payload, HttpServletRequest request) {
        User user = userService.getUerRequest(request);
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        Topic topic = new Topic();
        topic.setTitle(map.get("title"));
        topic.setContent(map.get("content"));
        topic.setUser(user);
        Category category = categoryService.findBySlug(map.get("category"));
        topic.setCategory(category);
        topic.setCreated_at(utils.getToday());
        topic.setUpdated_at(utils.getToday());
        topic.setViews(0);
        topic.setNumber_of_replies(0);
        if (topicService.createTopic(topic)) {
            TopicDTO topicDTO = topicService.makeTopicDTO(topic);
            return new ResponseEntity<>(gson.toJson(topicDTO), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    @PostMapping("topics/{slug}/replies")
    public ResponseEntity<String> postReplies(@PathVariable Long slug, @RequestBody String payload, HttpServletRequest request) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        User user = userService.getUerRequest(request);
        Reply reply = replyService.createReply(slug, map.get("content"));
        reply.setUser(user);
        Reply saved = replyService.save(reply);
        ReplyDTO replyDTO = replyService.makeReplyDTO(saved);
        return new ResponseEntity<>(gson.toJson(replyDTO), HttpStatus.OK);
    }

    @PutMapping("topics/{slug}/replies/{id}")
    public ResponseEntity<String> updateReply(@PathVariable Long id, @RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        Reply reply = replyService.updateReply(id, map.get("content"));
        ReplyDTO replyDTO = replyService.makeReplyDTO(reply);
        return new ResponseEntity<>(gson.toJson(replyDTO), HttpStatus.OK);
    }

    @DeleteMapping("topics/{slug}/replies/{id}")
    public ResponseEntity<String> deleteReply(@PathVariable Long id, HttpServletRequest request) {
        boolean remove = replyService.deleteReply(id, request);
        if(remove){
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
        return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
    }

}
