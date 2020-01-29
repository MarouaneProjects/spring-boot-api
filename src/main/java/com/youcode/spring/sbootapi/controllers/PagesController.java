package com.youcode.spring.sbootapi.controllers;

import com.youcode.spring.sbootapi.dtos.response.pages.HomeDtoResponse;
import com.youcode.spring.sbootapi.models.Category;
import com.youcode.spring.sbootapi.models.Tag;
import com.youcode.spring.sbootapi.services.CategoryService;
import com.youcode.spring.sbootapi.services.TagService;
import com.youcode.spring.sbootapi.dtos.response.pages.HomeDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("")
@RestController
public class PagesController {

    @Autowired
    private final CategoryService categoriesService;
    @Autowired
    private final TagService tagService;

    @Autowired
    public PagesController(CategoryService categoriesService, TagService tagService) {
        this.categoriesService = categoriesService;
        this.tagService = tagService;
    }

    @GetMapping(path = {"", "/home"})
    public HomeDtoResponse home() {
        List<Category> categories = categoriesService.getAllSummary();
        List<Tag> tags = tagService.getAllTags();
        return HomeDtoResponse.build(tags, categories);
    }
}
