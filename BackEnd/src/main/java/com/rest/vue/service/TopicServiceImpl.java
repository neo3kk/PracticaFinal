package com.rest.vue.service;

import com.rest.vue.entities.*;
import com.rest.vue.repos.CategoryRepository;
import com.rest.vue.repos.ReplyRepository;
import com.rest.vue.repos.TopicRepository;
import com.rest.vue.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CategoryService categoryService;

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
        List<TopicDTO> listDTO = new ArrayList<>();
        list.forEach(topic -> {
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
        CategoryDTO categoryDTO = categoryService.makeCategoryDTO(categoryService.findBySlug(topic.getCategory().getSlug()));
        topicDTO.setCategory(categoryDTO);
        topicDTO.setContent(topic.getContent());
        topicDTO.setCreatedAt(topic.getCreated_at());
        topicDTO.setUpdatedAt(topic.getUpdated_at());
        topicDTO.setViews(topic.getViews());
        UserDTO user = userService.makeUserDTO(userTopic);
        topicDTO.setUser(user);
        topicDTO.setReplies(null);
        topicDTO.setNumberOfReplies(topic.getNumber_of_replies());
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
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Topic updateTopic(Topic topic) {
        Topic t = topicRepository.save(topic);
        return t;
    }

    @Override
    public boolean deleteTopic(Long id, HttpServletRequest request) {
        Topic topic = findById(id);
        User user = userService.getUerRequest(request);
        if (topic.getUser().getId().equals(user.getId())) {
            try {
                topicRepository.delete(topic);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;

    }

}
