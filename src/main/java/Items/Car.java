package Items;

import Exceptions.CSVException;
import Services.CSVActions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class Car extends Variety{
    private boolean restoration;

    private String brand;

    public static String path = "src/Items.csv";

    public Car(int id, String name, String description, int manYear, int desiredPrice, int sellerId,int quantity,int idAuction, boolean restoration, String brand) {
        super(id, name, description, manYear, desiredPrice, sellerId,quantity,idAuction);
        this.restoration = restoration;
        this.brand = brand;
    }

    public static Car CreateObject(){;
        return null;
    }

    public boolean isRestoration() {
        return restoration;
    }

    public void setRestoration(boolean restoration) {
        this.restoration = restoration;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return restoration == car.restoration && Objects.equals(brand, car.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), restoration, brand);
    }


    @Override
    public void printItem(){
        System.out.print("The car we are looking at today is a ");
        System.out.print(name);
        System.out.print(", made by ");
        System.out.print(brand);
        System.out.print(" in ");
        System.out.print(manYear);
        System.out.print(", and you should consider spending your life savings on it because it is ");
        System.out.print(description);
        System.out.print(".");
        if(restoration)
        {
            System.out.print("\nAlso, this car has been masterfully restored and it awaits you in perfect condition.");
        }
        System.out.print("\n We currently have ");
        System.out.print(quantity);
        System.out.print(" of the these bad boys listed in our action and the bidding price starts from ");
        System.out.print(desiredPrice);
        System.out.print(".");
        System.out.println();

    }
    @Override
    public void writeInCSV(String path) throws CSVException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            writer.println(carToCSV());
        } catch (IOException e) {
            throw new CSVException("Error at openning CSV file" + path);
        }
    }

    private String carToCSV() {
        return varietyToCSV() + "," +
                this.getBrand() + "," +
                this.isRestoration();
    }




    public static Car CreateObject(int idAuction, int sellerId, Scanner scan) throws Exception {
        System.out.println("Enter the car's brand");
        String brand = scan.nextLine();
        System.out.println("Enter the car's name(model): ");
        String name = scan.nextLine();
        System.out.println("Enter the description: ");
        String description = scan.nextLine();

        int manYear = getValidIntegerInput(scan, "Enter the manufacture year: ");
        int desiredPrice = getValidIntegerInput(scan, "Enter the desired price: ");
        int quantity = getValidIntegerInput(scan, "Enter the quantity: ");
        boolean restoration = getValidBooleanInput(scan, "Enter true if the car has been restored, or false if otherwise: ");

        System.out.println("Enter anything to continue");
        scan.nextLine();

        int nextId = CSVActions.checkNextId(Variety.path);
        return  new Car(nextId, name, description, manYear, desiredPrice, sellerId, quantity, idAuction, restoration, brand);
    }

    private static int getValidIntegerInput(Scanner scan, String prompt) {
        int result;
        while (true) {
            System.out.println(prompt);
            String input = scan.nextLine();
            try {
                result = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return result;
    }
    private static boolean getValidBooleanInput(Scanner scan, String prompt) {
        boolean result;
        while (true) {
            System.out.println(prompt);
            String input = scan.nextLine().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                result = Boolean.parseBoolean(input);
                break;
            } else {
                System.out.println("Invalid input. Please enter 'true' or 'false'.");
            }
        }
        return result;
    }


}