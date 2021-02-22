package com.rest.vue.entities;

import java.util.Objects;


public class ReplyDTO {

    Long id;
    Long _id;
    String title;
    String content;
    String createdAt;
    String updatedAt;
    Long topic;
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


    public Long getTopic() {
        return topic;
    }

    public void setTopic(Long topic) {
        this.topic = topic;
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
        ReplyDTO replyDTO = (ReplyDTO) o;
        return Objects.equals(id, replyDTO.id) &&
                Objects.equals(_id, replyDTO._id) &&
                Objects.equals(title, replyDTO.title) &&
                Objects.equals(content, replyDTO.content) &&
                Objects.equals(createdAt, replyDTO.createdAt) &&
                Objects.equals(updatedAt, replyDTO.updatedAt) &&
                Objects.equals(topic, replyDTO.topic) &&
                Objects.equals(user, replyDTO.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, _id, title, content, createdAt, updatedAt, topic, user);
    }

    @Override
    public String toString() {
        return "ReplyDTO{" +
                "id=" + id +
                ", _id=" + _id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", topic=" + topic +
                ", user=" + user +
                '}';
    }
}
