package com.example.testdebeziumpr.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Order {
    @Id
    private Long id;
    private Long count;
    private String address;
}
