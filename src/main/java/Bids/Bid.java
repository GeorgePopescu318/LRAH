package Bids;

import Exceptions.CSVException;
import Items.Antique;
import Items.Variety;
import Services.CSVActions;

import java.io.*;
import java.util.*;

public class Bid implements CSVActions {
    private int id;
    private int idBidder;
    private int idItem;
    private int biddingPrice;

    public static String path = "src/Bids.csv";

    public Bid(int id, int idBidder, int idItem, int biddingPrice) {
        this.id = id;
        this.idBidder = idBidder;
        this.idItem = idItem;
        this.biddingPrice = biddingPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBidder() {
        return idBidder;
    }

    public void setIdBidder(int idBidder) {
        this.idBidder = idBidder;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(int biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return id == bid.id && idBidder == bid.idBidder && idItem == bid.idItem && biddingPrice == bid.biddingPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idBidder, idItem, biddingPrice);
    }

    @Override
    public String toString() {
        return "Bid: " +
                "idBidder=" + idBidder +
                ", biddingPrice=" + biddingPrice;
    }

    @Override
    public void writeInCSV(String path) throws CSVException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))){
            writer.println(bidToCSV());

        }catch(IOException e){
            throw new CSVException("Error at openning CSV file" + path);        }
    }

    private String bidToCSV(){
        return  this.id + "," +
                this.idBidder + "," +
                this.idItem + "," +
                this.biddingPrice;
    }

    public static int[] getHighestBid(String path, int id) throws Exception {
        int maxBid = -1;
        int idBidder = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int bidderId = Integer.parseInt(values[1].trim());
                    int itemId = Integer.parseInt(values[2].trim());
                    int bidPrice = Integer.parseInt(values[3].trim());

                    if (itemId == id && bidPrice > maxBid) {
                        maxBid = bidPrice;
                        idBidder = bidderId;
                    }
                }
            }
        }
        return new int[]{maxBid, idBidder};
    }

    public static Bid CreateObject(int bidderId, int itemId, Scanner scan) throws Exception {

        int biddingPrice = getValidIntegerInput(scan, "Enter the price for your bid : ");

        System.out.println("Enter anything to continue");
        scan.nextLine();

        int nextId = CSVActions.checkNextId(Bid.path);
        return new Bid(nextId, bidderId, itemId, biddingPrice);
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

    public static List<String> showBidsForItem(String path, int itId) throws CSVException {
        ArrayList allBids = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int itemId = Integer.parseInt(values[2].trim());
                    if (itemId == itId) {
                        allBids.addAll(List.of(values));
                    }
                }
            }
        }catch(Exception e){
            throw new CSVException("Error at openning CSV file" + path);
        }
        return allBids;
    }





}
