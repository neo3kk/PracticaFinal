package com.rest.vue.service;

import com.rest.vue.entities.*;
import com.rest.vue.repos.CategoryRepository;
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

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Topic> findTopicsByCategory(String slug) {
        Category category = categoryRepository.findCategoryBySlug(slug);
        List<Topic> list = topicRepository.findTopicByCategory(category);
        return list;
    }

    @Override
    public List<TopicDTO> createListTopicDTO(List<Topic> list) {
        System.out.println("CREATE LIST DTO's");
        List<TopicDTO> listDTO = new ArrayList<>();
        list.forEach(topic -> {
            System.out.println("CREATE LIST DTO's");
            TopicDTO topicDTO = makeTopicDTO(topic);
            listDTO.add(topicDTO);
        });
        return listDTO;
    }

    @Override
    public TopicDTO makeTopicDTO(Topic topic) {
        User userTopic = userRepository.findById(topic.getUser().getId()).get();
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.set_id(topic.getId());
        topicDTO.setId(topic.getId());
        topicDTO.setTitle(topic.getTitle());
        topicDTO.setCategory(topic.getCategory().getTitle());
        topicDTO.setContent(topic.getContent());
        topicDTO.setCreatedAt(topic.getCreated_at());
        topicDTO.setUpdatedAt(topic.getUpdated_at());
        topicDTO.setViews(topic.getViews());
        UserDTO user = userService.makeUserDTO(userTopic);
        topicDTO.setUser(user);
        topicDTO.setReplies(null);
        topicDTO.setNumberOfReplies(topic.getNumber_of_replies());
        System.out.println(topicDTO.toString());
        return topicDTO;
    }


    @Override
    public Topic findById(Long topicParam) {
        Topic topic = topicRepository.findTopicByid(topicParam);
        return topic;
    }

    @Override
    public boolean createTopic(Topic topic) {
        if (topicRepository.findTopicsByid(topic.getId()) == null) {
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
    @Override
    public boolean updateTopic(Topic topic) {

        topicRepository.save(topic);
        return true;
    }
}
