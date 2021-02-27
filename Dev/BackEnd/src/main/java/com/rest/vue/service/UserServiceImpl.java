package com.rest.vue.service;

import com.google.gson.Gson;
import com.rest.vue.entities.*;
import com.rest.vue.repos.ImageRepository;
import com.rest.vue.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    Gson gson = new Gson();

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    ImageRepository imageRepository;


    @Override
    public UserDTO makeUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        String[] string = new String[]{"own_topics:write", "own_topics:delete", "own_replies:write", "own_replies:delete", "categories:write", "categories:delete"};
        Map<String, Object> permissions = new HashMap<>();
        permissions.put("root", string);
        permissions.put("categories", new ArrayList<>());
        userDTO.set_id(user.getId());
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setPassword(user.getPassword());

        try {
            byte[] image = imageRepository.findImageByUser(user).getPhoto();
            userDTO.setAvatarUrl(Arrays.toString(image));
        } catch (Exception e) {
            userDTO.setAvatarUrl("");
        }


        userDTO.setPermissions(permissions);
        return userDTO;
    }

    @Override
    public User findUserByemail(String email) {
        User user = userRepository.findUserByemail(email);
        return user;
    }

    @Override
    public boolean createUser(User user) {
        if (userRepository.findUserByemail(user.getEmail()) == null) {
            try {
                userRepository.save(user);
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
    public User getUerRequest(HttpServletRequest request) {
        String email = tokenService.getSubject(request);
        return findUserByemail(email);
    }

    @Override
    public User updateUser(String payload, HttpServletRequest request) {
        HashMap map = gson.fromJson(payload, HashMap.class);
        User userRequest = userRepository.findById(getUerRequest(request).getId()).get();
        String email = (String) map.get("email");
        String name = (String) map.get("name");
        String avatar = (String) map.get("avatar");
        userRequest.setName(name);
        userRequest.setEmail(email);
        return userRepository.save(userRequest);
    }

    private Image updateImage(String avatar, User userRequest) {
        byte[] byteData = avatar.getBytes();
        Image image = new Image();
        image.setPhoto(byteData);
        image.setUser(userRequest);
        if(userRequest.getAvatar().getId()!=null){
            System.out.println("DELETE IMAGE");
            try {
                System.out.println(imageRepository.findById(userRequest.getAvatar().getId()).get().toString());
                imageRepository.delete(imageRepository.findById(userRequest.getAvatar().getId()).get());
            }catch (Exception e){
                System.out.println("ERROR");
            }
        }
        Image savedImage = imageRepository.save(image);
        return savedImage;

    }

    @Override
    public User updatePassword(String newPassword, HttpServletRequest request) {
        User user = getUerRequest(request);
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean checkpassword(HttpServletRequest request, String currentPassword) {
        User user = getUerRequest(request);
        if (user.getPassword().equals(currentPassword)) {
            return true;
        }
        return false;
    }
}
