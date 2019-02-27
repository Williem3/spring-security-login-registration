package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDAO{

    @Autowired
    private SessionFactory sessionFactory;

    // define getEmployee method
    @Override
    public Employee getEmployee(Long theId) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // get Employee by the id
        Employee theEmployee = currentSession.get(Employee.class, theId);

        // return the employee
        return theEmployee;
    }

    // define saveEmployee method
    @Override
    public void saveEmployee(Employee theEmployee) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // save Employee by the id
        currentSession.saveOrUpdate(theEmployee);
    }

    // define deleteEmployee method
    @Override
    public void deleteEmployee(Long theId) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete Employee by id
        currentSession.delete(theId);
    }

    @Override
    public List<Employee> getEmployees() {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create query
        Query<Employee> theQuery =
                currentSession.createQuery("from Employee order by lastName", Employee.class);

        // apply result list to variable list
        List<Employee> theEmployees = theQuery.getResultList();

        // return the result list variable
        return theEmployees;
    }

    @Override
    public Employee findByUserName(String userName) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now create query... where username(from database) matches uName(direct relationship with userName1)
        Query<Employee> theQuery = currentSession.createQuery("from Employee where username=:uName", Employee.class);
        theQuery.setParameter("uName", userName);

        Employee theEmployee = null;
        try {
            theEmployee = theQuery.getSingleResult();
        }
        catch (Exception e) {
            theEmployee =null;
        }
        return theEmployee;
    }

    @Override
    public void save(Employee employee) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create or save user
        currentSession.saveOrUpdate(employee);
    }


}
