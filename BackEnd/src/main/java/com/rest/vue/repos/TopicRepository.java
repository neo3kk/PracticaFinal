package com.rest.vue.repos;


import com.rest.vue.entities.Category;
import com.rest.vue.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface TopicRepository extends JpaRepository<Topic, Long> {


    List<Topic> findTopicByCategory(Category category);
    Boolean findTopicsByid(Long id);
    Topic findTopicByid(Long id);
}

