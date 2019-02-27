package com.luv2code.springsecurity.demo.dao;


import com.luv2code.springsecurity.demo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    // need to inject session factory
    @Autowired
    private SessionFactory sessionFactory;


    // Implement the method for getting the customers for the list
    @Override
    public List<Customer> getCustomers() {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create a query ... sort by last name
        Query<Customer> theQuery =
                currentSession.createQuery("from Customer order by lastName", Customer.class);
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();

        // return result list
        return customers;
    }

    @Override
    public void saveCustomer(Customer theCustomer) {

        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // save customer to DB
        currentSession.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getCustomer(Long theId) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // assign a variable (theCustomer) to represent the theId Customer Info
        Customer theCustomer = currentSession.get(Customer.class, theId);

        // return theCustomer
        return theCustomer;
    }

    @Override
    public void deleteCustomer(Long theId) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete theId from database using a query
        Query theQuery =
                currentSession.createQuery("delete from Customer where id=:customerId");
        theQuery.setParameter("customerId", theId);
        theQuery.executeUpdate();
    }

    @Override
    public Customer findByUserName(String userName) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create query
        Query<Customer> theQuery =
                currentSession.createQuery("from Customer where username=:uName", Customer.class);
        theQuery.setParameter("uName", userName);

        Customer theCustomer = null;
        try {
            theCustomer = theQuery.getSingleResult();
        }
        catch (Exception e) {
            theCustomer = null;
        }
        return theCustomer;
    }

    @Override
    public void save(Customer customer) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // save the customer
        currentSession.saveOrUpdate(customer);

    }
}
