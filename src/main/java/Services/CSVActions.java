package Services;

import Exceptions.CSVException;
import Items.Variety;
import Users.Participant;
import Users.User;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.util.concurrent.ExecutionException;


public interface CSVActions {
    void writeInCSV(String path) throws CSVException;

//    public static <T> HashMap<Integer, String> getHashMap(String path, boolean bool) throws Exception {
//        return null;
//    }
//        HashMap<Integer,String > hashMap = new HashMap<Integer,String >();
//        try(BufferedReader br = new BufferedReader(new FileReader(path))){
//            String line;
//            while ((line = br.readLine()) != null){
//                String[] values = line.split(",");
//                if(values.length >= 4){
//                    int id = Integer.parseInt(values[0].trim());
//                    String auctionType = values[1].trim();
//
//                    hashMap.put(id, auctionType);
//                }
//            }
//        }
//        return hashMap;
//    }

    //HashMap<Integer,String > auctionsList = new HashMap<Integer,String >();

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