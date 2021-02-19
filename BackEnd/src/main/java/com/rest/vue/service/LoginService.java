package com.rest.vue.service;

import com.rest.vue.entities.User;
import com.rest.vue.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UserRepository userRepository;

    public boolean checkUserAndPassword(String username, String password) {
        try{
            User user = userRepository.findUserByEmailAndPassword(username, password);
            if(user==null){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
