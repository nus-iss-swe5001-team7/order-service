package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.mapper.GroupOrderMapper;
import com.nus.edu.se.order.dao.OrderDetailRepository;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.restaurant.dto.RestaurantResponse;
import com.nus.edu.se.restaurant.service.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GroupOrdersService {

    private final WebClient.Builder webClientBuilder;
    @Autowired
    private OrderDetailRepository orderDetailsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GroupOrderRepository groupOrderRepository;

    @Autowired
    private GroupOrderMapper groupOrderMapper;

    @Autowired
    private RestaurantService restaurantService;

    public boolean groupFoodOrderNotValidToJoin(GroupFoodOrder groupFoodOrder) {
        return groupFoodOrder.getStatus() == GroupFoodOrder.Status.SUBMITTED_TO_RESTAURANT || (groupFoodOrder.getStatus() == GroupFoodOrder.Status.ORDER_CANCEL);
    }

   public List<GroupFoodOrderList> getAllGroupOrders() {
       return getAllGroupOrders(null);
   }

    public List<GroupFoodOrderList> getAllGroupOrders(String userId) {
        List<GroupFoodOrder> groupFoodOrders = groupOrderRepository.findAll();
        List<GroupFoodOrderList> groupFoodOrderList = new ArrayList<>();

        for (GroupFoodOrder order : groupFoodOrders) {
            GroupFoodOrderList dto = new GroupFoodOrderList();
            dto.setGroupFoodOrderId(order.getId());
            dto.setDeliveryTime(order.getGroupOrderDeliveryTime());
            dto.setOrderTime(order.getGroupOrderCreateTime());
            dto.setOrderStatus(order.getStatus().toString());
            dto.setDeliveryLocation(order.getDeliveryLocation());

            // find the order that associate to the group order
            List<Order> orderList = orderRepository.findOrderByGroupFoodOrderOrderByCreatedTimeAsc(order);
            if (orderList.size()>0) { // to handle inconsistent of group_food_orders and order_items if any failure
                List<String> userIds = orderList.stream().map(orderItem -> orderItem.getId().toString()).toList();
                Order firstOrder = orderList.get(0);

                // find the restaurant that assocaite to the order
                String restaurantId = firstOrder.getRestaurantId();
                RestaurantResponse restaurant = restaurantService.getRestaurantById(restaurantId);

                if (restaurant != null) {
                    dto.setRestaurantId(restaurant.getId());
                    dto.setRestaurantName(restaurant.getRestaurantName());
                    dto.setLocation(restaurant.getLocation());
                    dto.setRating(String.valueOf(restaurant.getRating()));
                    dto.setImgUrl(restaurant.getRestaurantImgURL());
                }

                if (!userIds.contains(userId)) {
                    groupFoodOrderList.add(dto);
                }
            }
        }

        return groupFoodOrderList;
    }

    @org.springframework.transaction.annotation.Transactional
    public GroupFoodOrder updateStatus(String orderId, GroupFoodOrder.Status status) {
        UUID orderUUID = UUID.fromString(orderId);
        GroupFoodOrder groupFoodOrder = new GroupFoodOrder();
        Optional<GroupFoodOrder> groupOrder = groupOrderRepository.findById(orderUUID);

        if (groupOrder.isPresent()) {
            groupFoodOrder = groupOrder.get();
            groupFoodOrder.setId(orderUUID);
            groupFoodOrder.setStatus(status);
            groupFoodOrder = groupOrderRepository.saveAndFlush(groupFoodOrder);
        } else {
            throw new RuntimeException("Not found GroupOrder with orderId = " + orderId);
        }
        return groupFoodOrder;
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new DataNotFoundException("Order Not Found."));
        orderDetailsRepository.deleteAll(orderDetailsRepository.findOrderDetailsByOrderItemId(UUID.fromString(orderId)));
        orderRepository.deleteById(UUID.fromString(orderId));
    }

}
