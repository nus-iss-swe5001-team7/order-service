package com.nus.edu.se.menu.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponse {
    private String id;
    private String menuName;
    private String restaurantId;
    private String description;
    private float menuPrice;
    private String category;
    private boolean available = true;
    private String menuImageURL;
}
