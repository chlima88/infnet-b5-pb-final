package com.chlima.delivery.config;

import com.chlima.delivery.model.Deliverer;
import com.chlima.delivery.model.Delivery;
import com.chlima.delivery.repository.DelivererRepository;
import com.chlima.delivery.repository.DeliveryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;
import java.util.Optional;

@Configuration
public class MongoDataLoader {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DelivererRepository delivererRepository;

    @Bean
    public ApplicationRunner dataInitializer() {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            Optional<ClassPathResource> delivererFile = Optional.of(new ClassPathResource("data-deliverer.json"));
            if (delivererFile.get().exists()) {
                List<Deliverer> deliverers = objectMapper.readValue(
                        new ClassPathResource("data-deliverer.json").getInputStream(),
                        new TypeReference<List<Deliverer>>() {
                        }
                );
                delivererRepository.deleteAll();
                delivererRepository.insert(deliverers);
            }

            Optional<ClassPathResource> deliveryFile = Optional.of(new ClassPathResource("data-delivery.json"));
            if (deliveryFile.get().exists()) {
                List<Delivery> deliveries = objectMapper.readValue(
                        deliveryFile.get().getInputStream(),
                        new TypeReference<List<Delivery>>() {}
                );
                deliveryRepository.deleteAll();
                deliveryRepository.insert(deliveries);
            }
        };
    }
}