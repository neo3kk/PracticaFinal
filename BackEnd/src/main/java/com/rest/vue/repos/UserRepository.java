package com.rest.vue.repos;


import com.rest.vue.entities.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserByEmailAndPassword(String name, String pass);

    User findUserByName(String user);

    User findUserByemail(String email);
}

