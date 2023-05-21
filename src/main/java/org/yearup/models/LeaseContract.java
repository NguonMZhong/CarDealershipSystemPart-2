package org.yearup.models;

import java.util.ArrayList;

public class LeaseContract extends Contract
{
    private final static double EXPECTED_END_VALUE = 0.5; //50% of the original price
    private final static double LEASE_FEE = 0.07; //7% of the original price

    public LeaseContract(String date, String customerName, String customerEmail, ArrayList<Vehicle> vehicleSold)
    {
        super(date, customerName, customerEmail, vehicleSold);

    }
    public double getExpectedEndValue()
    {
        return EXPECTED_END_VALUE;
    }
    public double getLeaseFee()
    {
        return LEASE_FEE;
    }
    @Override
    public double getTotalPrice()
    {
        double totalPrice = 0;

        for (Vehicle vehicle : getVehicleSold())
        {
            totalPrice += vehicle.getPrice();
        }
        totalPrice -= (totalPrice * getExpectedEndValue()); //subtract expected end value
        totalPrice += (totalPrice * getLeaseFee()); //add the lease fee

        return totalPrice;

    }

    @Override
    public double getMonthlyPayment()
    {
        double originalPrice = getTotalPrice();
        double leaseFee = originalPrice * getLeaseFee();
        double residualValue = originalPrice * getExpectedEndValue();

        double financedAmount = originalPrice - residualValue + leaseFee;
        double leaseFinanceInterest = 0.04;
        double monthlyInterestRate =leaseFinanceInterest / 12;
        int leaseTerm = 36;

        double monthlyPayment = financedAmount * monthlyInterestRate
                / (1 - Math.pow(1 + monthlyInterestRate, - leaseTerm));

        return monthlyPayment;

    }
}
