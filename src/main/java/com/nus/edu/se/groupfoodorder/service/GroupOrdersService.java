package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.exception.AuthenticationException;
import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.exception.ServiceNotAvailableException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;
import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderResponse;
import com.nus.edu.se.groupfoodorder.dto.JointGroupFoodOrderDTO;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.mapper.GroupOrderMapper;
import com.nus.edu.se.menu.dto.MenuResponse;
import com.nus.edu.se.menu.service.MenuService;
import com.nus.edu.se.order.dao.OrderDetailRepository;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.dto.OrderDetailDTO;
import com.nus.edu.se.order.mapper.OrderDetailMapper;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import com.nus.edu.se.restaurant.dto.RestaurantResponse;
import com.nus.edu.se.restaurant.service.RestaurantService;
import com.nus.edu.se.user.dto.UsersResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    JwtTokenInterface jwtTokenInterface;

    public void setGroupOrderMapper(GroupOrderMapper mapper) {
        this.groupOrderMapper = mapper;
    }

    public void setOrderDetailMapper(OrderDetailMapper mapper) {
        this.orderDetailMapper = mapper;
    }

    public boolean groupFoodOrderNotValidToJoin(GroupFoodOrder groupFoodOrder) {
        return groupFoodOrder.getStatus() == GroupFoodOrder.Status.SUBMITTED_TO_RESTAURANT || (groupFoodOrder.getStatus() == GroupFoodOrder.Status.ORDER_CANCEL);
    }

   public List<GroupFoodOrderList> getAllGroupOrders(String token) throws AuthenticationException, ServiceNotAvailableException{
        return getAllGroupOrders(null, token);
   }

    public List<GroupFoodOrderList> getAllGroupOrders(String userId, String token) throws AuthenticationException, ServiceNotAvailableException{
        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
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
                if (!orderList.isEmpty()) { // to handle inconsistent of group_food_orders and order_items due to any failure
                    List<String> userIds = orderList.stream().map(orderItem -> orderItem.getUserId().toString()).toList();
                    Order firstOrder = orderList.get(0);

                    // find the restaurant that assocaite to the order
                    try {
                        String restaurantId = firstOrder.getRestaurantId();

                        RestaurantResponse restaurant = restaurantService.getRestaurantById(restaurantId, token);

                        if (restaurant != null) {
                            dto.setRestaurantId(restaurant.getId());
                            dto.setRestaurantName(restaurant.getRestaurantName());
                            dto.setLocation(restaurant.getLocation());
                            dto.setRating(String.valueOf(restaurant.getRating()));
                            dto.setImgUrl(restaurant.getRestaurantImgURL());
                        }
                    } catch (ServiceNotAvailableException e) {
                        throw new ServiceNotAvailableException("Restaurant service is currently unavailable. Please try again later.", e);
                    }

                    if (!userIds.contains(userId)) {
                        groupFoodOrderList.add(dto);
                    }
                }
            }

            return groupFoodOrderList;
        } else {
            throw new AuthenticationException("User is not authenticated to getAllGroupOrders!");
        }
    }

    @Transactional
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteOrder(String orderId)  {
        Order order = orderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new DataNotFoundException("Order Not Found."));
        orderDetailsRepository.deleteAll(orderDetailsRepository.findOrderDetailsByOrder(order));
        orderRepository.deleteById(UUID.fromString(orderId));
    }

    public List<GroupFoodOrderResponse> getGroupOrderListByUserId(UsersResponse user, String token) {

        List<GroupFoodOrderResponse> orderDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findOrderByUserId(user.getUserId());

        if (!orderList.isEmpty()) {
            for (Order order : orderList) {
                GroupFoodOrder groupFoodOrder = order.getGroupFoodOrder();

                List<String> orderListInGroupFoodOrder = orderRepository.findOrderByGroupFoodOrderOrderByCreatedTimeAsc(groupFoodOrder).stream().map(v -> v.getId().toString()).toList();
                try {
                    RestaurantResponse restaurant = restaurantService.getRestaurantById(order.getRestaurantId(), token);

                    // convert to OrderItemDto
                    GroupFoodOrderResponse groupFoodOrderDTO = groupOrderMapper.fromGroupFoodOrderToGroupFoodOrderDTO(order,
                            groupFoodOrder, restaurant);

                    List<OrderDetail> orderDetailList = orderDetailsRepository.findOrderDetailsByOrder(order);
                    List<OrderDetailDTO> orderDetailDtoList = new ArrayList<>();

                    float totalPrice = 0;
                    for (OrderDetail orderDetail : orderDetailList) {
                        if (orderDetail != null) {
                            MenuResponse menu = menuService.findById(orderDetail.getMenuId(), token);
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
                    groupFoodOrderDTO.setRestaurantName(restaurant.getRestaurantName());
                    orderDtoList.add(groupFoodOrderDTO);
                } catch (ServiceNotAvailableException e) {
                    throw new ServiceNotAvailableException("Restaurant service is currently unavailable. Please try again later.", e);
                }
            }
        }

        return orderDtoList;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public ResponseEntity<JointGroupFoodOrderDTO> getInfoForGroupOrder(UUID groupOrderId, String token){
        JointGroupFoodOrderDTO jointGroupFoodOrder = new JointGroupFoodOrderDTO();

        GroupFoodOrder order = groupOrderRepository.findById(groupOrderId).orElseThrow(() -> new DataNotFoundException("Group Order Not Found."));

        List<Order> orderList = orderRepository.findOrderByGroupFoodOrderOrderByCreatedTimeAsc(order);
        if (!orderList.isEmpty()) {
            Order firstOrder = orderList.get(0);
            String restaurantId = firstOrder.getRestaurantId();
            jointGroupFoodOrder.setRestaurantId(restaurantId);
            jointGroupFoodOrder.setMainOrderId(firstOrder.getId().toString());
            jointGroupFoodOrder.setNumberOfUsers(orderList.size());
        }

        jointGroupFoodOrder.setStatus(order.getStatus().toString());
        jointGroupFoodOrder.setDeliveryLocation(order.getDeliveryLocation());
        jointGroupFoodOrder.setGroupOrderDeliveryFee(order.getDeliveryFee());

        return ResponseEntity.ok(jointGroupFoodOrder);
    }
}
