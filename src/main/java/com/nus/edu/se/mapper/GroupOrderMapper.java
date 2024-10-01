package com.nus.edu.se.mapper;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderResponse;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.restaurant.dto.RestaurantResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
@Component
public class GroupOrderMapper {

     public GroupFoodOrderResponse fromGroupFoodOrderToGroupFoodOrderDTO(Order order, GroupFoodOrder groupFoodOrder, RestaurantResponse restaurant) {

        UUID orderID = order.getId(); // orderId
        String orderStatus = groupFoodOrder != null ? groupFoodOrder.getStatus().name() : null;
        Date orderCreatedTime = order != null ? order.getCreatedTime() : null;

        GroupFoodOrderResponse groupFoodOrderDTO = new GroupFoodOrderResponse();
        groupFoodOrderDTO.setOrderId(orderID);
        groupFoodOrderDTO.setOrderTime(orderCreatedTime);
        groupFoodOrderDTO.setOrderStatus(orderStatus);
        groupFoodOrderDTO.setGroupFoodOrderId(groupFoodOrder.getId());//for update group order status

        //get Location and Rating for order sorting.
//        RestaurantResponse restaurant = new RestaurantResponse();//order.getRestaurant();
        groupFoodOrderDTO.setLocation(restaurant.getLocation());
        groupFoodOrderDTO.setRating(restaurant.getRating());

        System.out.println("restaurant.getRating():"+restaurant.getRating());
        return groupFoodOrderDTO;
    }

}
