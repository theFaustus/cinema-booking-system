package com.evil.cbs.service;

import com.evil.cbs.domain.Customer;
import com.evil.cbs.web.form.RegisterCustomerFormBean;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(RegisterCustomerFormBean registerCustomerFormBean);

    Customer findCustomerByEmail(String email);

    List<Customer> findAll();

    void enableCustomer(String email);

    void disableCustomer(String email);


}
