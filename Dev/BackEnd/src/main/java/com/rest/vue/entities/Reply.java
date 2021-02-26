package com.rest.vue.entities;


import javax.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "reply")
public class Reply {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name ="content", columnDefinition = "TEXT")
    String content;

    @Column(name ="created_at", columnDefinition = "TEXT")
    String created_at;

    @Column(name ="updated_at", columnDefinition = "TEXT")
    String updated_at;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "replyTopic_id"), name = "replyTopic_id")
    Topic topic;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "replyUser_id"), name = "replyUser_id")
    User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return Objects.equals(id, reply.id) &&
                Objects.equals(content, reply.content) &&
                Objects.equals(created_at, reply.created_at) &&
                Objects.equals(updated_at, reply.updated_at) &&
                Objects.equals(topic, reply.topic) &&
                Objects.equals(user, reply.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created_at, updated_at, topic, user);
    }
}
