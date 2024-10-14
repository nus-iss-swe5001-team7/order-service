
package com.nus.edu.se.payment.boundary;

import com.fasterxml.jackson.databind.JsonNode;
import com.nus.edu.se.exception.AuthenticationException;
import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.service.GroupOrdersService;
import com.nus.edu.se.groupfoodorder.service.JwtTokenInterface;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.payment.pricing.DecoratorGenerator;
import com.nus.edu.se.payment.pricing.FoodCart;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("UpdatePaymentStatusAPI")
public class PaymentResource {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GroupOrderRepository groupOrderRepository;

    @Autowired
    private GroupOrdersService groupOrdersService;

    @Autowired
    JwtTokenInterface jwtTokenInterface;

    @Transactional
    @PutMapping(value = "/updatePayment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePaymentStatus(@RequestBody JsonNode paymentDetailsN, HttpServletRequest request) throws  org.apache.tomcat.websocket.AuthenticationException {
        String token = groupOrdersService.resolveToken(request);
        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {

            System.out.println(paymentDetailsN);
            System.out.println("Inside the JsonNode function");
            JsonNode orderItemIdNode = paymentDetailsN.get("orderItemId");
            JsonNode paymentStatusNode = paymentDetailsN.get("paymentStatus");
            JsonNode isGroupFoodOrderNode = paymentDetailsN.get("isGroupFoodOrder");
            JsonNode isGetPromoNode = paymentDetailsN.get("isGetPromo");
            JsonNode totalPriceNode = paymentDetailsN.get("totalPrice");
            JsonNode isForShowNode = paymentDetailsN.get("forShow");
            JsonNode selectedPaymentMethod = paymentDetailsN.get("paymentType"); //need to comment


            System.out.println(isGroupFoodOrderNode);
            System.out.println(isGetPromoNode);
            System.out.println("selected payment method is :");
            System.out.println(selectedPaymentMethod);
            // type conversion to be used by foodCart
            boolean isGroupFoodOrderStrategy = isGroupFoodOrderNode != null && isGroupFoodOrderNode.asBoolean();
            // boolean isGroupFoodOrderStrategy = isGroupFoodOrderNode.get("_value").asBoolean();
            boolean isGetPromoDecorator = isGetPromoNode != null && isGetPromoNode.asBoolean();
            // boolean isGetPromoDecorator = isGetPromoNode.get("_value").asBoolean();
            // boolean isForShow = isForShowNode.get("_value").asBoolean();
            boolean isForShow = isForShowNode != null && isForShowNode.asBoolean();

            String totalPriceString = totalPriceNode != null ? totalPriceNode.asText() : "0.0";
            System.out.println("isForShow");
            System.out.println(isForShow);
            // call FoodCart if isForShow
            System.out.printf("Is for show?: %b\n",isForShow);
            float totalPrice = Float.parseFloat(totalPriceString);

            System.out.println(orderItemIdNode);
            System.out.println(totalPriceString);
            System.out.println(isGetPromoDecorator);
            System.out.println(isGroupFoodOrderStrategy);
            if (isForShow) {
                DecoratorGenerator gen = new DecoratorGenerator();
                DecoratorGenerator.PromoType selectedPromo = gen.generateRandomDecorator();
                System.out.printf("Generated Promotion:: %s\n",selectedPromo);
                FoodCart foodCart = new FoodCart(isGroupFoodOrderStrategy, isGetPromoDecorator, totalPrice, selectedPromo);
                float finalTotalPrice = foodCart.checkout();

                // System.out.printf("Final total Price: %.2f%n", finalTotalPrice);

                String formattedStringPrice = String.format("%.2f", finalTotalPrice);
                System.out.printf("Final total Price: %s\n",formattedStringPrice);
                formattedStringPrice = formattedStringPrice.concat(" ").concat(selectedPromo.name());
                System.out.printf("Final String: %s\n",formattedStringPrice);
                return new ResponseEntity<>(formattedStringPrice, HttpStatus.OK);
            }

            // String selectedPaymentOption = paymentDetailsN.get("selectedPaymentOption").asText(); //need to uncomment
            String selectedPaymentOption = selectedPaymentMethod.asText(); //need to comment
            System.out.println("Selected Payment Option is :");
            System.out.println(selectedPaymentOption);

            UUID orderItemId;
            try {
                orderItemId = UUID.fromString(orderItemIdNode.asText());
                System.out.println(orderItemId);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Invalid orderItemId format", HttpStatus.BAD_REQUEST);
            }

           // Order order = orderRepository.findByOrderItemId(orderItemId);
            Order order = orderRepository.findById(orderItemId).orElse(null);

            if (order == null) {
                return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
            }

            PaymentGatewayInterface paymentGateway;

            switch(selectedPaymentOption) {
                case "creditCard":
                    CCPaymentInterface creditCardPayment = new CreditCard();
                    paymentGateway = new CreditCardAdapter(creditCardPayment);
                    break;
                case "payNow":
                        PayNowPaymentInterface payNowPayment = new PayNow();
                        paymentGateway = new PayNowAdapter(payNowPayment);
                        break;
                case "payLah":
                    PayLahPaymentInterface payLahPayment = new PayLah();
                    paymentGateway = new PayLahAdapter(payLahPayment);
                    break;
                default:
                    return new ResponseEntity<>("Invalid payment status value", HttpStatus.BAD_REQUEST);
            }
            paymentGateway.processPayment();

            String paymentStatusValue = paymentStatusNode.asText();
            System.out.println(paymentStatusValue);
            switch (paymentStatusValue) {
                case "COMPLETED":
                    order.setPaymentStatus(Order.PaymentStatus.COMPLETED);
                    break;
                case "FAILED":
                    order.setPaymentStatus(Order.PaymentStatus.FAILED);
                    break;
                case "PENDING":
                    GroupFoodOrder groupFoodOrder = groupOrderRepository.findGroupFoodOrderById(order.getGroupFoodOrder().getId()).orElseThrow(() -> new DataNotFoundException("Group Order Not Found."));
                    if (groupOrdersService.groupFoodOrderNotValidToJoin(groupFoodOrder)) {
                        groupOrdersService.deleteOrder(order.getId().toString());
                        throw new AuthenticationException("Group Order already submitted to restaurant, please join other group orders!");
                    } else {
                        order.setPaymentStatus(Order.PaymentStatus.COMPLETED);
                        groupFoodOrder.setGroupOrderCreateTime(new Date());
                    }
                    break;
                case "PROCESSING":
                    order.setPaymentStatus(Order.PaymentStatus.PROCESSING);
                    break;
                default:
                    return new ResponseEntity<>("Invalid payment status value", HttpStatus.BAD_REQUEST);
            }

            orderRepository.save(order);

            return new ResponseEntity<>("Payment status updated successfully", HttpStatus.OK);
        } else {
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to updatePaymentStatus!");
        }
    }


