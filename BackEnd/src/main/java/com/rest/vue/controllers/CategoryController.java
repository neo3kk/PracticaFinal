package com.rest.vue.controllers;


import com.google.gson.Gson;
import com.rest.vue.entities.*;
import com.rest.vue.repos.UserRepository;
import com.rest.vue.service.CategoryService;
import com.rest.vue.service.TokenService;
import com.rest.vue.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    UserRepository userRepository;


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
        User user = userRepository.findUserByemail(email);
        UserDTO userDTO = makeUserDTO(user);
        return new ResponseEntity<>(gson.toJson(userDTO), HttpStatus.OK);
    }


    public UserDTO makeUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        String[] string = new String[]{"own_topics:write", "own_topics:delete", "own_replies:write", "own_replies:delete"};
        Map<String, Object> permissions = new HashMap<>();
        permissions.put("root", string);
        permissions.put("categories", new ArrayList<>());
        userDTO.set_id(user.get_id());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setPassword(user.getPassword());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setPermissions(permissions);
        return userDTO;
    }


}
