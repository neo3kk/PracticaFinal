package com.rest.vue.entities;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", columnDefinition = "TEXT")
    String title;

    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    @Column(name = "views")
    Integer views;

    @Column(name = "created_at", columnDefinition = "TEXT")
    String created_at;

    @Column(name = "updated_at", columnDefinition = "TEXT")
    String updated_at;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "topicCategory_id"), name = "topicCategory_id")
    Category category;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Reply> replies;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "topicUser_id"), name = "topicUser_id")
    User user;

    @Column(name = "number_of_replies")
    Integer number_of_replies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumber_of_replies() {
        return number_of_replies;
    }

    public void setNumber_of_replies(Integer number_of_replies) {
        this.number_of_replies = number_of_replies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) &&
                Objects.equals(title, topic.title) &&
                Objects.equals(content, topic.content) &&
                Objects.equals(views, topic.views) &&
                Objects.equals(created_at, topic.created_at) &&
                Objects.equals(updated_at, topic.updated_at) &&
                Objects.equals(category, topic.category) &&
                Objects.equals(replies, topic.replies) &&
                Objects.equals(user, topic.user) &&
                Objects.equals(number_of_replies, topic.number_of_replies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, views, created_at, updated_at, category, replies, user, number_of_replies);
    }
}
