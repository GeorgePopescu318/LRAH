package Services;
import Bids.Bid;
import Items.Antique;
import Items.Art;
import Items.Car;
import Items.Variety;
import Users.Admin;
import Users.Participant;
import Users.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import Auctions.Auction;

import java.util.Scanner;
import java.util.regex.Pattern;

import Services.CSVActions;

import javax.swing.*;

public class  Menu {
    private static Menu single_menu = null;

    private User currentUser;
    private Auction currentAuction;
    private Variety currentObject;

    private Menu(){}

    public static synchronized Menu getInstance()
    {
        if (single_menu == null) {
            single_menu = new Menu();
        }

        return single_menu;
    }

    public void InitUser(Scanner scan) throws Exception {
        Pattern usernamePattern = Pattern.compile("^[a-zA-Z ]+$"); // Regex for only alphabetic characters
        System.out.println("    WELCOME\n");
        System.out.println("1.Login\n2.Register");

        //Jakey
        int option = Integer.parseInt(scan.nextLine());
        boolean passedLogin = false;
        if (option == 1){
            System.out.println("LOGIN");
            while(!passedLogin){
            System.out.println("Enter name:");
            String name = scan.nextLine();
            System.out.println("Enter password:");
            String password = scan.nextLine();

            if (User.getUserId(User.path, name, password) == -1){
                System.out.println("Wrong user or password. Try again");
            }
            else {
                passedLogin = true;
                if (User.isUserAdmin(User.path, name, password)){
                    currentUser = new Admin(User.getUserId(User.path, name, password), name, password,true);
                    System.out.println(currentUser.isAdmin());
                }else {
                    currentUser = new Participant(User.getUserId(User.path, name, password), name, password);
                }
            }
            }

        }else{
            System.out.println("REGISTER\n");
            boolean validUsername = false;
            String name ="";
            System.out.print("Enter your Name:");
            while (!validUsername) {
                name = scan.nextLine();
                if (!Objects.equals(name, "")) {
                    Matcher matcher = usernamePattern.matcher(name);
                    if (matcher.matches())
                        validUsername = true;
                    else
                        System.out.println("Invalid name. Only alphabetic characters are allowed. Please try again.");
                }
            }
            System.out.println("Enter password:");
            String password = scan.nextLine();
            int highestID = CSVActions.checkNextId((User.path));
            if (!name.isEmpty()) {
                currentUser = new Participant(highestID, name, password);
            }
            ((CSVActions) currentUser).writeInCSV(User.path);



        }
    }

    public void MainMenu(Scanner scan) throws Exception {
        do {
        System.out.println("MAIN MENU\n");
        System.out.println("1.Open profile");
        System.out.println("2.Check ongoing auctions");
        System.out.println("3.Check future auctions");
        System.out.println("4.Exit");
            if (currentUser.isAdmin()){
                System.out.println("5.Check allItems");
            }
        int option = Integer.parseInt(scan.nextLine().strip());
        //jakey cu regex
        Pattern numberPattern = Pattern.compile("[1-2]+\\s*");
        if (option == 1) {
            System.out.println(currentUser);
            List<String> userItems = currentUser.showMyItems(Variety.path);
            System.out.println("Your items:");
            for (String item : userItems){
                System.out.println(item);
            }
            currentUser.changeCredentials(scan);
        }else if(option == 2){
            currentAuction = CheckOpenAuctions(scan);
            if (currentAuction != null) {
                printOpenAuction(scan,currentUser,currentAuction);
            }
        }else if(option == 3){
            currentAuction = CheckFutureAuctions(scan);
            if (currentAuction != null) {
                printFutureAuction(scan,currentUser,currentAuction);
            }
        }
            else if(option == 5 && currentUser.isAdmin()){
                List<String> allItems = currentUser.getAllItems(Variety.path);
                for (String item : allItems){
                    System.out.println(item);
                }
        }else{
            break;
        }
            //1
        } while (true);

    }

    public Auction CheckFutureAuctions(Scanner scan) throws Exception {
        HashMap<Integer, String> auctionsList = Auction.getHashMap(Auction.path, false);
        System.out.println("    FUTURE AUCTIONS");
        for (Integer i : auctionsList.keySet()) {
            System.out.println("    " + i + "." + auctionsList.get(i));
        }
        if (currentUser.isAdmin()){
            System.out.println("    " + 1 + ".Add Auction");
        }
        System.out.println("    " + 0 + ".Exit");
        int option = Integer.parseInt(scan.nextLine());
        if (option != 0 && option != 1) {
            List<String> auctionSpecs = Auction.searchAuctionById(Auction.path, option);
            return new Auction(Integer.parseInt(auctionSpecs.get(0)), LocalDate.parse(auctionSpecs.get(1)), auctionSpecs.get(2));

        }else if (option == 1){
            return currentUser.CreateAuction(scan);
        }
        else {
            return null;
        }
    }

