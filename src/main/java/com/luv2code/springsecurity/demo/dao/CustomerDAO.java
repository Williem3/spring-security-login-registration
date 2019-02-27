package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    public List<Customer> getCustomers();

    void saveCustomer(Customer theCustomer);

    Customer getCustomer(Long theId);

    void deleteCustomer(Long theId);

    Customer findByUserName(String userName);

    void save(Customer customer);
}
