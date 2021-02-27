package com.rest.vue.repos;


import com.rest.vue.entities.Image;
import com.rest.vue.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImageByUser(User user);
}

