package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.dao.CustomerDAO;
import com.luv2code.springsecurity.demo.dao.EmployeeDAO;
import com.luv2code.springsecurity.demo.dao.RoleDao;
import com.luv2code.springsecurity.demo.entity.Customer;
import com.luv2code.springsecurity.demo.entity.Employee;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    // inject CustomerDAO
    @Autowired
    private CustomerDAO customerDAO;

    // inject RoleDao
    @Autowired
    private RoleDao roleDao;

    // inject Employee DAO
    @Autowired
    private EmployeeDAO employeeDAO;

    // inject BCrypt
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // need more clarity on how this works
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    // State that the process will be a transaction
    @Override
    @Transactional
    public List<Customer> getCustomers() {

        return customerDAO.getCustomers();
    }

    @Override
    @Transactional
    public void saveCustomer(Customer theCustomer) {
        customerDAO.saveCustomer(theCustomer);
    }

    @Override
    @Transactional
    public Customer getCustomer(Long theId) {
        return customerDAO.getCustomer(theId);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long theId) {
        customerDAO.deleteCustomer(theId);
    }

    @Override
    @Transactional
    public Customer findByUserName(String userName) {
        return customerDAO.findByUserName(userName);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Customer customer = findByUserName(userName);
        if (customer != null) {
            return new User(customer.getUserName(), customer.getPassword(), mapRolesToAuthorities(customer.getRoles()));
        }
        else {
            // Not found in user table, check employee table
            Employee employee = employeeDAO.findByUserName(userName);
            if (employee != null) {
                return new User(employee.getUserName(), employee.getPassword(), mapRolesToAuthorities(employee.getRoles()));
            }
        }
        throw new UsernameNotFoundException("Invalid username or password.");
    }

    @Override
    @Transactional
    public void save(CrmUser theCrmUser) {
        Customer customer = new Customer();

        customer.setUserName(theCrmUser.getUserName());
        customer.setPassword(passwordEncoder.encode(theCrmUser.getPassword()));
        customer.setFirstName(theCrmUser.getFirstName());
        customer.setLastName(theCrmUser.getLastName());
        customer.setEmail(theCrmUser.getEmail());
        customer.setAddress(theCrmUser.getAddress());
        customer.setPhoneNumber(theCrmUser.getPhoneNumber());
        customer.setZipcode(theCrmUser.getZipcode());

        customer.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_CUSTOMER")));

        customerDAO.save(customer);

    }
}
