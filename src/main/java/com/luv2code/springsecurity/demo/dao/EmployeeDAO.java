package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee getEmployee(Long theId);

    void saveEmployee(Employee theEmployee);

    void deleteEmployee(Long theId);

    List<Employee> getEmployees();

    void save(Employee employee);

    Employee findByUserName(String userName);
}
