package com.rest.vue.service;

import com.rest.vue.entities.Reply;
import com.rest.vue.entities.Topic;
import com.rest.vue.entities.TopicDTO;
import com.rest.vue.entities.User;
import com.rest.vue.repos.ReplyRepository;
import com.rest.vue.repos.TopicRepository;
import com.rest.vue.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Override
    public List<Topic> findTopicsByCategory(String slug) {
        List<Topic> list = topicRepository.findTopicsByCategory(slug);
        return list;
    }

    @Override
    public List<TopicDTO> createListTopicDTO(List<Topic> list) {
        List<TopicDTO> listDTO = new ArrayList<>();
        list.forEach(topic->{
            TopicDTO topicDTO = makeTopicDTO(topic);
            listDTO.add(topicDTO);
        });
        return listDTO;
    }

    @Override
    public TopicDTO makeTopicDTO(Topic topic) {
        User userTopic = userRepository.findUserByName(topic.getUser());
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.set_id(topic.get_id());
        topicDTO.setCategory(topic.getCategory());
        topicDTO.setContent(topic.getContent());
        topicDTO.setCreatedAt(topic.getCreated_at());
        topicDTO.setUpdatedAt(topic.getUpdated_at());
        topicDTO.setViews(topic.getViews());
        topicDTO.setUser(userTopic);
        List<Reply> replies = replyRepository.findRepliesByTopicAndUser(topic.getTitle(), userTopic.getName());
        topicDTO.setReplies(replies);
        topicDTO.setNumberOfReplies(replies.size());
    return topicDTO;
    }

    @Override
    public Topic findById(Integer topicParam) {
        Topic topic = topicRepository.findTopicBy_id(topicParam);
        return topic;
    }

    @Override
    public boolean createTopic(Topic topic) {
        if (topicRepository.findTopicsBy_id(topic.get_id()) == null) {
            try {
                topicRepository.save(topic);
                return true;
            } catch (Exception e) {
                System.out.println("HA FALLAT INSERT");
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
