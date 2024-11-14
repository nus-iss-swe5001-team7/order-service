package com.nus.edu.se.order.mapper;

import com.nus.edu.se.order.dto.OrderDetailDTO;
import com.nus.edu.se.menu.dto.MenuResponse;
import com.nus.edu.se.menu.service.MenuService;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class OrderDetailMapper {
    @Autowired
    private MenuService menuService;

    public OrderDetail fromOrderDetailDTOToOrderDetail(OrderDetailDTO orderDetailDTO, Order order, String token) {
        OrderDetail orderDetail = new OrderDetail();
        MenuResponse menu = menuService.findById(orderDetailDTO.getMenuId(), token);
        orderDetail.setMenuId(menu.getId());
//        orderDetail.setOrderItemId(order.getId());
        orderDetail.setOrder(order);
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPreferences(orderDetailDTO.getPreferences());
        return orderDetail;
    }

    public List<OrderDetailDTO> fromOrderDetailToOrderDetailDTO(List<OrderDetail> orderDetails, String token) {
        return orderDetails.stream()
                .map(orderDetail -> {
                    MenuResponse menu = menuService.findById(orderDetail.getMenuId(), token);
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setMenuId(orderDetail.getMenuId());
                    orderDetailDTO.setPrice(menu.getMenuPrice());
                    orderDetailDTO.setName(menu.getMenuName());
                    orderDetailDTO.setQuantity(orderDetail.getQuantity());
                    orderDetailDTO.setDescription(menu.getDescription());
                    orderDetailDTO.setImgUrl(menu.getMenuImageURL());
                    orderDetailDTO.setPreferences(orderDetail.getPreferences());
                    return orderDetailDTO;
                })
                .collect(Collectors.toList());
    }

    public OrderDetailDTO fromOrderDetailToOrderDetailDTO(OrderDetail orderDetail,MenuResponse menu) {
        OrderDetailDTO orderDetailDto = new OrderDetailDTO();

        orderDetailDto.setQuantity(orderDetail.getQuantity());
        orderDetailDto.setName(menu.getMenuName());
        orderDetailDto.setImgUrl(menu.getMenuImageURL());
        orderDetailDto.setDescription(menu.getDescription());
        orderDetailDto.setPrice(menu.getMenuPrice());
        orderDetailDto.setPreferences(orderDetail.getPreferences());
        return orderDetailDto;
    }
}
