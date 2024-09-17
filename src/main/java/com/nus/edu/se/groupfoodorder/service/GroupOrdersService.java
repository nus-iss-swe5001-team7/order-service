package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.dto.RestaurantResponse;
import com.nus.edu.se.groupfoodorder.dto.UsersResponse;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.mapper.GroupOrderMapper;
import com.nus.edu.se.order.dao.OrderDetailRepository;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.model.Order;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public boolean groupFoodOrderNotValidToJoin(GroupFoodOrder groupFoodOrder) {
        return groupFoodOrder.getStatus() == GroupFoodOrder.Status.SUBMITTED_TO_RESTAURANT || (groupFoodOrder.getStatus() == GroupFoodOrder.Status.ORDER_CANCEL);
    }

    public UsersResponse getUserById(UUID userId){
         UsersResponse usersResponse = webClientBuilder.build().get()
//                .uri("http://localhost:8080/user/getUserById/{id}", userId)
                .uri("http://user-service/user/getUserById/{id}", userId)
//                .uri(uriBuilder -> uriBuilder.path("http://localhost:8080/user/getUserById")
//                        .queryParam("id",userId)
//                        .build())
//                 .uri("http://localhost:8080/user/getUserById/{id}", uriBuilder -> uriBuilder
//                         .queryParam("id",userId)
//                         .build() )
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    return Mono.error(new RuntimeException("GroupOrdersService getUserById Client error: " + response.statusCode()));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    return Mono.error(new RuntimeException("GroupOrdersService getUserById Server error: " + response.statusCode()));
                })
        .bodyToMono(UsersResponse.class).block();
        return usersResponse;
    }

    public RestaurantResponse getRestaurantById(UUID restaurantById){
        RestaurantResponse restaurantResponse = webClientBuilder.build().get()
                .uri("http://restaurant-service/restaurant/getRestaurantById/{id}", restaurantById)
//                .uri("http://localhost:8090/restaurant/getRestaurantById/{id}", restaurantById)
//                .uri(uriBuilder -> uriBuilder.path("http://localhost:8090/restaurant/getRestaurantById").queryParam("id",restaurantById).build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    return Mono.error(new RuntimeException("GroupOrdersService getRestaurantById Client error: " + response.statusCode()));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    return Mono.error(new RuntimeException("GroupOrdersService getRestaurantById Server error: " + response.statusCode()));
                })
                .bodyToMono(RestaurantResponse.class).block();
        return restaurantResponse;
    }


    /**public List<GroupFoodOrderResponse> getGroupOrderListByUserId(UUID userId) {

        List<GroupFoodOrderResponse> orderDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findOrderByUserId(userId);

        if (!orderList.isEmpty()) {
            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                GroupFoodOrder groupFoodOrder = order.getGroupFoodOrder();

                List<String> orderListInGroupFoodOrder = orderRepository.findOrderByGroupFoodOrderOrderByCreatedTimeAsc(groupFoodOrder).stream().map(v -> v.getId().toString()).toList();

                // convert to OrderItemDto
                GroupFoodOrderResponse groupFoodOrderDTO = groupOrderMapper.fromGroupFoodOrderToGroupFoodOrderDTO(order,
                        groupFoodOrder);

                List<OrderDetail> orderDetailList = orderDetailsRepository.findOrderDetailsByOrder(order);
                List<OrderDetailDTO> orderDetailDtoList = new ArrayList<OrderDetailDTO>();

                float totalPrice = 0;
                for (int j = 0; j < orderDetailList.size(); j++) {
                    OrderDetail orderDetail = orderDetailList.get(j);

                    if (orderDetail != null) {
                        Menu menu = orderDetail.getMenu();
                        float totalPriceOrderDetail = menu.getMenuPrice() * orderDetail.getQuantity();
                        // System.out.println("totalPriceOrderDetail: " + totalPriceOrderDetail);
                        totalPrice = totalPrice + totalPriceOrderDetail;
                        // convert to OrderDetailsDto
                        OrderDetailDTO orderDetailDto = orderDetailMapper.fromOrderDetailToOrderDetailDTO(orderDetail,
                                menu);
                        orderDetailDtoList.add(orderDetailDto);
                    }
                    // System.out.println("totalPrice: " + totalPrice);
                    groupFoodOrderDTO.setTotalPrice(totalPrice);
                }

                // System.out.println("groupFoodOrderDTO setTotalPrice: " +
                // groupFoodOrderDTO.getTotalPrice());
                groupFoodOrderDTO.setOrderDetailDtoList(orderDetailDtoList);
                groupFoodOrderDTO.setOrderIdsList(orderListInGroupFoodOrder);
                groupFoodOrderDTO.setDeliveryLocation(groupFoodOrder.getDeliveryLocation());
                groupFoodOrderDTO.setDeliveryFee(order.getDeliveryFee());
                groupFoodOrderDTO.setRestaurantName(order.getRestaurant().getRestaurantName());
                orderDtoList.add(groupFoodOrderDTO);
            }
        }

        return orderDtoList;
    }
**/
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
