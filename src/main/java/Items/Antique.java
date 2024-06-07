package Items;

import java.util.*;

import Exceptions.CSVException;
import Services.CSVActions;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import Bids.Bid;
import Users.Participant;

public class Antique extends Variety{
    private String originLocation;
    private String material;

    public static String path = "src/Items.csv";

    public Antique(int id, String name, String description, int manYear, int desiredPrice, int sellerId,int idAuction, int quantity, String originLocation, String material) {
        super(id, name, description, manYear, desiredPrice, sellerId, quantity,idAuction);
        this.originLocation = originLocation;
        this.material = material;
    }

    public String getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(String originLocation) {
        this.originLocation = originLocation;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Antique antique = (Antique) o;
        return Objects.equals(originLocation, antique.originLocation) && Objects.equals(material, antique.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), originLocation, material);
    }



    @Override
    public void printItem(){
        System.out.print("This fabulous piece we are looking at today is a ");
        System.out.print(name);
        System.out.print(", made from the finest ");
        System.out.print(material);
        System.out.print(".");
        System.out.print("This antique piece hails from the lands of ");
        System.out.print(originLocation);
        System.out.print(" and dates roughly from the year of ");
        System.out.print(manYear);
        System.out.print(".");
        System.out.print("\nFrom the information gathered from the previous owners, this exquisite piece is a ");
        System.out.print(description);
        System.out.print(".");
        System.out.print("\nWe currently have ");
        System.out.print(quantity);
        System.out.print(" of the these bad boys listed in our action and the bidding price starts from ");
        System.out.print(desiredPrice);
        System.out.print(".");
        System.out.println();

    }
    @Override
    public void writeInCSV (String path) throws CSVException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
                writer.println(antiqueToCSV());
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            throw new CSVException("Error at openning CSV file" + path);
        }
    }

    @Override
    public String toString() {
        return "Antique{" +
                "originLocation='" + originLocation + '\'' +
                ", material='" + material + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", manYear=" + manYear +
                ", desiredPrice=" + desiredPrice +
                ", soldPrice=" + soldPrice +
                ", commissionValue=" + commissionValue +
                ", idSeller=" + idSeller +
                ", idBuyer=" + idBuyer +
                ", idAuction=" + idAuction +
                ", quantity=" + quantity +
                ", bids=" + bids +
                '}';
    }

    private String antiqueToCSV() {
        return varietyToCSV() + "," +
                this.getOriginLocation() + "," +
                this.getMaterial();
    }


    public HashMap<Integer, String> getHashMap(String path, Antique object) throws Exception {
        return new HashMap<>();
    }

//    private String bidsToCSV(SortedSet<Bid> bids) {
//        StringBuilder sb = new StringBuilder();
//
//        for (Bid bid : bids) {
//            sb.append(bid.toString()).append(";"); // Adjust based on Bid's toString implementation
//        }
//        return sb.toString();
//    }




//    public static Antique CreateObject(int idAuction, int sellerId,Scanner scan) throws Exception {
//        System.out.println("Enter the item's name: ");
//        String name = scan.nextLine();
//        System.out.println("Enter the description: ");
//        String description = scan.nextLine();
//        System.out.println("Enter the manufacture's year: ");
//        int manYear = Integer.parseInt(scan.nextLine());
//        System.out.println("Enter the desired price: ");
//        int desiredPrice = Integer.parseInt(scan.nextLine());
//        System.out.println("Enter the quantity: ");
//        int quantity = Integer.parseInt(scan.nextLine());
//        System.out.println("Enter the location where the item originates from: ");
//        String originLocation = scan.nextLine();
//        System.out.println("Enter the material used on the item's manufacture: ");
//        String material = scan.nextLine();
//
//        int nextId = CSVActions.checkNextId(Variety.path);
//
//        return  new Antique(nextId, name, description, manYear, desiredPrice, sellerId, idAuction, quantity, originLocation, material);
//
//    }



    public static Antique CreateObject(int idAuction, int sellerId, Scanner scan) throws Exception {
        System.out.println("Enter the item's name: ");
        String name = scan.nextLine();
        System.out.println("Enter the description: ");
        String description = scan.nextLine();

        int manYear = getValidIntegerInput(scan, "Enter the manufacture year: ");
        int desiredPrice = getValidIntegerInput(scan, "Enter the desired price: ");
        int quantity = getValidIntegerInput(scan, "Enter the quantity: ");


        System.out.println("Enter the location where the item originates from: ");
        String originLocation = scan.nextLine();
        System.out.println("Enter the material used on the item's manufacture: ");
        String material = scan.nextLine();

        System.out.println("Enter anything to continue");
        scan.nextLine();

        int nextId = CSVActions.checkNextId(Variety.path);
        return  new Antique(nextId, name, description, manYear, desiredPrice, sellerId, idAuction, quantity, originLocation, material);
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
}
