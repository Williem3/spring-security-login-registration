package com.luv2code.springsecurity.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.luv2code.springsecurity.demo.entity.Customer;
import com.luv2code.springsecurity.demo.entity.Employee;
import com.luv2code.springsecurity.demo.service.CustomerService;
import com.luv2code.springsecurity.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    private CustomerService customerService;


    @Autowired
	private EmployeeService employeeService;

    // create custom Authenticate method
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");

		// declare username variable
		String userName = authentication.getName();
		
		System.out.println("userName=" + userName);

		// declare theUser as findByUserName through employeeService and customerService.
		Customer theCustomer = customerService.findByUserName(userName);

		
		// now place in the session
		HttpSession session = request.getSession();
		session.setAttribute("customer", theCustomer);
		
		// forward to home page
		response.sendRedirect(request.getContextPath() + "/");
	}

}
