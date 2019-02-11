package ca.javateacher.loancalculator.validator;

import ca.javateacher.loancalculator.model.LoanForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LoanFormValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(LoanFormValidator.class);

    @Override
    public boolean supports(Class<?> type) {
        return LoanForm.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        logger.trace("The validator is called to validate.");
        LoanForm form = (LoanForm) obj;
        if (form.getLoanAmount().trim().isEmpty()) {
            errors.rejectValue("loanAmount", "NotBlank.form.loanAmount");
        } else {
            try {
                double amount = Double.parseDouble(form.getLoanAmount());
                if(amount <= 0){
                    errors.rejectValue("loanAmount", "Positive.form.loanAmount");
                }
                double cents = amount*100;
                if(cents - Math.floor(cents) != 0.0){
                    errors.rejectValue("loanAmount", "IntegerCents.form.loanAmount");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("loanAmount", "Number.form.loanAmount");
            }
        }

        if (form.getAnnualInterestRate().trim().isEmpty()) {
            errors.rejectValue("annualInterestRate", "NotBlank.form.annualInterestRate");
        } else {
            try {
                double rate = Double.parseDouble(form.getAnnualInterestRate());
                if(rate <= 0){
                    errors.rejectValue("annualInterestRate", "Positive.form.annualInterestRate");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("annualInterestRate", "Number.form.annualInterestRate");
            }
        }

        if (form.getNumberOfYears().trim().isEmpty()) {
            errors.rejectValue("numberOfYears", "NotBlank.form.numberOfYears");
        } else {
            try {
                int years = Integer.parseInt(form.getNumberOfYears().trim());
                if(years <= 0 ){
                    errors.rejectValue("numberOfYears", "Positive.form.numberOfYears");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("numberOfYears", "Integer.form.numberOfYears");
            }
        }
    }

}
