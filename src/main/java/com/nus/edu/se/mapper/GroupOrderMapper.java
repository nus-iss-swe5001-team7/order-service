package com.nus.edu.se.mapper;

import org.springframework.stereotype.Component;

@Component
public class GroupOrderMapper {

   /* public GroupFoodOrderDTO fromGroupFoodOrderToGroupFoodOrderDTO(Order order, GroupFoodOrder groupFoodOrder) {

        UUID orderID = order.getId(); // orderId
        String orderStatus = groupFoodOrder != null ? groupFoodOrder.getStatus().name() : null;
        Date orderCreatedTime = order != null ? order.getCreatedTime() : null;

        GroupFoodOrderDTO groupFoodOrderDTO = new GroupFoodOrderDTO();
        groupFoodOrderDTO.setOrderId(orderID);
        groupFoodOrderDTO.setOrderTime(orderCreatedTime);
        groupFoodOrderDTO.setOrderStatus(orderStatus);
        groupFoodOrderDTO.setGroupFoodOrderId(groupFoodOrder.getId());//for update group order status

        //get Location and Rating for order sorting.
        Restaurant restaurant = order.getRestaurant();
        groupFoodOrderDTO.setLocation(restaurant.getLocation());
        groupFoodOrderDTO.setRating(restaurant.getRating());

        //System.out.println("restaurant.getRating():"+restaurant.getRating());
        return groupFoodOrderDTO;
    }*/

}
