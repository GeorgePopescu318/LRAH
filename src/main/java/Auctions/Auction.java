package Auctions;

import Exceptions.CSVException;
import Items.Antique;
import Items.Art;
import Items.Car;
import Items.Variety;
import Services.CSVActions;
import Users.User;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Auction implements CSVActions {
    private int id;
    private LocalDate auctionDate;

    public String getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(String auctionType) {
        this.auctionType = auctionType;
    }

    private String auctionType;

    private List<Variety> items;
    public static String path = "src/Auctions.csv";
    private boolean isOpen;

    public enum AuctionTypes {
        Variety,
        Antique,
        Art,
        Car
    }

    ;

    public Auction(int id, LocalDate eventDate, String auctionType) {
        this.id = id;
        this.auctionDate = eventDate;
        boolean validType = false;
        for (AuctionTypes auction : AuctionTypes.values()) {
            if (auction.name().equalsIgnoreCase(auctionType)) {
                this.auctionType = auctionType;
                validType = true;
                break;
            }
        }
        if (!validType) {
            throw new ArithmeticException("You can't create that type of auction:" + auctionType);
        }
        this.isOpen = false;
        this.items = null;
    }

    public void addItems(Variety item) {
        if (!item.getClass().getSimpleName().equalsIgnoreCase(auctionType)) {
            throw new ArithmeticException("You can't add that type of item:" + item.getClass().getName() + " this action only accepts: " + auctionType);
        } else {
            items.add(item);
        }

    }







    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return auctionDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.auctionDate = eventDate;
    }

    public List<Variety> getItems() {
        return items;
    }

    public void setItems(List<Variety> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return id == auction.id && Objects.equals(auctionDate, auction.auctionDate) && Objects.equals(items, auction.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, auctionDate, items);
    }

    @Override
    public void writeInCSV(String path) throws CSVException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            writer.println(auctionToCSV());

        } catch (IOException e) {
            throw new CSVException("Error at openning CSV file" + path);
        }
    }

    private String auctionToCSV() {
        return this.id + "," +
                this.auctionDate + "," +
                this.auctionType + "," +
                this.isOpen;
    }


    public static HashMap<Integer, String> getHashMap(String path, boolean isOpenClosed) throws Exception {
        HashMap<Integer, String> auctionsList = new HashMap<Integer, String>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int id = Integer.parseInt(values[0].trim());
                    String auctionType = values[2].trim();
                    boolean open = Boolean.parseBoolean(values[3].trim());

                    if (open == isOpenClosed)
                        auctionsList.put(id, auctionType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auctionsList;
    }



    public static List<String> searchAuctionById(String path, int id) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int currentId = Integer.parseInt(values[0].trim());
                    if (currentId == id) {
                        return Arrays.asList(values);
                    }
                }
            }
        }
        return null;
    }

    public static void updateAuctionStatus(String path, int auctionId, boolean newStatus) throws CSVException {
        List<String> lines = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int currentId = Integer.parseInt(values[0].trim());
                    if (currentId == auctionId) {
                        values[3] = String.valueOf(newStatus);
                    }
                    lines.add(String.join(",", values));
                }
            }
        }catch (Exception e){
            throw new CSVException("Error at openning file" + path);
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (String l : lines) {
                writer.println(l);
            }
        }catch (Exception e){
            throw  new CSVException("Error at openning file" + path);

        }
    }

    public static void deleteAuction(String path, int auctionId) throws CSVException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int currentId = Integer.parseInt(values[0].trim());
                    if (currentId != auctionId) {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            throw new CSVException("Error when reading from file " + path, e);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (String l : lines) {
                writer.println(l);
            }
        } catch (IOException e) {
            throw new CSVException("Error when writing to file " + path, e);
        }
    }


}
