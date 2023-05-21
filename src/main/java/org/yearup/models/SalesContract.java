package org.yearup.models;

import java.util.ArrayList;

public class SalesContract extends Contract
{

    private static final double SALE_TAX_AMOUNT = 0.05;
    private static final double RECORDING_FEE = 100;
    private static final double PROCESSING_FEE_UNDER_1000 = 295;
    private static final double PROCESSING_FEE_ABOVE_1000 = 495;
    private boolean financeOption; //Whether they want to finance (yes or no)

    public SalesContract(String date, String name, String email, ArrayList<Vehicle> vehicleSold, boolean financeOption)
    {
        super(date,name,email,vehicleSold);
        this.financeOption = financeOption;
    }

    public static double getSaleTaxAmount()
    {
        return SALE_TAX_AMOUNT;
    }
    public static double getRecordingFee()
    {
        return RECORDING_FEE;
    }
    public static double getProcessingFeeUnder1000()
    {
        return PROCESSING_FEE_UNDER_1000;
    }
    public static double getProcessingFeeAbove1000()
    {
        return PROCESSING_FEE_ABOVE_1000;
    }
    public boolean isFinanceOption()
    {
        return financeOption;
    }

    public void setFinanceOption(boolean financeOption)
    {
        this.financeOption = financeOption;
    }

    @Override
    public double getTotalPrice()
    {
        double totalPrice = 0;

        for(Vehicle vehicle : getVehicleSold())
        {
            totalPrice += vehicle.getPrice();
        }

        totalPrice += SALE_TAX_AMOUNT + RECORDING_FEE;

        if (totalPrice < 1_000)
        {
            totalPrice += PROCESSING_FEE_UNDER_1000;
        }
        else
        {
            totalPrice += PROCESSING_FEE_ABOVE_1000;
        }

        return totalPrice;
    }

    @Override
    public double getMonthlyPayment()
    {
        //if no financing option
        if (!financeOption)
        {
            return 0;
        }
        double loanAmount = getTotalPrice();
        double interestRate;
        int loanTerm;

        if (loanAmount >= 10_000)
        {
            //4.25% loan for 10,000 or more
            interestRate = 0.0425;
            loanTerm = 48;
        }
        else
        {
            //5.25% for loans under 10K
            interestRate = 0.0525;
            loanTerm = 24;
        }

        double monthlyInterestRate = interestRate / 12;
        //M = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        double monthlyPayment = loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate,loanTerm))
                / (Math.pow(1 + monthlyInterestRate, loanTerm) -1);

        return monthlyPayment;

    }
}
