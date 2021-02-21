package com.rest.vue.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table("category")
public class Category {
    @Id
    int _id;

    String color;
    String description;
    String slug;
    String title;

}