    @Transactional
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> updatePaymentStatus(@PathVariable UUID id, @RequestBody String newPaymentStatus) {
        Order order = orderRepository.findById(id).orElse(null);

        System.out.println("First test");

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Order not found
        }


        System.out.println("Before switch");
        System.out.println(newPaymentStatus);

        //to uncomment following
        String jsonString = newPaymentStatus;

        // Find the index of the colon
        int colonIndex = jsonString.indexOf(":");

        // Extract the substring after the colon
        String value = jsonString.substring(colonIndex + 2, jsonString.length() - 2);



        System.out.println(value); //to delete
        //System.out.println(value); // Output: FAILED //to uncomment

        switch(value) { //change back to value
            case "COMPLETED":
                order.setPaymentStatus(Order.PaymentStatus.COMPLETED);
                break;
            case "FAILED":
                order.setPaymentStatus(Order.PaymentStatus.FAILED);
                //orderRepository.save(order);
                System.out.println("Inside failed");
                break;
            case "PENDING":
                // code block
                order.setPaymentStatus(Order.PaymentStatus.PENDING);
                //orderRepository.save(order);
                break;
            case "PROCESSING":
                // code block
                order.setPaymentStatus(Order.PaymentStatus.PROCESSING);
                //orderRepository.save(order);
                break;
            default:
                System.out.println("Default Block");
                // code block
        }
        orderRepository.save(order);


        // // Update the payment status
        // System.out.println("second test");
        // order.setPaymentStatus(Order.PaymentStatus.COMPLETED); // Assuming newPaymentStatus is a String

        // // Save the updated order
        // orderRepository.save(order);
        // System.out.println("third test");

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // for quick testing
   @Transactional
   @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   //RequestBody
    public Order updatePaymentStatus(@PathVariable UUID id) {
       // boolean areUpdated = true;
        // UUID orderitemUuid = UUID.fromString(orderItemId);
        System.out.println("UpdatePaymentStatus");
        return orderRepository.findById(id).orElse(null);
    }

}



