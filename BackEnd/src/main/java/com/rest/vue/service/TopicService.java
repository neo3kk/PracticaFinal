package com.rest.vue.service;

import com.rest.vue.entities.Topic;
import com.rest.vue.entities.TopicDTO;

import java.util.List;

public interface TopicService {


    List<Topic> findTopicsByCategory(String slug);

    List<TopicDTO> createListTopicDTO(List<Topic> list);

    TopicDTO makeTopicDTO(Topic topic);

    Topic findById(Integer topicParam);

    boolean createTopic(Topic topic);
}
