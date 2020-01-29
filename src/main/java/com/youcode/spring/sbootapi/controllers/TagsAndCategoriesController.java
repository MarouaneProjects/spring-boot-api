package com.youcode.spring.sbootapi.controllers;

import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.dtos.response.categories.CategoriesListResponse;
import com.youcode.spring.sbootapi.dtos.response.categories.SingleCategoryDto;
import com.youcode.spring.sbootapi.dtos.response.tags.SingleTagDto;
import com.youcode.spring.sbootapi.dtos.response.tags.TagsListResponse;
import com.youcode.spring.sbootapi.enums.CrudOperation;
import com.youcode.spring.sbootapi.errors.exceptions.PermissionDeniedException;
import com.youcode.spring.sbootapi.models.Category;
import com.youcode.spring.sbootapi.models.Tag;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.CategoryService;
import com.youcode.spring.sbootapi.services.StorageService;
import com.youcode.spring.sbootapi.services.TagService;
import com.youcode.spring.sbootapi.services.auth.AuthorizationService;
import com.youcode.spring.sbootapi.services.auth.UsersService;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.dtos.response.tags.SingleTagDto;
import com.youcode.spring.sbootapi.dtos.response.tags.TagsListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class TagsAndCategoriesController {

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping("tags")
    public TagsListResponse getTags(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "page_size", defaultValue = "8") int pageSize,
                                    HttpServletRequest request) {

        List<Tag> tags = tagService.getAllTags();
        return TagsListResponse.build(tags, request.getRequestURI());
    }


    @GetMapping("categories")
    public CategoriesListResponse getCategories(@RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "page_size", defaultValue = "8") int pageSize,
                                                HttpServletRequest request) {
        // Page<Category> categories = categoryService.findLatest(page, pageSize);
        List<Category> categories = categoryService.getAllSummary();
        return CategoriesListResponse.build(categories, request.getRequestURI());
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("tags")
    public ResponseEntity<AppResponse> createTag(HttpServletRequest request, @RequestParam("images[]") MultipartFile[] uploadingFiles) {

        try {
            User user = usersService.getCurrentLoggedInUser();
            if (!this.authorizationCheckOnTags(CrudOperation.CREATE, user))
                throw new PermissionDeniedException("You are not allowed to create products");

            String name = request.getParameter("name");
            String description = request.getParameter("description");

            List<File> files = storageService.upload(uploadingFiles, "/images/tags");

            Tag tag = tagService.create(name, description, files);
            return new ResponseEntity<>(SingleTagDto.build(tag, true, true), HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')") // Remember, if you do not set the prefix, it will add ROLE_ prefix for you
    @PostMapping("categories")
    public ResponseEntity<AppResponse> createCategory(HttpServletRequest request, @RequestParam("images[]") MultipartFile[] uploadingFiles) {

        try {
            User user = usersService.getCurrentLoggedInUser();
            if (!this.authorizationCheckOnCategories(CrudOperation.CREATE, user))
                throw new PermissionDeniedException("You are not allowed to create products");

            String name = request.getParameter("name");
            String description = request.getParameter("description");

            List<File> files = storageService.upload(uploadingFiles, "/images/categories");

            Category category = categoryService.create(name, description, files);
            return new ResponseEntity<>(SingleCategoryDto.build(category, true, true), HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean authorizationCheckOnCategories(CrudOperation crudOperation, User user) {
        return authorizationCheckCategories(crudOperation, user, null);
    }

    private boolean authorizationCheckOnTags(CrudOperation operation, User user) {
        return this.authorizationCheckOnTags(operation, user, null);
    }

    private boolean authorizationCheckOnTags(CrudOperation operation, User user, Tag tag) {
        switch (operation) {
            case CREATE:
                return authorizationService.canCreateTags(user);
            case UPDATE:
                return authorizationService.canUpdateTags(user, tag);
            case DELETE:
                return authorizationService.canDeleteTags(user, tag);
            case READ:
                return authorizationService.canReadTags(user);
            default:
                return false;
        }
    }

    private boolean authorizationCheckCategories(CrudOperation operation, User user, Category category) {
        switch (operation) {
            case CREATE:
                return authorizationService.canCreateCategories(user);
            case UPDATE:
                return authorizationService.canUpdateCategories(user, category);
            case DELETE:
                return authorizationService.canDeleteCategories(user);
            case READ:
                return authorizationService.canReadCategories(user);
            default:
                return false;
        }
    }

}
