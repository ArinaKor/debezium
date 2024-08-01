package com.example.testdebeziumpr.repository;

import com.example.testdebeziumpr.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Modifying
    @Query(name="sql.Customer.UpdateColumn", nativeQuery = true)
    void updateTable(@Param("param") String param);
}
