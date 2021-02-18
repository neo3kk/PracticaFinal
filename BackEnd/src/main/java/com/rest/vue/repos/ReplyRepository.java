package com.rest.vue.repos;


import com.rest.vue.entities.Reply;
import com.rest.vue.entities.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ReplyRepository extends CrudRepository<Reply, Integer> {
    List<Reply> findRepliesByTopic(String topic);
}

