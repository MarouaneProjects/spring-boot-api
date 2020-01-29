package com.youcode.spring.sbootapi.dtos.response.pages;

import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.dtos.response.categories.SingleCategoryDto;
import com.youcode.spring.sbootapi.dtos.response.tags.SingleTagDto;
import com.youcode.spring.sbootapi.models.Category;
import com.youcode.spring.sbootapi.models.Tag;

import java.util.ArrayList;
import java.util.List;

public class HomeDtoResponse extends SuccessResponse {
    private final List<SingleTagDto> tags;
    private final List<SingleCategoryDto> categories;

    public HomeDtoResponse(List<SingleTagDto> tagDtos, List<SingleCategoryDto> categoryDtos) {
        this.tags = tagDtos;
        this.categories = categoryDtos;
    }

    public List<SingleTagDto> getTags() {
        return tags;
    }

    public List<SingleCategoryDto> getCategories() {
        return categories;
    }

    public static HomeDtoResponse build(List<Tag> tags, List<Category> categories) {
        List<SingleCategoryDto> categoryDtos = new ArrayList<>(categories.size());
        List<SingleTagDto> tagDtos = new ArrayList<>(tags.size());
        for (Tag tag : tags) {
            tagDtos.add(SingleTagDto.build(tag, true, true));
        }

        for (Category category : categories) {
            categoryDtos.add(SingleCategoryDto.build(category, true, true));
        }

        return new HomeDtoResponse(tagDtos, categoryDtos);
    }
}
