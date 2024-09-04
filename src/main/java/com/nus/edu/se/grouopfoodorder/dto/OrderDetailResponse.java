package com.nus.edu.se.grouopfoodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private UUID id;

    private Integer quantity;

    private UUID menuId;

    private String name;

    private float price;

    private String imgUrl;

    private String description;

    private UUID orderItemId;
}
