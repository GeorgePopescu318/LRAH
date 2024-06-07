package Items;

import Bids.Bid;
import Exceptions.CSVException;
import Services.CSVActions;
import Users.User;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Variety implements CSVActions{
    protected int id;
    protected String name;
    protected String description;
    protected int manYear;

    protected int desiredPrice;

    protected int soldPrice;

    protected float commissionValue;

    protected int idSeller;
    protected int idBuyer;

    protected int idAuction;
    protected int quantity;


    protected SortedSet<Bid> bids;

    public static String path = "src/Items.csv";

    public Variety(int id, String name, String description, int manYear, int desiredPrice, int idSeller, int quantity, int idAuction) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manYear = manYear;
        this.desiredPrice = desiredPrice;
        this.soldPrice = -1;
        this.commissionValue = (float) (desiredPrice * 0.2);
        this.idSeller = idSeller;
        this.idBuyer = -1;
        this.quantity = quantity;
        this.idAuction = idAuction;
        this.bids = null;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getManYear() {
        return manYear;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setManYear(int manYear) {
        this.manYear = manYear;
    }

    public int getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(int idAuction) {
        this.idAuction = idAuction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variety variety = (Variety) o;
        return id == variety.id && manYear == variety.manYear && Objects.equals(name, variety.name) && Objects.equals(description, variety.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, manYear);
    }

    public void addBids(Bid newBid){
        bids.add(newBid);
    }
    public void printBids(){
        for (Bid bid : bids){
            System.out.println(bid);
        }
    }

    public int getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(int desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public  void MakeBid(Scanner scan, User currentUser) throws Exception {
        int  bidValue = 0;
        bidValue = getValidIntegerInput(scan, "How much do you want to bid?");
        int[] biggestBid = Bid.getHighestBid(Bid.path,this.getId());
        if (this.desiredPrice > biggestBid[0]){
            biggestBid[0]= this.desiredPrice;
        }
        if (bidValue > biggestBid[0]){
            Bid newBid = new Bid(CSVActions.checkNextId(Bid.path),currentUser.getId(),this.getId(),bidValue);
            newBid.writeInCSV(Bid.path);
        }else{
            int option = getValidIntegerInput(scan, "Your bid was smaller than the highest one," +
                    " do you want to try again?\n1. Yes\n2.No");
            if (option == 1){
                bidValue = getValidIntegerInput(scan, "How much do you want to bid?");
            }else{
                return;
            }
            if (bidValue > biggestBid[0]){
                Bid newBid = new Bid(CSVActions.checkNextId(Bid.path),currentUser.getId(),this.getId(),bidValue);
                newBid.writeInCSV(Bid.path);
            }
        }
    }





        public static Variety CreateObject(int idAuction, int idSeller, Scanner scan) throws Exception {
            System.out.println("Enter the item's name: ");
            String name = scan.nextLine();
            System.out.println("Enter the description: ");
            String description = scan.nextLine();

            int manYear = getValidIntegerInput(scan, "Enter the manufacture year: ");
            int desiredPrice = getValidIntegerInput(scan, "Enter the desired price: ");
            int quantity = getValidIntegerInput(scan, "Enter the quantity: ");

            System.out.println("Enter anything to continue");
            scan.nextLine();

            int nextId = CSVActions.checkNextId(Variety.path);
            return new Variety(nextId, name, description, manYear, desiredPrice, idSeller, quantity, idAuction);
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




//    public static Variety CreateObject(int idAuction, int idSeller,Scanner scan) throws Exception {
//        System.out.println("Enter the item's name: ");
//        String name = scan.nextLine();
//        System.out.println("Enter the description: ");
//        String description = scan.nextLine();
//        System.out.println("Enter the manufacture year: ");
//        if((scan.nextLine())
//        {
//
//        }
//        int manYear = Integer.parseInt(scan.nextLine());
//
//        System.out.println("Enter the desired price: ");
//        int desiredPrice = Integer.parseInt(scan.nextLine());
//        System.out.println("Enter the quantity: ");
//        int quantity = Integer.parseInt(scan.nextLine());
//        System.out.println("Enter anything to continue");
//        scan.nextLine();
//
//        int nextId = CSVActions.checkNextId(Variety.path);
//        return  new Variety(nextId, name, description, manYear, desiredPrice, idSeller, quantity, idAuction);
//
//
//    }

    public void printItem(){
        System.out.print("This exquisite item sold in the Variety Auction,called ");
        System.out.print(name);
        System.out.print(", has been crafted in the year of ");
        System.out.print(manYear);
        System.out.print(", and you should consider spending your life savings on it because it is ");
        System.out.print(description);
        System.out.print(".");
        System.out.print("\n We currently have ");
        System.out.print(quantity);
        System.out.print(" of the these bad boys listed in our action and the bidding price starts from ");
        System.out.print(desiredPrice);
        System.out.print(".");
        System.out.println();
    }
    @Override
    public String toString() {
        return "Variety{" +
                "\n\tid=" + id +
                ", \n\tname='" + name + '\'' +
                ", \n\tdescription='" + description + '\'' +
                ", \n\tmanYear=" + manYear +
                ", \n\tdesiredPrice=" + desiredPrice +
                ", \n\tsoldPrice=" + soldPrice +
                ", \n\tcommissionValue=" + commissionValue +
                ", \n\tquantity=" + quantity +
                "\n}";
    }

    @Override
    public void writeInCSV(String path) throws CSVException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            writer.println(varietyToCSV());
        } catch (Exception e) {
            throw new CSVException("Error at openning CSV file" + path);
        }
    }

    protected String varietyToCSV() {
        return this.id + "," +
                this.name + "," +
                this.description + "," +
                this.manYear + "," +
                this.desiredPrice + "," +
                this.soldPrice + "," +
                this.commissionValue + "," +
                this.quantity + "," +
                this.idAuction + "," +
                this.idSeller + "," +
                this.idBuyer;
    }

    public static HashMap<Integer, String> getHashMap(String path, int auctionID) throws CSVException {
        HashMap<Integer,String > itemList = new HashMap<Integer,String >();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null){
                String[] values = line.split(",");
                if(values.length >= 11){
                    int id = Integer.parseInt(values[0].trim());
                    String itemName = values[1].trim();
                    int idAuction = Integer.parseInt(values[8].trim());

                    if (idAuction == auctionID)
                        itemList.put(id, itemName);
                }
            }

        }catch (Exception e){
            throw new CSVException("Error at openning CSV file" + path);
        }
        return itemList;
    }


    public static List<String> searchItemById(String path, int aucId, int itId) throws CSVException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    int itemId = Integer.parseInt(values[0].trim());
                    int auctionId = Integer.parseInt(values[8].trim());
                    if (auctionId == aucId && itemId == itId) {
                        return Arrays.asList(values);
                    }
                }
            }
        }catch(Exception e){
            throw new CSVException("Error at openning CSV file" + path);
        }
        return null;
    }

    public static void modifyItemForAuction(String itemsPath, String bidsPath, int idAuction) throws Exception {
        List<String> modifiedItems = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(itemsPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    int itemId = Integer.parseInt(values[0].trim());
                    int currentAuctionId = Integer.parseInt(values[8].trim());

                    if (currentAuctionId == idAuction) {
                        int[] bidInfo = Bid.getHighestBid(bidsPath, itemId);
                        int maxBid = bidInfo[0];
                        int idBidder = bidInfo[1];

                        if (maxBid != -1) {
                            values[5] = String.valueOf(maxBid);
                            values[10] = String.valueOf(idBidder);
                            double comissionValue = maxBid * 0.2;
                            values[6] = String.valueOf(comissionValue);
                        } else {
                            values[5] = "0";
                            values[10] = values[9];
                        }
                    }
                }
                modifiedItems.add(String.join(",", values));
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(itemsPath))) {
            for (String modifiedItem : modifiedItems) {
                writer.println(modifiedItem);
            }
        }
    }



    //TO DO
    // Print object of UI
}