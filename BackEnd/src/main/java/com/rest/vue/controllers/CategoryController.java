package com.rest.vue.controllers;


import com.google.gson.Gson;
import com.rest.vue.entities.*;
import com.rest.vue.service.*;
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

    @Autowired
    ReplyService replyService;

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
        if (list == null) {
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


    @PutMapping("categories/{slug}")
    public ResponseEntity<String> putCategory(@PathVariable String slug, @RequestBody String payload, HttpServletRequest request) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String title = map.get("title");
        String description = map.get("description");
        Category category = categoryService.findBySlug(slug);
        category.setTitle(title);
        category.setDescription(description);
        Category updatedCat = categoryService.updateCategory(category);
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

    @GetMapping("topics/{topicParam}")
    public ResponseEntity<String> getReplies(@PathVariable Long topicParam) {

        Topic topic = topicService.findById(topicParam);
        topic.setViews(topic.getViews() + 1);
        topicService.updateTopic(topic);
        TopicDTO topicDTO = topicService.makeTopicDTO(topic);
        List<ReplyDTO> replyDTOS = replyService.createListReplyDTO(topic.getReplies());
        topicDTO.setReplies(replyDTOS);
        return new ResponseEntity<>(gson.toJson(topicDTO), HttpStatus.OK);
    }


    @PostMapping("/topics")
    public ResponseEntity<String> login(@RequestBody String payload, HttpServletRequest request) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String header = request.getHeader("authorization");
        String token = header.replace("Bearer ", "");
        String email = tokenService.getSubject(token);
        User user = userService.findUserByemail(email);
        System.out.println(user.getEmail());
        Topic topic = new Topic();
        topic.setTitle(map.get("title"));
        topic.setContent(map.get("content"));
        topic.setUser(user);
        Category category = categoryService.findBySlug(map.get("category"));
        topic.setCategory(category);
        topic.setCreated_at(dateFormat.format(date));
        topic.setUpdated_at(dateFormat.format(date));
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
        String header = request.getHeader("authorization");
        String token = header.replace("Bearer ", "");
        String email = tokenService.getSubject(token);
        User user = userService.findUserByemail(email);

        Reply reply = replyService.createReply(slug, map.get("content"));
        reply.setUser(user);
        Reply saved = replyService.save(reply);
        ReplyDTO replyDTO = replyService.makeReplyDTO(saved);
       /* Topic topic = saved.getTopic();
        topic.setNumber_of_replies(topic.getReplies().toArray().length);
        topicService.updateTopic(topic);
        TopicDTO topicDTO = topicService.makeTopicDTO(topic);
        topicDTO.setNumberOfReplies(topic.getReplies().toArray().length);
        List<ReplyDTO> replyDTOS = replyService.createListReplyDTO(topic.getReplies());
        topicDTO.setReplies(replyDTOS);*/

        return new ResponseEntity<>(gson.toJson(replyDTO), HttpStatus.OK);
    }


}
