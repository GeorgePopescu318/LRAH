package Users;

import Auctions.Auction;
import Bids.Bid;
import Exceptions.CSVException;
import Items.Variety;
import Services.CSVActions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Admin extends User implements CSVActions {
    public Admin(int id, String name, String password, boolean isAdmin) {
        super(id, name, password,isAdmin);
    }


    @Override
    public void writeInCSV(String path) throws CSVException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))){
            writer.println(adminToCSV());

        }catch(IOException e){
            throw new CSVException("Error at openning CSV file" + path);
        }
    }

    @Override
    public void changeAuction(Scanner scan, int auctionId, boolean changedType) throws IOException, CSVException {
        System.out.println("Do you want to change auction"+auctionId+" to "+changedType+"?\n1. Yes\n 2. No");
        int option = Integer.parseInt(scan.nextLine());
        if (option == 1){
            Auction.updateAuctionStatus(Auction.path,auctionId,changedType);
            CSVActions.logAction(this.getName() + " changed auction status to " + changedType);
        }
    }
    @Override
    public void deleteAuction(Scanner scan, int auctionId) throws Exception {
        System.out.println("Do you want to delete auction "+auctionId+ "?\n1. Yes\n 2. No");
        int option = Integer.parseInt(scan.nextLine());
        if (option == 1){
            Variety.modifyItemForAuction(Variety.path, Bid.path,auctionId);
            Auction.deleteAuction(Auction.path,auctionId);
            CSVActions.logAction(this.getName() + "deleted auction with id :" + auctionId);
        }
    }

    @Override
    public Auction CreateAuction(Scanner scan) throws Exception {
        System.out.println("Enter date (ex: YYYY-MM-DD)");
        LocalDate data = LocalDate.parse(scan.nextLine());
        System.out.println("Enter type from Variety,Car,Art,Antique");
        String type = scan.nextLine();
        Auction newAuction = new Auction(CSVActions.checkNextId(Auction.path),data,type);
        CSVActions.logAction(this.getName() + "created auction with id :" + CSVActions.checkNextId(Auction.path));
        newAuction.writeInCSV(Auction.path);
        return newAuction;

    }


    private String adminToCSV(){
        return this.id + "," +
                this.name + "," +
                this.password;
    }

}
