package org.example;

import Users.Participant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Items.Antique;
import Services.CSVActions;
import Services.Menu;
public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        Menu meniu = Menu.getInstance();
        meniu.InitUser(scan);
        meniu.MainMenu(scan);
        scan.close();

    }
}