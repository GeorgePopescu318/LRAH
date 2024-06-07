package Users;
import Bids.Bid;
import Exceptions.CSVException;
import Services.CSVActions;
import jdk.jshell.spi.ExecutionControlProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Participant extends User implements CSVActions{
    private List<Bid> currentBids;

    public Participant(int id, String name, String password) {
        super(id, name, password);
        this.currentBids = null;
    }

    public List<Bid> getCurrentBids() {
        return currentBids;
    }

    @Override
    public void changeAuction(Scanner scan, int auctionId, boolean changedType) throws IOException{}

    @Override
    public void deleteAuction(Scanner scan, int auctionId) throws Exception{}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Participant that = (Participant) o;
        return Objects.equals(currentBids, that.currentBids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), currentBids);
    }


    @Override
    public void writeInCSV(String path) throws CSVException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))){
                writer.println(participantToCSV());

        }catch(IOException e){
            throw new CSVException("Error at openning CSV file" + path);
        }
    }

    private String participantToCSV(){
        return this.id + "," +
                this.name + "," +
                this.password + "," +
                this.isAdmin;
    }


    public static HashMap<Integer, String> getHashMap(String path, Participant object) throws Exception {
        return new HashMap<>();
    }

}
