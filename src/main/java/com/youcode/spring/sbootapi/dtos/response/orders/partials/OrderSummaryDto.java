package com.youcode.spring.sbootapi.dtos.response.orders.partials;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youcode.spring.sbootapi.dtos.response.addresses.AddressExcludeUserDto;
import com.youcode.spring.sbootapi.enums.OrderStatus;
import com.youcode.spring.sbootapi.models.Order;
import com.youcode.spring.sbootapi.enums.OrderStatus;
import com.youcode.spring.sbootapi.models.Order;

import java.time.ZonedDateTime;

public class OrderSummaryDto {

    private final OrderStatus orderStatus;

    @JsonProperty("addresss")
    // @JsonIgnore
    private final AddressExcludeUserDto addressDto;
    private final String trackingNumber;
    private final Long orderItemsCount;
    private Long id;

    public String address;
    private String city;
    private String country;
    private Integer zipCode;

    private double total;

    private ZonedDateTime createdAt;


    public OrderSummaryDto(Long id, String trackingNumber, OrderStatus orderStatus, double totalAmount, Long orderItemsCount, AddressExcludeUserDto addressDto) {
        this.id = id;
        this.total = totalAmount;
        this.orderItemsCount = orderItemsCount;
        if (trackingNumber == null)
            this.orderStatus = OrderStatus.PROCESSED;
        else
            this.orderStatus = orderStatus;

        if (trackingNumber == null)
            this.trackingNumber = "not defined yet";
        else
            this.trackingNumber = trackingNumber;

        this.addressDto = addressDto;
    }

    public static OrderSummaryDto build(Order order) {
        return new OrderSummaryDto(
                order.getId(),
                order.getTrackingNumber(),
                order.getOrderStatus(),
                order.getTotalAmount(),
                order.getOrderItemsCount(),
                AddressExcludeUserDto.build(order.getAddress())
        );
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public AddressExcludeUserDto getAddressDto() {
        return addressDto;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public Long getOrderItemsCount() {
        return orderItemsCount;
    }

}
