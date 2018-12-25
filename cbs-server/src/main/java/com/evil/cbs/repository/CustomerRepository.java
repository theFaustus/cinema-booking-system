package com.evil.cbs.repository;

import com.evil.cbs.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.email = :email")
    Customer findCustomerBy(@Param("email") String email);

    @Modifying
    @Query("update Customer c set c.enabled = :enabledValue where c.email = :email")
    void updateEnabledValue(@Param("email") String email, @Param("enabledValue") int value);

}
