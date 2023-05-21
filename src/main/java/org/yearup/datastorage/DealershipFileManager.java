package org.yearup.datastorage;

import org.yearup.models.Dealership;
import org.yearup.models.Vehicle;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DealershipFileManager
{
    public Dealership getDealership()
    {
        Dealership dealership = null;

        try(FileReader reader = new FileReader("inventory.csv");
            Scanner scanner = new Scanner(reader)
        )
        {
            String line = scanner.nextLine();
            String[] columns = line.split("\\|");
            String name = columns[0];
            String address = columns[1];
            String phone = columns[2];

            // load cars
            ArrayList<Vehicle> inventory = new ArrayList<>();
            while (scanner.hasNext())
            {
                line = scanner.nextLine();
                columns = line.split("\\|");
                String vin = columns[0];
                int year = Integer.parseInt(columns[1]);
                String make = columns[2];
                String model = columns[3];
                String type = columns[4];
                String color = columns[5];
                int miles = Integer.parseInt(columns[6]);
                double price = Double.parseDouble(columns[7]);

                Vehicle vehicle = new Vehicle(vin,year,make, model,type,color,miles, price);
                inventory.add(vehicle);

            }

            dealership = new Dealership(name, address, phone, inventory);
        }
        catch (IOException e)
        {
        }
        return dealership;
    }

    public void saveDealership(Dealership dealership)
    {
        try(PrintWriter writer = new PrintWriter("inventory.csv"))
        {
            writer.printf("%s|%s|%s\n", dealership.getName(), dealership.getAddress(), dealership.getPhone());
            for(Vehicle vehicle: dealership.getAllVehicles())
            {
                writer.printf("%s|%d|%s|%s|%s|%s|%d|%.2f\n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
            }
        }
        catch (IOException e)
        {
        }
    }
}
