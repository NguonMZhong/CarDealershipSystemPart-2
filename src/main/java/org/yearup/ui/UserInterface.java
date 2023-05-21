package org.yearup.ui;

import org.yearup.datastorage.ContractDataManager;
import org.yearup.datastorage.DealershipFileManager;
import org.yearup.models.Contract;
import org.yearup.models.Dealership;
import org.yearup.models.Vehicle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface
{
    Scanner userInput = new Scanner(System.in);
    private DealershipFileManager fileManager;
    private Dealership dealership;

    public UserInterface()
    {
        fileManager = new DealershipFileManager();
        dealership = fileManager.getDealership();
    }

    public void run()
    {

        while(true)
        {
            System.out.println();
            displayHomeScreen();
            try
            {
                int selection = getUserInputInt("Make a selection");
                System.out.println();

                switch (selection)
                {
                    case 1: // search by price range
                        processGetByPrice();
                        break;
                    case 2: // search by make model
                        processGetByMakeModel();
                        break;
                    case 3: // search by year
                        processGetByYear();
                        break;
                    case 4:
                        processGetByColor();
                        break;
                    case 5:
                        processGetByMileage();
                        break;
                    case 6:
                        processGetByVehicleType();
                        break;
                    case 7:
                        processGetAllVehicles();
                        break;
                    case 8:
                        processAddVehicle();
                        break;
                    case 9:
                        processDeleteVehicle();
                        break;
                    case 10:
                        processRecordSaleOrLease();
                        break;
                    case 99: // exit
                        fileManager.saveDealership(dealership);
                        return;
                    default:
                        System.out.println("Not a valid selection.");
                }
            }
            catch(Exception ex)
            {
                System.out.println("Please select a valid option.");
            }

        }

    }

    public String getUserInputString(String message)
    {
        System.out.print(message + ": ");
        return userInput.nextLine().strip();
    }

    public int getUserInputInt(String message)
    {
        return Integer.parseInt(getUserInputString(message));
    }

    public double getUserInputDouble(String message)
    {
        return Double.parseDouble(getUserInputString(message));
    }

    public void displayHomeScreen()
    {
        System.out.println();
        System.out.println("What do you want to do?");
        System.out.println(" 1) search by price range");
        System.out.println(" 2) search by make / model");
        System.out.println(" 3) search by year range");
        System.out.println(" 4) search by color");
        System.out.println(" 5) search by mileage range");
        System.out.println(" 6) search by type (sedan, truck , SUV, coupe");
        System.out.println(" 7) list all vehicles");
        System.out.println(" 8) add vehicle");
        System.out.println(" 9) remove vehicle");
        System.out.println("10) SELL/LEASE A VEHICLE");
        System.out.println(" 99) exit");

    }

    private void displaySearchResults(ArrayList<Vehicle> results)
    {
        if(results.size() == 0)
        {
            System.out.println("No search results.");
            return;
        }

        for(Vehicle vehicle: results)
        {
            System.out.printf("%-5d %-15s %-15s %-8s %-7d $ %.2f\n" ,vehicle.getYear(), vehicle.getMake()
                    , vehicle.getModel(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
        }
    }

    public void processGetByPrice()
    {
        // declare variables
        double min = getUserInputDouble("Enter the lowest price");
        double max = getUserInputDouble("Enter the highest price");

        // get all cars from the dealership
        ArrayList<Vehicle> results = dealership.getByPrice(min, max);

        // display the vehicles
        System.out.println("Search by Price: " + min + " - " + max);
        System.out.println("------------------------------------");

        displaySearchResults(results);

    }

    public void processGetByMakeModel()
    {

        // declare variables
        String make = getUserInputString("Enter the make");
        String model = getUserInputString("Enter the model");

        // get all cars from the dealership
        ArrayList<Vehicle> results = dealership.getByMakeModel(make, model);

        // display the vehicles
        System.out.println("Search by Make / Model: " + make + " / " + model);
        System.out.println("------------------------------------");

        displaySearchResults(results);
    }

    public void processGetByYear()
    {
        // declare variables
        int min = getUserInputInt("Enter the lowest year");
        int max = getUserInputInt("Enter the highest year");

        // get all cars from the dealership
        ArrayList<Vehicle> results = dealership.getByYear(min, max);

        // display the vehicles
        System.out.println("Search by Year: " + min + " - " + max);
        System.out.println("------------------------------------");

        displaySearchResults(results);
    }

    public void processGetByColor()
    {
        // declare variables
        String color = getUserInputString("Enter the color");

        // get all cars from the dealership
        ArrayList<Vehicle> results = dealership.getByColor(color);

        // display the vehicles
        System.out.println("Search by Color: " + color);
        System.out.println("------------------------------------");

        displaySearchResults(results);
    }

    public void processGetByMileage()
    {
        // declare variables
        int min = getUserInputInt("Enter the lowest mileage");
        int max = getUserInputInt("Enter the highest mileage");

        // get all cars from the dealership
        ArrayList<Vehicle> results = dealership.getByMileage(min, max);

        // display the vehicles
        System.out.println("Search by Mileage: " + min + " - " + max);
        System.out.println("------------------------------------");

        displaySearchResults(results);
    }

    public void processGetByVehicleType()
    {
        // declare variables
        String type = getUserInputString("Enter the vehicle type");

        // get all cars from the dealership
        ArrayList<Vehicle> results = dealership.getByType(type);

        // display the vehicles
        System.out.println("Search by Vehicle Type: " + type);
        System.out.println("------------------------------------");

        displaySearchResults(results);
    }

    public void processGetAllVehicles()
    {
        // get all cars from the dealership
        ArrayList<Vehicle> results = dealership.getAllVehicles();

        // display the vehicles
        System.out.println("Display All Vehicles: ");
        System.out.println("------------------------------------");

        displaySearchResults(results);
    }

    public void processAddVehicle()
    {
        System.out.println("Add new vehicle:");
        System.out.println("---------------------");
        String vin = getUserInputString("Enter vin");
        int year = getUserInputInt("Enter year");
        String make = getUserInputString("Enter make");
        String model = getUserInputString("Enter model");
        String type = getUserInputString("Enter type");
        String color = getUserInputString("Enter color");
        int miles = getUserInputInt("Enter miles");
        double price = getUserInputDouble("Enter price");

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, miles, price);

        dealership.addVehicle(vehicle);

        System.out.println();
        System.out.printf("%s %s has been added.\n", vehicle.getMake(),vehicle.getModel());
        System.out.println();
    }

    public void processDeleteVehicle()
    {
        System.out.println("Remove vehicle:");
        System.out.println("---------------------");
        String vin = getUserInputString("Enter vin of the car to remove");

        Vehicle vehicle = dealership.findByVin(vin);
        if(vehicle == null)
        {
            System.out.printf("VIN: %s was not found.\n", vin);
            return;
        }

        dealership.removeVehicle(vehicle);
        System.out.printf("Vehicle [%s] was successfully removed.\n", vin);
    }
    public void processRecordSaleOrLease()
    {
        System.out.println("Record a sale or lease:");
        System.out.println("-------------------------");
        String vin = getUserInputString("Enter the VIN of the vehicle");
        Vehicle vehicle = dealership.findByVin(vin);

        if (vehicle == null)
        {
            System.out.println("Vehicle not found.");
            return;
        }
        if (vehicle.getYear() > 3)
        {
            System.out.println("Unfortunately you can't lease a vehicle over 3 years old.");
            return;
        }

        String date = getUserInputString("Enter the date (MM/dd/YYYY");
        String name = getUserInputString("Enter your name.");
        String email = getUserInputString("Enter your email address.");

        int choice = getUserInputInt("Select if you want to sale or to lease");
        Contract contract;

        switch (choice)
        {
            case 1:
                contract = salesContract (date, name, email, vehicle);
                dealership.removeVehicle(vehicle);
                break;
            case 2:
                contract = leaseContract(date, name, email, vehicle);
                dealership.removeVehicle(vehicle);
                break;
            default:
                System.out.println("Invalid option.Try again.");
                return;
        }
    }

    private Contract salesContract(String date, String name, String email, Vehicle vehicle)
    {
        return null;
    }

    private Contract leaseContract(String date, String name, String email, Vehicle vehicle)
    {
        return null;
    }

    public void saveContract(Contract contract)
    {
        ContractDataManager contractDataManager = new ContractDataManager();
        contractDataManager.saveContract(contract);

        System.out.println("Contract has been saved.");
    }
}
