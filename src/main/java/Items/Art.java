package Items;

import Exceptions.CSVException;
import Services.CSVActions;

import javax.swing.text.Style;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class Art extends Variety {

    private String artistName;

    enum ArtStyle {
        Classicism,
        Baroque,
        Impressionism,
        Minimalism,
        Symbolism,
        Digital_Art
    }

    ;
    private String style;

    public static String path = "src/Items.csv";

    private int height;
    private int width;

    public Art(int id, String name, String description, int manYear, int desiredPrice, int sellerId, int quantity, int idAuction, String artistName, String style, int height, int width) {
        super(id, name, description, manYear, desiredPrice, sellerId, quantity, idAuction);
        this.artistName = artistName;
        this.style = style;
        this.height = height;
        this.width = width;
    }



    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Art art = (Art) o;
        return Objects.equals(artistName, art.artistName) && style == art.style;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), artistName, style);
    }
    @Override
    public void writeInCSV(String path) throws CSVException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            writer.println(artToCSV());
        } catch (IOException e) {
            throw new CSVException("Error at openning CSV file" + path);
        }
    }



    @Override
    public void printItem(){
        System.out.print("The art piece we are looking at today is a ");
        System.out.print(name);
        System.out.print(", made by the great ");
        System.out.print(artistName);
        System.out.print(" in the year of ");
        System.out.print(manYear);
        System.out.print(".");
        System.out.print("\nThis wonder of ");
        System.out.print(style);
        System.out.print(" art is a ");
        System.out.print(description);
        System.out.print(".");
        System.out.print("Also, the height and and the width of the piece are ");
        System.out.print(height);
        System.out.print(", respectively ");
        System.out.print(width);
        System.out.print(".");
        System.out.print("\nWe currently have ");
        System.out.print(quantity);
        System.out.print(" of the these bad boys listed in our action and the bidding price starts from ");
        System.out.print(desiredPrice);
        System.out.print(".");
        System.out.println();
    }


    @Override
    public String toString() {
        return "Art{" +
                "artistName='" + artistName + '\'' +
                ", style='" + style + '\'' +
                ", height=" + height +
                ", width=" + width +
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

    private String artToCSV() {
        return varietyToCSV() + "," +
                this.getArtistName() + "," +
                this.getStyle() + "," +
                this.getHeight() + "," +
                this.getWidth();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

//    public static Art CreateObject(int idAuction, int sellerId, Scanner scan) throws Exception {
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
//        System.out.println("Enter artist's name: ");
//        String artistName = scan.nextLine();
//        System.out.println("Please choose from the following styles the one that represents your art piece:  ");
//        System.out.println(ArtStyle.Classicism.toString());
//        System.out.println(ArtStyle.Baroque.toString());
//        System.out.println(ArtStyle.Impressionism.toString());
//        System.out.println(ArtStyle.Minimalism.toString());
//        System.out.println(ArtStyle.Symbolism.toString());
//        System.out.println(ArtStyle.Digital_Art.toString());
//
//        String style = scan.nextLine();
//        System.out.println("Enter the piece's height: ");
//        int height = Integer.parseInt(scan.nextLine());
//        System.out.println("Enter the piece's width: ");
//        int width = Integer.parseInt(scan.nextLine());
//
//        int nextId = CSVActions.checkNextId(Variety.path);
//
//        return new Art(nextId, name, description, manYear, desiredPrice, sellerId, quantity, idAuction, artistName, style, height, width);
//
//    }


    public static Art CreateObject(int idAuction, int sellerId, Scanner scan) throws Exception {
        System.out.println("Enter the item's name: ");
        String name = scan.nextLine();
        System.out.println("Enter the description: ");
        String description = scan.nextLine();

        int manYear = getValidIntegerInput(scan, "Enter the manufacture year: ");
        int desiredPrice = getValidIntegerInput(scan, "Enter the desired price: ");
        int quantity = getValidIntegerInput(scan, "Enter the quantity: ");


        System.out.println("Enter artist's name: ");
        String artistName = scan.nextLine();
        System.out.println("Please choose from the following styles the one that represents your art piece:  ");
        System.out.println(ArtStyle.Classicism.toString());
        System.out.println(ArtStyle.Baroque.toString());
        System.out.println(ArtStyle.Impressionism.toString());
        System.out.println(ArtStyle.Minimalism.toString());
        System.out.println(ArtStyle.Symbolism.toString());
        System.out.println(ArtStyle.Digital_Art.toString());
        String style = scan.nextLine();


        int height = getValidIntegerInput(scan, "Enter the piece's height ");
        int width = getValidIntegerInput(scan, "Enter the piece's width: ");

        System.out.println("Enter anything to continue");
        scan.nextLine();

        int nextId = CSVActions.checkNextId(Variety.path);
        return  new Art(nextId, name, description, manYear, desiredPrice, sellerId, quantity, idAuction, artistName, style, height, width);
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


