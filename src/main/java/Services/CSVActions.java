package Services;

import Exceptions.CSVException;
import Items.Variety;
import Users.Participant;
import Users.User;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public interface CSVActions {
    void writeInCSV(String path) throws CSVException;


    static void logAction(String action) {
        try {
            FileWriter fw = new FileWriter("src/logs.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime timeStamp = LocalDateTime.now();
            bw.write(action + " " + dateFormat.format(timeStamp) + "\n");
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while logging the action.");
            e.printStackTrace();
        }
    }

     static int checkNextId(String pathFile) throws Exception{
        Scanner sc = new Scanner(new File(pathFile));
        sc.useDelimiter("\n");
        int maxId = -1;

        while(sc.hasNext()){
            String line = sc.next();
            String[] columns = line.split(",");
            if (columns.length > 0){
//                System.out.println(columns[0]);
                int id = Integer.parseInt(columns[0]);

                maxId = Math.max(maxId, id);
            }
        }
        sc.close();

        return maxId + 1;
    }
}