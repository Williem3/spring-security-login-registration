package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.entity.Employee;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface EmployeeService {
    Employee getEmployee(Long theId);

    void saveEmployee(Employee theEmployee);

    void deleteEmployee(Long theId);

    List<Employee> getEmployees();

    void save(CrmUser theCrmUser);

    Employee findByUserName(String userName);
}
