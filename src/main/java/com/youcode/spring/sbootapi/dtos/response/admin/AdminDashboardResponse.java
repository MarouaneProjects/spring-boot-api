package com.youcode.spring.sbootapi.dtos.response.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.dtos.response.comments.CommentListResponse;
import com.youcode.spring.sbootapi.dtos.response.orders.OrderListResponse;
import com.youcode.spring.sbootapi.dtos.response.products.ProductListResponse;
import com.youcode.spring.sbootapi.models.Comment;
import com.youcode.spring.sbootapi.models.Order;
import com.youcode.spring.sbootapi.models.Product;
import com.youcode.spring.sbootapi.models.User;
import org.springframework.data.domain.Page;

public class AdminDashboardResponse extends SuccessResponse {

    @JsonProperty("products")
    private ProductListResponse productDataSection;
    @JsonProperty("comments")
    private CommentListResponse commentsData;
    @JsonProperty("orders")
    private OrderListResponse orderDataSection;
    @JsonProperty("users")
    private UsersDataSection usersData;

    public AdminDashboardResponse(ProductListResponse productListResponse, CommentListResponse commentListResponse, OrderListResponse orderListResponse, UsersDataSection usersData) {
        this.productDataSection = productListResponse;
        this.commentsData = commentListResponse;
        this.orderDataSection = orderListResponse;
        this.usersData = usersData;
    }

    public static AdminDashboardResponse build(Page<Product> products, Page<Comment> comments, Page<Order> orders,
                                               Page<User> users, String productsBasePath,
                                               String commentsBasePath, String ordersBasePath, String usersBasePath) {
        return new AdminDashboardResponse(
                ProductListResponse.build(products, productsBasePath),
                CommentListResponse.build(comments, commentsBasePath),
                OrderListResponse.build(orders, ordersBasePath),
                UsersDataSection.build(users, usersBasePath)
        );
    }

    public UsersDataSection getUsersData() {
        return usersData;
    }

    public void setUsersData(UsersDataSection usersData) {
        this.usersData = usersData;
    }


    public CommentListResponse getCommentsData() {
        return commentsData;
    }

    public void setCommentsData(CommentListResponse commentsData) {
        this.commentsData = commentsData;
    }

    public OrderListResponse getOrderDataSection() {
        return orderDataSection;
    }

    public void setOrderDataSection(OrderListResponse orderListResponse) {
        this.orderDataSection = orderListResponse;
    }

    public void setUsersDataSection(UsersDataSection usersDataSection) {
        this.usersData = usersDataSection;
    }
}
