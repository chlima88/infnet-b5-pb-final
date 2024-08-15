package com.chlima.order.service;

import com.chlima.order.client.DeliveryClient;
import com.chlima.order.dto.CreateOrderDto;
import com.chlima.order.dto.OrderDto;
import com.chlima.order.dto.RequestDeliveryDto;
import com.chlima.order.model.Order;
import com.chlima.order.model.OrderItem;
import com.chlima.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class OrderCatalog {
    private DeliveryClient deliveryClient;
    private final OrderRepository orderRepository;
    public OrderCatalog(OrderRepository orderRepository, DeliveryClient deliveryClient) {
        this.deliveryClient = deliveryClient;
        this.orderRepository = orderRepository;
    }
    public OrderDto createOrder(CreateOrderDto orderDto) {
        Order order = Order.create(
                orderDto.customerId(),
                orderDto.orderItems().stream().map(
                        orderItemDto -> (OrderItem)toggleDtoModel(orderItemDto, OrderItem.class)
                ).toList()
        );
        orderRepository.save(order);
        log.info("Order placed: " + order.getOrderId());
        return (OrderDto) toggleDtoModel(order, OrderDto.class);
    }

    public OrderDto payOrder(String orderId) {
        //OrderDto orderDto = updateOrder(orderId, Order::pay);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("OrderId " + orderId + " not found"));
        order.pay();
        orderRepository.save(order);
        RequestDeliveryDto requestDeliveryDto = new RequestDeliveryDto(order.getOrderId(),order.getCustomerId(), order.getOrderItems().size());
        deliveryClient.requestDelivery(requestDeliveryDto);
        log.info("Order Status -> "+ order.getStatus());

        return findOrderById(orderId);
    }

    public OrderDto cancelOrder(String orderId) {
        return updateOrder(orderId, Order::cancel);
    }

    public OrderDto shipOrder(String orderId, String deliveryId) {
        return updateOrder(orderId, (Order order) -> {
            order.ship();
            order.setDeliveryId(deliveryId);
        });
    }

    public OrderDto completeOrder(String orderId) {
        return updateOrder(orderId, Order::complete);
    }

    private OrderDto updateOrder(String orderId, Consumer<Order> cb) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("OrderId " + orderId + " not found"));
        cb.accept(order);
        orderRepository.save(order);
        return (OrderDto) toggleDtoModel(order, OrderDto.class);
    }

    public OrderDto findOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("OrderId " + orderId + " not found"));
        return (OrderDto) toggleDtoModel(order, OrderDto.class);
    }

    private Object toggleDtoModel(Object ojb, Class<?> className){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(ojb, className);
    }
}
