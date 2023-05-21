package org.yearup.datastorage;

import org.yearup.models.*;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class ContractFileManager
{
    public ContractFileManager()
    {
    }

    public ArrayList<Contract> loadContracts(String filePath)
    {
        ArrayList<Contract> contracts = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath);
             Scanner scanner = new Scanner(reader);
        )
        {
            ArrayList<Vehicle> vehicleSold;
            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                String[] columns = line.split("\\|");

                String type = columns[0].toLowerCase();
                String date = columns[1];
                String name = columns[2];
                String email = columns[3];
                String vin = columns[4];
                int year = Integer.parseInt(columns[5]);
                String make = columns[6];
                String model = columns[7];
                String vehicleType = columns[8];
                String color = columns[9];
                int odometer = Integer.parseInt(columns[10]);
                double price = Double.parseDouble(columns[11]);

                switch (type)
                {
                    case "sales":
                        vehicleSold = new ArrayList<>();
                        Vehicle vehicleSales = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                        vehicleSold.add(vehicleSales);

                        double recordingFee = Double.parseDouble(columns[12]);
                        double processingFee = Double.parseDouble(columns[13]);
                        double totalCostSales = Double.parseDouble(columns[14]);
                        boolean financeOption = Boolean.parseBoolean(columns[15]);
                        double monthlyPayment = Double.parseDouble(columns[16]);
                        contracts.add(new SalesContract(date, name, email, vehicleSold, financeOption));
                        break;
                    case "lease":
                        vehicleSold = new ArrayList<>();
                        Vehicle vehicleLease = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                        vehicleSold.add(vehicleLease);
                        double expectedEndValue = Double.parseDouble(columns[12]);
                        double leaseFee = Double.parseDouble(columns[13]);
                        double totalCostLease = Double.parseDouble(columns[14]);
                        double monthlyPaymentLease = Double.parseDouble(columns[16]);
                        contracts.add(new LeaseContract(date, name, email, vehicleSold));
                        break;

                }
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return contracts;
    }

    public void fileAppend(String filePath, String newContent)
    {


        try (FileWriter writer = new FileWriter(filePath, true))
        {
            writer.write(newContent);
            System.out.println("Appended content: " + newContent);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}