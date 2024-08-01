package com.example.testdebeziumpr.service;

import com.example.testdebeziumpr.model.Order;
import com.example.testdebeziumpr.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void replicateData(Map<String, Object> customerData, Envelope.Operation operation) {
        final ObjectMapper mapper = new ObjectMapper();
        final Order customer = mapper.convertValue(customerData, Order.class);

        if (Envelope.Operation.DELETE == operation) {
//            orderRepository.deleteById(customer.getId());
            System.out.println("It was delete");
        } else {
            System.out.println("It was " + operation.name());

//            orderRepository.save(customer);
        }

    }
}
