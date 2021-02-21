package com.rest.vue.service;

import com.rest.vue.entities.User;
import com.rest.vue.entities.UserDTO;

public interface UserService {


    UserDTO makeUserDTO(User user);

    User findUserByemail(String email);

    boolean createUser(User user);

}
