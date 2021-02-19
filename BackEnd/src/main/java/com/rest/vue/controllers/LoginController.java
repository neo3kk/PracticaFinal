package com.rest.vue.controllers;


import com.google.gson.Gson;
import com.rest.vue.entities.User;
import com.rest.vue.repos.UserRepository;
import com.rest.vue.service.LoginService;
import com.rest.vue.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    Gson gson = new Gson();

    @Autowired
    LoginService loginService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");
        if (!loginService.checkUserAndPassword(email, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findUserByEmailAndPassword(email, password);
        String token = tokenService.newToken(email);
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("token", token);
        restMap.put("user", user);
        return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);
    }
}
