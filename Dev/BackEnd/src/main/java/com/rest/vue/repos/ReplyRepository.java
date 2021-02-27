package com.rest.vue.repos;


import com.rest.vue.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findRepliesByTopicId(Long topic_id);
}

