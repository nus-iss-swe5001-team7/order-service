package com.nus.edu.se.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @UuidGenerator
    @Column(name = "order_detail_id", nullable = false)
    private UUID id;

//    @Column(name = "order_item_id", nullable = false)
//    private UUID orderItemId;
    @ManyToOne
    @JoinColumn(name = "order_item_id", nullable = false)
    private Order order;

    @Column(name = "menu_id", nullable = false)
    private String menuId;

    @Column(name = "order_item_quantity")
    private Integer quantity;
}
