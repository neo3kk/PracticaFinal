package com.rest.vue.repos;


import com.rest.vue.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUserId(Long id);
}

