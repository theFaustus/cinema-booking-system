package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Customer;
import com.evil.cbs.domain.User;
import com.evil.cbs.repository.CustomerRepository;
import com.evil.cbs.service.CustomerService;
import com.evil.cbs.service.util.UserRole;
import com.evil.cbs.service.util.UserState;
import com.evil.cbs.web.form.RegisterCustomerFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer saveCustomer(RegisterCustomerFormBean registerCustomerFormBean) {
        Customer c = Customer.CustomerBuilder.aCustomer()
                .firstName(registerCustomerFormBean.getFirstName())
                .lastName(registerCustomerFormBean.getLastName())
                .email(registerCustomerFormBean.getEmail())
                .password(passwordEncoder.encode(registerCustomerFormBean.getCustomerPassword()))
                .telephoneNumber(registerCustomerFormBean.getTelephoneNumber())
                .role(UserRole.USER_ROLE.getValue())
                .build();

        customerRepository.save(c);
        return c;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerBy(email);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    @Override
    public void enableCustomer(String email) {
        customerRepository.updateEnabledValue(email, UserState.ENABLED.getValue());
    }

    @Transactional
    @Override
    public void disableCustomer(String email) {
        customerRepository.updateEnabledValue(email, UserState.DISABLED.getValue());
    }
}
