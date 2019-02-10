package ca.javateacher.loancalculator.controller;

import ca.javateacher.loancalculator.model.Loan;
import ca.javateacher.loancalculator.model.LoanForm;
import ca.javateacher.loancalculator.validator.LoanFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoanCalcController {

    private Logger logger = LoggerFactory.getLogger(LoanCalcController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if(binder.getObjectName().equals("form"))
            binder.setValidator(new LoanFormValidator());
    }

    @RequestMapping(value={"/","/Input.do"})
    public ModelAndView input(){
        // make the object available to the Input page and show the page
        logger.trace("Showing the first input page.");
        return new ModelAndView("Input","form", new LoanForm());
    }

    @RequestMapping("/Calculate.do")
    public ModelAndView calculate(
            @Validated @ModelAttribute(name="form") LoanForm form,
            BindingResult bindingResult){

        logger.trace("Received a user input.");
        // check the validation errors
        if (!bindingResult.hasErrors()) {
            logger.trace("The input data is valid.");
            // if no errors, the input data is valid
            // convert the data into numbers for the calculation
            // put the numbers in the object for the calculation
            Loan loan = new Loan();
            loan.setLoanAmount(Double.parseDouble(form.getLoanAmount()));
            loan.setAnnualInterestRate(Double.parseDouble(form.getAnnualInterestRate()));
            loan.setNumberOfYears(Integer.parseInt(form.getNumberOfYears()));
            logger.trace("Showing the output page.");
            // make the object available to the Output page and show the page
            return new ModelAndView("Output", "loan", loan);
        } else {
            logger.trace("The received data is invalid, going back to the inputs.");
            // if we got input errors, we are going back to the Input page
            // insert the previous user inputs into the Input page
            // the errors are already included
            return new ModelAndView("Input", "form" , form);
        }
    }

}
