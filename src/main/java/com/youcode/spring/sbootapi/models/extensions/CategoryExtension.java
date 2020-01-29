package com.youcode.spring.sbootapi.models.extensions;

import com.youcode.spring.sbootapi.models.Category;

public class CategoryExtension extends Category {

    Long productId;

    public CategoryExtension(Long id, String name, Long productId) {
        this.name = name;
        this.id = id;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
