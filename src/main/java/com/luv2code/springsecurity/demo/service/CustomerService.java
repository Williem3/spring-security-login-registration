package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.entity.Customer;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.List;


public interface CustomerService extends UserDetailsService {
    public List<Customer> getCustomers();

    void saveCustomer(Customer theCustomer);

    Customer getCustomer(Long theId);

    void deleteCustomer(Long theId);

    Customer findByUserName(String userName);

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    void save(CrmUser theCrmUser);
}
