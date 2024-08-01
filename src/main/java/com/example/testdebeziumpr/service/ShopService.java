package com.example.testdebeziumpr.service;

import com.example.testdebeziumpr.model.Shop;
import com.example.testdebeziumpr.repository.ShopRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository orderRepository;

    public void replicateData(Map<String, Object> customerData, Envelope.Operation operation) {
        final ObjectMapper mapper = new ObjectMapper();
        final Shop customer = mapper.convertValue(customerData, Shop.class);

        if (Envelope.Operation.DELETE == operation) {
//            orderRepository.deleteById(customer.getId());
            System.out.println("It was delete");
        } else {
            System.out.println("It was " + operation.name());

//            orderRepository.save(customer);
        }

    }
}

