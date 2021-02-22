package com.rest.vue.entities;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class TopicDTO {

    Long id;
    Long _id;
    String title;
    String content;
    Number views;
    String createdAt;
    String updatedAt;
    String category;
    Integer numberOfReplies;
    List<ReplyDTO> replies;
    UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Number getViews() {
        return views;
    }

    public void setViews(Number views) {
        this.views = views;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getNumberOfReplies() {
        return numberOfReplies;
    }

    public void setNumberOfReplies(Integer numberOfReplies) {
        this.numberOfReplies = numberOfReplies;
    }

    public List<ReplyDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyDTO> replies) {
        this.replies = replies;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicDTO topicDTO = (TopicDTO) o;
        return Objects.equals(id, topicDTO.id) &&
                Objects.equals(_id, topicDTO._id) &&
                Objects.equals(title, topicDTO.title) &&
                Objects.equals(content, topicDTO.content) &&
                Objects.equals(views, topicDTO.views) &&
                Objects.equals(createdAt, topicDTO.createdAt) &&
                Objects.equals(updatedAt, topicDTO.updatedAt) &&
                Objects.equals(category, topicDTO.category) &&
                Objects.equals(numberOfReplies, topicDTO.numberOfReplies) &&
                Objects.equals(replies, topicDTO.replies) &&
                Objects.equals(user, topicDTO.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, _id, title, content, views, createdAt, updatedAt, category, numberOfReplies, replies, user);
    }
}
