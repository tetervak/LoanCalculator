package ca.javateacher.loancalculator.model;

import ca.javateacher.loancalculator.model.LoanForm;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class LoanFormValidator {

    @NonNull
    public static ArrayList<String> validate(@NonNull LoanForm form) {

        ArrayList<String> errors = new ArrayList<>();

        if (form.getLoanAmount().trim().isEmpty()) {
            errors.add("Loan amount input is left empty");
        } else {
            try {
                double amount = Double.parseDouble(form.getLoanAmount());
                if(amount <= 0){
                    errors.add("Loan must be more than zero");
                }
                double cents = amount*100;
                if(cents - Math.floor(cents) != 0.0){
                    errors.add("Loan amount cannot contain fractional number of cents");
                }
            } catch (NumberFormatException e) {
                errors.add("Loan amount is not a number");
            }
        }

        if (form.getAnnualInterestRate().trim().isEmpty()) {
            errors.add("Annual interest rate input is left empty");
        } else {
            try {
                double rate = Double.parseDouble(form.getAnnualInterestRate());
                if(rate <= 0){
                    errors.add("Annual interest rate must be more than zero");
                }
            } catch (NumberFormatException e) {
                errors.add("Annual interest rate is not a number");
            }
        }

        if (form.getNumberOfYears().trim().isEmpty()) {
            errors.add("Number of years input is left empty");
        } else {
            try {
                int years = Integer.parseInt(form.getNumberOfYears());
                if(years <= 0 ){
                    errors.add("Number of years must be more than zero");
                }
            } catch (NumberFormatException e) {
                errors.add("Number of years is not an integer number");
            }
        }

        return errors;
    }

}
