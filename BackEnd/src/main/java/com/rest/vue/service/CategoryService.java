package com.rest.vue.service;

import com.rest.vue.entities.Category;

import java.util.List;
import java.util.Random;

public interface CategoryService {

    List<Category> findAll();

    Category findBySlug(String slug);

    Integer randomId();
}
