package org.yearup.datastorage;

import org.yearup.models.Contract;
import org.yearup.models.LeaseContract;
import org.yearup.models.SalesContract;
import org.yearup.models.Vehicle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ContractDataManager
{
    private ArrayList<Contract> contracts;

    public ContractDataManager()
    {
        ContractFileManager fileManager = new ContractFileManager();
        contracts = fileManager.loadContracts("contracts.csv");
    }

    public void run()
    {
        for (Contract contract : contracts)
        {
            display(contract);
        }
    }

    public void saveContract(Contract contract)
    {

        try (PrintWriter writer = new PrintWriter(new FileWriter("contracts.csv", true)))
        {
            if (contract instanceof SalesContract)
            {
                SalesContract salesContract = (SalesContract) contract;
                writer.printf("%s|%s|%s|%s|%s", contract.getType(), contract.getDate(), contract.getName(), contract.getEmail());

                for (Vehicle vehicle : salesContract.getVehicleSold())
                {
                    writer.printf("|%s|%d|%s|%s|%s|%s|%d|%.2f\n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(),
                            vehicle.getModel(), vehicle.getType(), vehicle.getColor(), vehicle.getOdometer(),
                            vehicle.getPrice());
                }
            } else if (contract instanceof LeaseContract)
            {
                LeaseContract leaseContract = (LeaseContract) contract;
                writer.printf("%s|%s|%s|%s|%s", contract.getType(), contract.getDate(), contract.getName(), contract.getEmail());

                //getvehiclesold class return the list of vehicles sold/leased in the contract
                for (Vehicle vehicle : leaseContract.getVehicleSold())
                {
                    writer.printf("|%s|%d|%s|%s|%s|%s|%d|%.2f\n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(),
                            vehicle.getModel(), vehicle.getType(), vehicle.getColor(), vehicle.getOdometer(),
                            vehicle.getPrice());
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void display(Contract contract)
    {
        System.out.println();
    }
}
