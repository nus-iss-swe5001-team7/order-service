package com.nus.edu.se.order.mapper;

import com.nus.edu.se.groupfoodorder.dto.OrderDetailDTO;
import com.nus.edu.se.menu.dto.MenuResponse;
import com.nus.edu.se.menu.service.MenuService;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class OrderDetailMapper {
    @Autowired
    private MenuService menuService;

    public OrderDetail fromOrderDetailDTOToOrderDetail(OrderDetailDTO orderDetailDTO, Order order) {
        OrderDetail orderDetail = new OrderDetail();
        MenuResponse menu = menuService.findById(orderDetailDTO.getMenuId());
        orderDetail.setMenuId(menu.getId());
        orderDetail.setOrderItemId(order.getId());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        return orderDetail;
    }

    public List<OrderDetailDTO> fromOrderDetailToOrderDetailDTO(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(orderDetail -> {
                    MenuResponse menu = menuService.findById(orderDetail.getMenuId());
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
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
