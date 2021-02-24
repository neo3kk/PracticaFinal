package com.rest.vue.service;

import com.google.gson.Gson;
import com.rest.vue.entities.*;
import com.rest.vue.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    Gson gson = new Gson();

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;


    @Override
    public UserDTO makeUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        String[] string = new String[]{"own_topics:write", "own_topics:delete", "own_replies:write", "own_replies:delete", "categories:write", "categories:delete"};
        Map<String, Object> permissions = new HashMap<>();
        permissions.put("root", string);
        permissions.put("categories", new ArrayList<>());
        userDTO.set_id(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setPassword(user.getPassword());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setPermissions(permissions);
        return userDTO;
    }

    @Override
    public User findUserByemail(String email) {
        User user = userRepository.findUserByemail(email);
        return user;
    }

    @Override
    public boolean createUser(User user) {
        if (userRepository.findUserByemail(user.getEmail()) == null) {
            try {
                userRepository.save(user);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public User getUerRequest(HttpServletRequest request) {
        String email = tokenService.getSubject(request);
        User user = findUserByemail(email);
        return user;
    }

    @Override
    public User updateUser(String payload, HttpServletRequest request) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        User userRequest = userRepository.findById(getUerRequest(request).getId()).get();
        String email = map.get("email");
        String name = map.get("name");
        String avatar = map.get("avatar");
        //System.out.println(avatar);
        userRequest.setName(name);
        userRequest.setEmail(email);
        User user = userRepository.save(userRequest);
        return user;
    }

    @Override
    public User updatePassword(String newPassword, HttpServletRequest request) {
        User user = getUerRequest(request);
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean checkpassword(HttpServletRequest request, String currentPassword) {
        User user = getUerRequest(request);
        if(user.getPassword().equals(currentPassword)){
            return true;
        }
        return false;
    }
}
