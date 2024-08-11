package com.chlima.order.service;

import com.chlima.order.dto.CreateOrderDto;
import com.chlima.order.dto.OrderDto;
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
    private final OrderRepository orderRepository;
    public OrderCatalog(OrderRepository orderRepository) {
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
        // TODO Acionar serviço de delivery;
        return updateOrder(orderId, Order::pay);
    }

    public OrderDto cancelOrder(String orderId) {
        return updateOrder(orderId, Order::cancel);
    }

    public OrderDto shipOrder(String orderId) {
        return updateOrder(orderId, Order::ship);
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

    private Object toggleDtoModel(Object ojb, Class<?> className){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(ojb, className);
    }
}
