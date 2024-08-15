package com.chlima.delivery.service;

import com.chlima.delivery.client.OrderClient;
import com.chlima.delivery.dto.*;
import com.chlima.delivery.model.Deliverer;
import com.chlima.delivery.model.Delivery;
import com.chlima.delivery.repository.DelivererRepository;
import com.chlima.delivery.repository.DeliveryRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DelivererRepository delivererRepository;
    private final OrderClient orderClient;


    public DeliveryService(
            DeliveryRepository deliveryRepository,
            DelivererRepository delivererRepository,
            OrderClient orderClient){
        this.deliveryRepository = deliveryRepository;
        this.delivererRepository = delivererRepository;
        this.orderClient = orderClient;
    }

    public DeliveryDto requestDelivery(RequestDeliveryDto deliveryDto){
        Optional<Deliverer> deliverer = delivererRepository.findFirstAvailable();
                //.orElseThrow(() -> new UnsupportedOperationException("No Deliverers currently available."));
        Delivery delivery = Delivery.create(deliveryDto.customerId(), deliveryDto.orderId(), deliveryDto.itemsQuantity());
        if (deliverer.isPresent()) {
            delivery.assignDeliverer(deliverer.get());
            delivery.startDelivery();
            delivererRepository.save(deliverer.get());
        }
        deliveryRepository.save(delivery);
        orderClient.shipOrder(delivery.getOrderId(), new ShipOrderDto(delivery.getDeliveryId()));
        return new DeliveryDto(
                delivery.getDeliveryId(),
                deliverer.map(Deliverer::getDelivererId).orElse(""),
                String.valueOf(delivery.getStatus())
        );
    }

    public DelivererDto delivererRegister(DelivererRegisterDto delivererDto){
        Deliverer deliverer = Deliverer.create(delivererDto.name(), delivererDto.phone());
        delivererRepository.save(deliverer);
        return (DelivererDto)toDto(deliverer, DelivererDto.class);
    }

    public void delivererDefineAvailable(String delivererId) {
        Deliverer deliverer = delivererRepository.findById(delivererId)
                .orElseThrow(() -> new NotFoundException("DelivererId Not Found"));
        deliverer.makeAvailable();
        delivererRepository.save(deliverer);
    }

    public void delivererDefineUnavailable(String delivererId) {
        Deliverer deliverer = delivererRepository.findById(delivererId)
                .orElseThrow(() -> new NotFoundException("DelivererId Not Found"));
        deliverer.makeUnavailable();
        delivererRepository.save(deliverer);
    }

    public DeliveryDto completeDelivery(String deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(()-> new NotFoundException("DeliveryId not found"));
        delivery.completeDelivery();
        delivererRepository.save(delivery.getDeliverer());
        deliveryRepository.save(delivery);
        System.out.println(delivery);
        orderClient.completeOrder(delivery.getOrderId());
        return new DeliveryDto(
                delivery.getDeliveryId(),
                delivery.getDeliverer().getDelivererId(),
                String.valueOf(delivery.getStatus())
        );
    }

    private Object toDto(Object ojb, Class<?> className){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(ojb, className);
    }
}
