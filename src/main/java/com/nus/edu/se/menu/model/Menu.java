package com.nus.edu.se.menu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @UuidGenerator
    @Column(name = "menu_id", nullable = false)
    private UUID id;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private float menuPrice;

    @Column(name = "category")
    private String category;

    @Column(name = "available", nullable = false)
    private boolean available = true;

    @Column(name = "menu_image_url", nullable = false)
    private String menuImageURL;

}
