package ca.javateacher.loancalculator.model;

public class Loan {

    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;

    public Loan() {
        this(2.5, 1, 1000);
    }

    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
        setAnnualInterestRate(annualInterestRate);
        setNumberOfYears(numberOfYears);
        setLoanAmount(loanAmount);
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public final void setAnnualInterestRate(double annualInterestRate) {
        if(annualInterestRate > 0){
            this.annualInterestRate = annualInterestRate;
        }else{
            throw new IllegalArgumentException("The annual interest rate must be more than zero.");
        }
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public final void setNumberOfYears(int numberOfYears) {
        if(numberOfYears >= 1){
            this.numberOfYears = numberOfYears;
        }else{
            throw new IllegalArgumentException("The number of years should be at least one.");
        }
    }

    public double getLoanAmount() {
        return loanAmount;
    }


    public final void setLoanAmount(double loanAmount) {
        if(loanAmount > 0){
            this.loanAmount = loanAmount;
        }else{
            throw new IllegalArgumentException("The loan amount must be positive.");
        }
    }

    public double getMonthlyPayment() {
        double monthlyInterestRate = annualInterestRate / 1200;
        double monthlyPayment =
                loanAmount * monthlyInterestRate / (1 -
                        (Math.pow(1 / (1 + monthlyInterestRate), numberOfYears * 12)));
        return monthlyPayment;
    }

    public double getTotalPayment() {
        double totalPayment = getMonthlyPayment() * numberOfYears * 12;
        return totalPayment;
    }
}
