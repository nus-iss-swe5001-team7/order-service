package com.nus.edu.se.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    private String menuId;

    private Integer quantity;

    private String name;

    private float price;

    private String imgUrl;

    private String description;

    private UUID orderItemId;
}
