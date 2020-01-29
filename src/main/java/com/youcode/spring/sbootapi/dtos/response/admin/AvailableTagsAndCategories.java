package com.youcode.spring.sbootapi.dtos.response.admin;

import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.dtos.response.categories.SingleCategoryDto;
import com.youcode.spring.sbootapi.dtos.response.tags.SingleTagDto;

import java.util.Collection;

public class AvailableTagsAndCategories extends SuccessResponse {
    private Collection<SingleTagDto> tags;

    //@NotNull Not implemented yet
    private Collection<SingleCategoryDto> categories;

    public Collection<SingleTagDto> getTags() {
        return tags;
    }

    public void setTags(Collection<SingleTagDto> tags) {
        this.tags = tags;
    }

    public Collection<SingleCategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(Collection<SingleCategoryDto> categories) {
        this.categories = categories;
    }
}
