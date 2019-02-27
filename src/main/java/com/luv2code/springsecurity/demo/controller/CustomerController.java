package com.luv2code.springsecurity.demo.controller;

import com.luv2code.springsecurity.demo.entity.Customer;
import com.luv2code.springsecurity.demo.service.CustomerService;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // inject the customerService into the controller
    @Autowired
    private CustomerService customerService;

    private Logger logger = Logger.getLogger(getClass().getName());

    @RequestMapping("/list")
    public String listCustomers(Model theModel) {

        // get customers form the dao
        List<Customer> theCustomers = customerService.getCustomers();

        // add the customers to the model
                                            // name     // variable from above
        theModel.addAttribute("customers", theCustomers);
        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        Customer theCustomer = new Customer();

        theModel.addAttribute("customer", theCustomer);

        return "customer-form";
    }

    @GetMapping("/showFormForUpdate")
                                    // Request customerId from database
    public String showFormForUpdate(@RequestParam("customerId") Long theId, Model theModel) {

        // get the customer from the database
        Customer theCustomer = customerService.getCustomer(theId);

        // get customer as a model attribute to pre-populate the form
        theModel.addAttribute("customer", theCustomer);

        // send over to our form
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){
        customerService.saveCustomer(theCustomer);

        return "redirect:/customer/list";
    }
    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") Long theId) {
        customerService.deleteCustomer(theId);

        return"redirect:/customer/list";
    }

    // method to trim white space from user input
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // Create GetMapping for registration form
    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {

        // pass attributes entered in the registration form
        // from crmUser model to the new CrmUser object
        theModel.addAttribute("crmUser", new CrmUser());

        return "registration-form";
    }

    // create PostMapping for processing the registration form into the DB
    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("crmUser") CrmUser theCrmUser,
            BindingResult theBindingResult,
            Model theModel) {

        String userName = theCrmUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()){
            return "registration-form";
        }

        // check the database if user already exists via username
        Customer existing = customerService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("crmUser", new CrmUser());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "registration-form";
        }
        // create user account, this does not execute until the above if statement has result of existing = null
        customerService.save(theCrmUser);

        logger.info("Successfully created user: " + userName);

        return "registration-confirmation";
    }
}
