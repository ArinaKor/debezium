package com.example.testdebeziumpr.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Customer {

    @Id
    private Long id;
    private String fullname;
    private String email;
}