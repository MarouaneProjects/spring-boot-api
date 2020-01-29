package com.youcode.spring.sbootapi.controllers;


import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.dtos.response.comments.CommentListResponse;
import com.youcode.spring.sbootapi.dtos.response.comments.SingleCommentDto;
import com.youcode.spring.sbootapi.enums.CrudOperation;
import com.youcode.spring.sbootapi.errors.exceptions.PermissionDeniedException;
import com.youcode.spring.sbootapi.forms.CommentForm;
import com.youcode.spring.sbootapi.models.Comment;
import com.youcode.spring.sbootapi.models.Product;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.CommentsService;
import com.youcode.spring.sbootapi.services.ProductsService;
import com.youcode.spring.sbootapi.services.auth.AuthorizationService;
import com.youcode.spring.sbootapi.services.auth.UsersService;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final AuthorizationService authorizationService;
    private final CommentsService commentsService;

    private final UsersService usersService;
    private final ProductsService productsService;


    @Autowired
    public CommentsController(CommentsService commentsService,
                              AuthorizationService authorizationService,
                              UsersService usersService, ProductsService productsService) {
        this.commentsService = commentsService;
        this.authorizationService = authorizationService;
        this.productsService = productsService;
        this.usersService = usersService;

    }

    @GetMapping("/from_product/{product_slug}")
    public CommentListResponse index(@PathVariable("product_slug") String slug,
                                     HttpServletRequest request,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "page_size", defaultValue = "30") int pageSize) {

        Page<Comment> commentsPage = this.commentsService.getCommentsFromProductWithSlug(slug, page, pageSize);
        return CommentListResponse.build(commentsPage, "");
    }

    @GetMapping("{id}")
    public SingleCommentDto show(@PathVariable("id") Long id) {
        Comment comment = this.commentsService.findById(id);
        return SingleCommentDto.build(comment);
    }

    @GetMapping("/from_user/{user_id}")
    public CommentListResponse fromUser(@PathVariable("user_id") Long id, Model model,
                                        HttpServletRequest request,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "page_size", defaultValue = "30") int pageSize) {

        User user = this.usersService.findById(id);
        Page<Comment> comments = this.commentsService.getCommentsFromUserWithId(user.getId(), page, pageSize);
        return CommentListResponse.build(comments, "");
    }


    @PostMapping("/products/{slug}/comments")
    public ResponseEntity<AppResponse> create(@PathVariable("slug") String slug,
                                              @Valid @RequestBody CommentForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(new ErrorResponse(bindingResult.getModel()), HttpStatus.BAD_REQUEST);

        if (this.isNotAuthorized(CrudOperation.CREATE, null))
            throw new PermissionDeniedException("Permission denied");

        Product product = this.productsService.findById(slug);
        User user = this.usersService.getCurrentLoggedInUser();
        Comment comment = this.commentsService.save(form.getContent(), product, user);

        return ResponseEntity.ok(SingleCommentDto.build(comment));
    }


    @PutMapping("{id}")
    public ResponseEntity<AppResponse> update(@PathVariable("id") Long id, @RequestBody CommentForm form) {
        if (this.isNotAuthorized(CrudOperation.UPDATE, id))
            throw new PermissionDeniedException("You are not allowed to this comment");

        User user = this.usersService.getCurrentLoggedInUser();
        Comment comment = this.commentsService.update(id, form.getContent(), user);

        return ResponseEntity.ok(SingleCommentDto.build(comment));
    }


    @DeleteMapping("{id}")
    public AppResponse delete(@PathVariable("id") Long id) {
        if (this.isNotAuthorized(CrudOperation.DELETE, id))
            throw new PermissionDeniedException();

        Comment comment = this.commentsService.findById(id);
        if (comment == null)
            throw new PermissionDeniedException();

        this.commentsService.delete(comment);
        return new SuccessResponse("Deleted Succesfully");
    }

    private boolean isNotAuthorized(CrudOperation operation, Long id) {
        return !isAuthorized(operation, id);
    }

    private boolean isAuthorized(CrudOperation operation, Long id) {
        return this.isAuthorized(this.commentsService.findByIdNotThrow(id), operation);
    }

    private boolean isAuthorized(Comment comment, CrudOperation operation) {
        switch (operation) {
            case CREATE:
                return this.authorizationService.canCreateComments(usersService.getCurrentLoggedInUser());
            case UPDATE:
                return this.authorizationService.canUpdateComments(comment, usersService.getCurrentLoggedInUser());
            case DELETE:
                return this.authorizationService.canDeleteComments(comment);
            default:
                return false;
        }
    }

}