    public Auction CheckOpenAuctions(Scanner scan) throws Exception {
        HashMap<Integer, String> auctionsList = Auction.getHashMap(Auction.path, true);
        System.out.println("    OPEN AUCTIONS");
        for (Integer i : auctionsList.keySet()) {
            System.out.println("    " + i + "." + auctionsList.get(i));
        }
        System.out.println("    " + 0 + ".Exit");
        int option = Integer.parseInt(scan.nextLine());
        if (option != 0 && auctionsList.containsKey(option)) {
            List<String> auctionSpecs = Auction.searchAuctionById(Auction.path, option);
            return new Auction(Integer.parseInt(auctionSpecs.get(0)), LocalDate.parse(auctionSpecs.get(1)), auctionSpecs.get(2));
        } else {
            return null;
        }
    }

    public void printFutureAuction(Scanner scan, User currentUser,Auction auction) throws Exception {

        do {
            Variety currentItem = null;
            int option = 2;

            System.out.println("    AUCTION NR: " + auction.getId());
            System.out.println("    Date: " + auction.getEventDate());
            System.out.println("    Type: " + auction.getAuctionType());
            System.out.println("    Added items until now");
            //functie de returnat hashmap de tip HashMap<Integer, String>, iteme care au la auctionId id ul de la auctionul
            // asta in care suntem acum Bezel
            HashMap<Integer, String> auctionsList = Variety.getHashMap(Variety.path, auction.getId());//aici
            for (Integer i : auctionsList.keySet()) {
                System.out.println("    " + i + "." + auctionsList.get(i));
            }
            if (currentUser.isAdmin()){
                System.out.println("2.Change auction type");
            }
            System.out.println("1.Introduce an item");
            System.out.println("0.Exit");
            option = Integer.parseInt(scan.nextLine());
            if (auctionsList.containsKey(option) || option == 1 || option == 0 || option == 2) {
                if (option > 3) {
                    String[] itemTypes = {Auction.AuctionTypes.Variety.toString(), Auction.AuctionTypes.Antique.toString(), Auction.AuctionTypes.Art.toString(), Auction.AuctionTypes.Car.toString()};
                    int auctionId = auction.getId();
                    List<String> itemParams = Variety.searchItemById(Variety.path, auctionId, option);
//                    System.out.println(itemParams);
                    if (auction.getAuctionType().equals(itemTypes[0])) {
                        currentItem = new Variety(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2),
                                Integer.parseInt(itemParams.get(3)), Integer.parseInt(itemParams.get(4)),
                                Integer.parseInt(itemParams.get(7)), Integer.parseInt(itemParams.get(8)),
                                Integer.parseInt(itemParams.get(9)));
                    } else if (auction.getAuctionType().equals(itemTypes[1])) {
                        currentItem = new Antique(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2),
                                Integer.parseInt(itemParams.get(3)), Integer.parseInt(itemParams.get(4)),
                                Integer.parseInt(itemParams.get(7)), Integer.parseInt(itemParams.get(8)),
                                Integer.parseInt(itemParams.get(9)), itemParams.get(11), itemParams.get(12));
                    } else if (auction.getAuctionType().equals(itemTypes[2])) {
                        currentItem = new Art(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2),
                                Integer.parseInt(itemParams.get(3)), Integer.parseInt(itemParams.get(4)),
                                Integer.parseInt(itemParams.get(7)), Integer.parseInt(itemParams.get(8)),
                                Integer.parseInt(itemParams.get(9)), itemParams.get(11), itemParams.get(12), Integer.parseInt(itemParams.get(13)), Integer.parseInt(itemParams.get(14)));
                    } else if (auction.getAuctionType().equals(itemTypes[3])) {
                        currentItem = new Car(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2),
                                Integer.parseInt(itemParams.get(3)), Integer.parseInt(itemParams.get(4)),
                                Integer.parseInt(itemParams.get(7)), Integer.parseInt(itemParams.get(8)),
                                Integer.parseInt(itemParams.get(9)), Boolean.parseBoolean(itemParams.get(11)), itemParams.get(12));
                    }

                } else if (option == 1) {
                    String[] itemTypes = {Auction.AuctionTypes.Variety.toString(), Auction.AuctionTypes.Antique.toString(), Auction.AuctionTypes.Art.toString(), Auction.AuctionTypes.Car.toString()};
                    int auctionId = auction.getId();
//            System.out.println(itemParams);
                    if (auction.getAuctionType().equals(itemTypes[0])) {
                        currentItem = Variety.CreateObject(auction.getId(), currentUser.getId(), scan);
                    } else if (auction.getAuctionType().equals(itemTypes[1])) {
                        currentItem = Antique.CreateObject(auction.getId(), currentUser.getId(), scan);
                    } else if (auction.getAuctionType().equals(itemTypes[2])) {
                        currentItem = Art.CreateObject(auction.getId(), currentUser.getId(), scan);
                    } else if (auction.getAuctionType().equals(itemTypes[3])) {
                        currentItem = Car.CreateObject(auction.getId(), currentUser.getId(), scan);
                    }

                    currentItem.writeInCSV(Variety.path);


                }else if (option == 2){
                    currentUser.changeAuction(scan,auction.getId(),true);
                    break;
                } else if (option == 0) {
                    break;
                }
                if (currentItem != null) {
                    currentItem.printItem();
                }
            }else{
                System.out.println("ERROR:Enter an id from the list.");
            }
        }
        while (true);

    }

    public void printOpenAuction(Scanner scan, User currentUser,Auction auction) throws Exception {
        do {
            Variety currentItem = null;

            System.out.println("    AUCTION NR: " + auction.getId());
            System.out.println("    Date: " + auction.getEventDate());
            System.out.println("    Type: " + auction.getAuctionType());
            System.out.println("    Items");
            //functie de returnat hashmap de tip HashMap<Integer, String>, iteme care au la auctionId id ul de la auctionul
            // asta in care suntem acum Bezel
            HashMap<Integer, String> auctionsList = Variety.getHashMap(Variety.path, auction.getId());//aici
            for (Integer i : auctionsList.keySet()) {
                System.out.println("    " + i + "." + auctionsList.get(i));
            }
            if (currentUser.isAdmin()){
                System.out.println("2.Delete auction");
            }
            if (currentUser.isAdmin()){
                System.out.println("1.Change auction type");
            }
            System.out.println("0.Exit");
            int option = Integer.parseInt(scan.nextLine());
            if (auctionsList.containsKey(option) || option == 1 || option == 0 || option == 2) {
                if (option > 2) {
                    String[] itemTypes = {Auction.AuctionTypes.Variety.toString(), Auction.AuctionTypes.Antique.toString(), Auction.AuctionTypes.Art.toString(), Auction.AuctionTypes.Car.toString()};
                    int auctionId = auction.getId();
                    List<String> itemParams = Variety.searchItemById(Variety.path, auctionId, option);
                    //            System.out.println(itemParams);
                    if (auction.getAuctionType().equals(itemTypes[0])) {
                        currentItem = new Variety(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2),
                                Integer.parseInt(itemParams.get(3)), Integer.parseInt(itemParams.get(4)),
                                Integer.parseInt(itemParams.get(7)), Integer.parseInt(itemParams.get(8)),
                                Integer.parseInt(itemParams.get(9)));
                    } else if (auction.getAuctionType().equals(itemTypes[1])) {
                        currentItem = new Antique(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2),
                                Integer.parseInt(itemParams.get(3)), Integer.parseInt(itemParams.get(4)),
                                Integer.parseInt(itemParams.get(7)), Integer.parseInt(itemParams.get(8)),
                                Integer.parseInt(itemParams.get(9)), itemParams.get(11), itemParams.get(12));
                    } else if (auction.getAuctionType().equals(itemTypes[2])) {
                        currentItem = new Art(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2),
                                Integer.parseInt(itemParams.get(3)), Integer.parseInt(itemParams.get(4)),
                                Integer.parseInt(itemParams.get(7)), Integer.parseInt(itemParams.get(8)),
                                Integer.parseInt(itemParams.get(9)), itemParams.get(11), itemParams.get(12), Integer.parseInt(itemParams.get(13)), Integer.parseInt(itemParams.get(14)));
                    } else if (auction.getAuctionType().equals(itemTypes[3])) {
                        currentItem = new Car(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2),
                                Integer.parseInt(itemParams.get(3)), Integer.parseInt(itemParams.get(4)),
                                Integer.parseInt(itemParams.get(7)), Integer.parseInt(itemParams.get(8)),
                                Integer.parseInt(itemParams.get(9)), Boolean.parseBoolean(itemParams.get(11)), itemParams.get(12));
                    }

                } else if (option == 1){
//                    System.out.println(Boolean.parseBoolean(auction.getAuctionType()));
                    currentUser.changeAuction(scan,auction.getId(),false);
                    break;
                } else if (option == 2){
//                    System.out.println(Boolean.parseBoolean(auction.getAuctionType()));
                    currentUser.deleteAuction(scan,currentAuction.getId());
                    break;
                } else {
                    break;
                }
                if (currentItem != null) {
                    currentItem.printItem();

                    int biggestBid = Bid.getHighestBid(Bid.path,currentItem.getId())[0];

                    if (currentItem.getDesiredPrice() > biggestBid){
                        biggestBid= currentItem.getDesiredPrice();
                    }
                    System.out.println("Highest bid is "+ biggestBid+"$");
                    System.out.println("Do you want to bid? Press 1");
                    int optionBid = Integer.parseInt(scan.nextLine());
                    if (optionBid == 1){
                        currentItem.MakeBid(scan,currentUser);
                    }
                    else{
                        return;
                    }
                }
                //aici populam lista asta
            }
        }while(true);
    }


}