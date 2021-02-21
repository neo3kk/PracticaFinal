package com.rest.vue.repos;


import com.rest.vue.entities.Category;
import com.rest.vue.entities.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface TopicRepository extends CrudRepository<Topic, Integer> {
    List<Topic> findTopicsByCategory(String category);
    Boolean findTopicsBy_id(Integer id);
    Topic findTopicBy_id(Integer id);
}

