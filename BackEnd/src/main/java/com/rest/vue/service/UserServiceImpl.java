package com.rest.vue.service;

import com.rest.vue.entities.*;
import com.rest.vue.repos.ReplyRepository;
import com.rest.vue.repos.TopicRepository;
import com.rest.vue.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;



    @Override
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
}
