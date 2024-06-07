package Users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import Auctions.Auction;
import Exceptions.CSVException;
import Services.CSVActions;
import Services.Menu;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public abstract class User{
    protected int id;
    protected String name;
    protected String password;
    protected boolean isAdmin;
    public static String path = "src/Users.csv";
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.isAdmin = false;
    }

    public User(int id, String name, String password, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public void changeAuction(Scanner scan, int auctionId, boolean changedType) throws IOException, CSVException {}

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean isUserAdmin(String path, String name, String password)throws Exception{
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null){
                String values[] = line.split(",");
                if(values.length >= 4){
                    String userName = values[1].trim();
                    String userPass = values[2].trim();
                    boolean userAdmin = Boolean.parseBoolean(values[3].trim());


                    if(userName.equalsIgnoreCase(name) && userPass.equalsIgnoreCase(password) && userAdmin){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getUserId(String path, String name, String password) throws Exception{
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null){
                String[] values = line.split(",");
                if(values.length >= 3){
                    int id = Integer.parseInt(values[0].trim());
                    String userName = values[1].trim();
                    String userPass = values[2].trim();


                    if(userName.equalsIgnoreCase(name) && userPass.equalsIgnoreCase(password)){
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public void updateUserCredentials(String path, String name, String password) throws Exception{
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            String[] values = lines.get(i).split(",");
            if (values.length >= 3) {
                int userId = Integer.parseInt(values[0].trim());
                if (userId == id) {
                    values[1] = name;
                    values[2] = password;
                    lines.set(i, String.join(",", values));
                    break;
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }



    @Override
    public String toString() {
        return "Your login credentials are the following:" +
                id +
                ", username='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public Auction CreateAuction(Scanner scan) throws Exception {
        return null;
    }

    public List<String> showMyItems (String path) throws CSVException {
        List<String> matchingItems = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    int buyerId = Integer.parseInt(values[10].trim());
                    if (buyerId == this.id) {
                        matchingItems.add(line);
                    }
                }
            }
        }catch (Exception e){
            throw new CSVException("Error at openning file" + path);
        }
        return matchingItems;
    }

    public List<String> getAllItems(String itemsPath) throws CSVException {
        List<String> itemsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(itemsPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                itemsList.add(line);
            }
        }catch (Exception e){
            throw new CSVException("Error at opening file:" + path);
        }
        return itemsList;
    }

    public void deleteAuction(Scanner scan, int auctionId) throws Exception {}

    public void changeCredentials(Scanner scan) throws Exception {
        Pattern usernamePattern = Pattern.compile("^[a-zA-Z]+$"); // Regex for only alphabetic characters
        try {
            System.out.println("    1. Press 1 to change your username");
            System.out.println("    2. Press 2 to change your password");
            System.out.println("    3. Press 3 to go back to the main menu");
            int option = Integer.parseInt(scan.nextLine());
            if (option == 1) {
                System.out.println("    Your current Name is: " + this.getName());
                boolean validUsername = false;
                while (!validUsername) {
                    System.out.println("    Enter your new Name: ");
                    String newName = scan.nextLine();
                    Matcher matcher = usernamePattern.matcher(newName);
                    if (matcher.matches()) {
                        setName(newName);
                        this.updateUserCredentials(path, newName, this.password);
                        validUsername = true;
                        CSVActions.logAction(this.getName() + " changed their username");
                    } else {
                        System.out.println("Invalid username. Only alphabetic characters are allowed. Please try again.");
                    }
                }
            } else if (option == 2) {
                boolean passLoop = true;
                while (passLoop) {
                    System.out.println("    Please enter your current password:\n");
                    String currentPassUnverif = scan.nextLine();
                    String currentPass = getPassword();
                    if (currentPassUnverif.equals(currentPass)) {
                        passLoop = false;
                        System.out.println("    Please enter your new password:\n");
                        String newPassword = scan.nextLine();
                        setPassword(newPassword);
                        this.updateUserCredentials(path, this.name, newPassword);
                        CSVActions.logAction(this.getName() + " changed their password");
                    } else {
                        System.out.println(" The password you typed in is wrong, please try to carefully enter it again, or press 1 to go back to the main menu");
                        int returnToMenu = Integer.parseInt(scan.nextLine());
                        if (returnToMenu == 1) {
                            passLoop = false;
                        }
                    }
                }
            } else if (option == 3) {
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        } finally {

        }

    }
}

