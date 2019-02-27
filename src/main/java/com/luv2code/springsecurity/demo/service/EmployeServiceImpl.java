package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.dao.EmployeeDAO;
import com.luv2code.springsecurity.demo.dao.RoleDao;
import com.luv2code.springsecurity.demo.entity.Employee;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeServiceImpl implements EmployeeService {

    // inject EmployeeDao
    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // need more clarity on how this works
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    // delegate methods to the employeeDao
    @Override
    @Transactional
    public Employee getEmployee(Long theId) {
        return employeeDAO.getEmployee(theId);
    }

    @Override
    @Transactional
    public void saveEmployee(Employee theEmployee) {
        employeeDAO.saveEmployee(theEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long theId) {
        employeeDAO.deleteEmployee(theId);
    }

    @Override
    @Transactional
    public List<Employee> getEmployees() {
        return employeeDAO.getEmployees();
    }

    @Override
    @Transactional
    public Employee findByUserName(String userName) {
        return employeeDAO.findByUserName(userName);
    }

    @Override
    @Transactional
    public void save(CrmUser theCrmUser) {
        Employee employee = new Employee();

        employee.setUserName(theCrmUser.getUserName());
        employee.setPassword(passwordEncoder.encode(theCrmUser.getPassword()));
        employee.setFirstName(theCrmUser.getFirstName());
        employee.setLastName(theCrmUser.getLastName());
        employee.setEmail(theCrmUser.getEmail());
        employee.setAddress(theCrmUser.getAddress());
        employee.setPhoneNumber(theCrmUser.getPhoneNumber());
        employee.setZipcode(theCrmUser.getZipcode());

        employee.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

        employeeDAO.save(employee);

    }
}
