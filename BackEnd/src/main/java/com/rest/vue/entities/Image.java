package com.rest.vue.entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;


@Entity
@Table(name = "image")
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lob
    @Column(name ="data")
    byte[] photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
        Image image = (Image) o;
        return Objects.equals(id, image.id) &&
                Arrays.equals(photo, image.photo) &&
                Objects.equals(user, image.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, user);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
