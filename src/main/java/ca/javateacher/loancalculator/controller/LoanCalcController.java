package ca.javateacher.loancalculator.controller;

import ca.javateacher.loancalculator.model.Loan;
import ca.javateacher.loancalculator.model.LoanForm;
import ca.javateacher.loancalculator.model.LoanFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class LoanCalcController {

    @RequestMapping(value={"/","/Input.do"})
    public static String input(){
        return "Input";
    }

    @RequestMapping("/Calculate.do")
    public static String calculate(HttpServletRequest request){

        // get the input values
        String strAmount = request.getParameter("loanAmount").trim();
        String strRate = request.getParameter("annualInterestRate").trim();
        String strYears = request.getParameter("numberOfYears").trim();

        // put the input values into the object
        LoanForm form = new LoanForm();
        form.setLoanAmount(strAmount);
        form.setAnnualInterestRate(strRate);
        form.setNumberOfYears(strYears);

        // validate the data inside of the object
        ArrayList<String> errors = LoanFormValidator.validate(form);

        // check the validation errors
        if (errors.isEmpty()) {
            // if no errors, the input data is valid
            // convert the data into numbers for the calculation
            double amount = Double.parseDouble(strAmount);
            double rate = Double.parseDouble(strRate);
            int years = Integer.parseInt(strYears);

            // put the numbers in the object for the calculation
            Loan loan = new Loan();
            loan.setLoanAmount(amount);
            loan.setAnnualInterestRate(rate);
            loan.setNumberOfYears(years);

            // make the calculating object available to the output page
            request.setAttribute("loan", loan);

            // show the calculation output page
            return "Output";
        } else {
            // if we got input errors
            // make the error list available to the error output page
            request.setAttribute("errors", errors);
            return "Errors";
        }
    }

}
