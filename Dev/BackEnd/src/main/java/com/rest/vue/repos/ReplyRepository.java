package com.rest.vue.repos;


import com.rest.vue.entities.Reply;
import com.rest.vue.entities.Topic;
import com.rest.vue.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findRepliesByTopic(String topic);

    List<Reply> findRepliesByTopicAndUser(Topic topic, User user);

    List<Reply> findRepliesByTopicId(Long topic_id);
}

