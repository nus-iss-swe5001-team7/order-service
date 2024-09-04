package com.nus.edu.se.order.mapper;

import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.grouopfoodorder.dto.OrderDetailResponse;
import com.nus.edu.se.menu.dao.MenuRepository;
import com.nus.edu.se.menu.model.Menu;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDetailMapper {
    @Autowired
    private MenuRepository menuRepository;

    public OrderDetail fromOrderDetailDTOToOrderDetail(OrderDetailResponse orderDetailDTO, Order order) {
        OrderDetail orderDetail = new OrderDetail();
        Menu menu = menuRepository.findById(orderDetailDTO.getMenuId()).orElseThrow(()-> new DataNotFoundException("Menu not found - fromOrderDetailDTOToOrderDetail."));
        orderDetail.setMenuId(menu.getId());
        orderDetail.setOrderItemId(order.getId());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        return orderDetail;
    }

    public List<OrderDetailResponse> fromOrderDetailToOrderDetailDTO(List<OrderDetailResponse> orderDetails) {
        return orderDetails.stream()
                .map(orderDetail -> {
                    Menu menu = menuRepository.findById(orderDetail.getMenuId()).orElseThrow(()-> new DataNotFoundException("Menu not found - fromOrderDetailToOrderDetailDTO."));

                    OrderDetailResponse orderDetailDTO = new OrderDetailResponse();
                    orderDetailDTO.setMenuId(orderDetail.getMenuId());
                    orderDetailDTO.setPrice(menu.getMenuPrice());
                    orderDetailDTO.setName(menu.getMenuName());
                    orderDetailDTO.setQuantity(orderDetail.getQuantity());
                    orderDetailDTO.setDescription(menu.getDescription());
                    orderDetailDTO.setImgUrl(menu.getMenuImageURL());
                    return orderDetailDTO;
                })
                .collect(Collectors.toList());
    }
}
