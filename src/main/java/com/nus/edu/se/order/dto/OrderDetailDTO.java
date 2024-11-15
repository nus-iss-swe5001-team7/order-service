package com.nus.edu.se.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailDTO {

    private String menuId;

    private Integer quantity;

    private String name;

    private float price;

    private String imgUrl;

    private String description;

    private UUID orderItemId;

    private Map<String, String> preferences;
}
