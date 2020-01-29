package com.youcode.spring.sbootapi.services.interfaces;

import com.youcode.spring.sbootapi.errors.exceptions.ResourceNotFoundException;
import com.youcode.spring.sbootapi.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductsService extends CrudService<Product> {

    Page<Product> findAllForSummary(int page, int pageSize);

    Product findBySlug(String slug);

    Product findById(Long id) throws ResourceNotFoundException;

    Product findById(String slug);

    Page<Product> findByTagName(String tagName, int page, int count);

    Product create(Product product);

    Product create(Product product, String tags, String categories);

    Product save(Product product);

    List<Product> findBasicInfoWhereProductIds(List<Long> productIds);

    Page<Product> getByCategory(String categoryName, int page, int pageSize);
}
