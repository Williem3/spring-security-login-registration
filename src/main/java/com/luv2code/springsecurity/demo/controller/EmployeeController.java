package com.luv2code.springsecurity.demo.controller;

import com.luv2code.springsecurity.demo.entity.Employee;
import com.luv2code.springsecurity.demo.service.EmployeeService;
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
@RequestMapping("/employee")
public class EmployeeController {

    // inject the employeeService into the controller
    @Autowired
    private EmployeeService employeeService;

    // create logger for logging information
    private Logger logger = Logger.getLogger(getClass().getName());

    // method to trim white space from user input
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @RequestMapping("/list")
    public String listEmployees(Model theModel) {

        // get employees form the dao
        List<Employee> theEmployees = employeeService.getEmployees();

        // add the employees to the model
        // name     // variable from above
        theModel.addAttribute("employees", theEmployees);
        return "list-employees";
    }

    @GetMapping("/showFormForUpdate")
    // Request employeeId from database
    public String showFormForUpdate(@RequestParam("employeeId") Long theId, Model theModel) {

        // get the employee from the database
        Employee theEmployee = employeeService.getEmployee(theId);

        // get employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employee-form";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
        employeeService.saveEmployee(theEmployee);

        return "redirect:/employee/list";
    }
    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") Long theId) {
        employeeService.deleteEmployee(theId);

        return"redirect:/employee/list";
    }

    // Create GetMapping for registration form
    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {

        // pass attributes entered in the registration form
        // from crmUser model to the new CrmUser object
        theModel.addAttribute("crmUser", new CrmUser());

        return "employee-registration-form";
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
            return "employee-registration-form";
        }

        // check the database if user already exists via username
        Employee existing = employeeService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("crmUser", new CrmUser());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "registration-form";
        }
        // create user account, this does not execute until the above if statement has result of existing = null
        employeeService.save(theCrmUser);

        logger.info("Successfully created user: " + userName);

        return "employee-registration-confirmation";
    }
}
