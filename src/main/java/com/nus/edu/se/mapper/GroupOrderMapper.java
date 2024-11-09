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
        Date orderCreatedTime = groupFoodOrder != null && groupFoodOrder.getGroupOrderCreateTime() != null ? groupFoodOrder.getGroupOrderCreateTime() : null;

        GroupFoodOrderResponse groupFoodOrderDTO = new GroupFoodOrderResponse();
        groupFoodOrderDTO.setOrderId(orderID);
        groupFoodOrderDTO.setOrderTime(orderCreatedTime);
        groupFoodOrderDTO.setOrderStatus(orderStatus);
        groupFoodOrderDTO.setGroupFoodOrderId(groupFoodOrder.getId());//for update group order status

        //get Location and Rating for order sorting.
//        RestaurantResponse restaurant = new RestaurantResponse();//order.getRestaurant();
        groupFoodOrderDTO.setLocation(restaurant.getLocation());
        groupFoodOrderDTO.setRating(restaurant.getRating());
        groupFoodOrderDTO.setRestaurantAddress(restaurant.getRestaurantAddress());
        groupFoodOrderDTO.setRestaurantLongitude(restaurant.getRestaurantLongitude());
        groupFoodOrderDTO.setRestaurantLatitude(restaurant.getRestaurantLatitude());

        System.out.println("restaurant.getRating():"+restaurant.getRating());
        return groupFoodOrderDTO;
    }

}
