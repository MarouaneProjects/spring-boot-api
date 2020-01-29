package com.youcode.spring.sbootapi.repository;

import com.youcode.spring.sbootapi.models.FileUpload;
import com.youcode.spring.sbootapi.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

    @Query("select new com.youcode.spring.sbootapi.models.extensions.ProductImageExtension(f.product.id, f.filePath) from FileUpload f where f.product.id in :productIds")
    List<ProductImage> findAllWhereProductIdIn(List<Long> productIds);
}
