package com.ss.utopia.ui;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class StartUI {
    public static void main(String [] args) throws SQLException {
        while(true) {
            Integer choice = selectOptionMain();
            switch (choice) {
                case 1:
                    EmployeeUI.ui();
                    break;
                case 2:
                    admin();
                    break;
                case 3:
                    TravelerUI.ui();
                    break;
            }
        }
    }


    public static void admin(){

    }

    public static void traveler(){

    }



    public static Integer selectOptionMain(){
        Scanner scan = new Scanner(new InputStreamReader(System.in));
        Integer choice = 0;
        while(true){
            printMainMenu();
            try{
                choice= Integer.parseInt(scan.next());
                while(choice <1 || choice > 3){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printMainMenu();
                    choice = Integer.parseInt(scan.next());
                }
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Input.\nPlease Enter Valid Input From The Menu Below:");
            }
        }
        return choice;
    }

    public static void printMainMenu(){
        System.out.println("Welcome to the Utopia Airlines Management System. Which category of a user are you:");
        System.out.println("1) Employee\n2) Administrator,\n3) Traveler");
    }
}
