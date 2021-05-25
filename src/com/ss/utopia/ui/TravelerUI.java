package com.ss.utopia.ui;

import com.ss.utopia.entity.*;
import com.ss.utopia.service.EmployeeService;
import com.ss.utopia.service.TravelerService;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class TravelerUI {
    private static Scanner scan = new Scanner( new InputStreamReader(System.in));
    private static TravelerService trav = new TravelerService();


    public static void ui() throws SQLException {
        isTraveler();
    }
    public static void isTraveler() throws SQLException {

        System.out.print("Please Enter Your Username: ");
        String user = scan.nextLine();
        System.out.print("Please Enter Your Password: ");
        String pass= scan.nextLine();

        List<User> users = trav.getTravByUserPass(user,pass);
        if(users.size()==0 ){
            System.out.println("User not found");
        }
        else if(users.get(0).getRole().getId()!=3){
            System.out.println("User not Traveler");
        }
        else{
            TRAV1();
        }

    }

    public static void TRAV1() throws SQLException {
        Integer choice=selectOptionTRAV1();
        switch(choice){
            case 1:
                option1(trav.getAllFlights());
                break;
            case 2:
                option2(trav.getAllFlights());
                break;
            case 3:
                break;
        }
    }
    // prints all flights
    public static void option1(List<Flight> flights) throws SQLException {
        Integer c = selectOption1(flights);
        if(c!=flights.size()+1){
            option12(flights, c);
        }
        else{
            TRAV1();
        }
    }

    //View flight details for booking ticket or Quit
    public static void option12(List<Flight> flights, Integer pos) throws SQLException {
        Integer choice = selectOption12();
        Flight flight = flights.get(pos-1);
        if(choice == 1){
            option121(flights, pos,flight);

        }
        else{
            TRAV1();
        }

    }

    // Displays details and books ticket then quits
    public static void option121(List<Flight> flights, Integer pos,Flight flight) throws SQLException {
        Integer choice =selectOption121(flight);

        if(choice == 1){
            Booking book = new Booking();
            book.setIsActive((short) 1);
            String conf= genCode();
            book.setConfirmCode(conf);
            trav.bookTicket(flight, book);
            TRAV1();
        }

    }

    // Display flight for canceling tickets
    public static void option2(List<Flight> flights) throws SQLException {
        Integer choice = selectOption2(flights);
        if(choice!=flights.size()+1){
            option21(flights, choice);
        }
        else{
            TRAV1();
        }
    }

    // Confirm Cancellation and return to prev menu
    public static void option21(List<Flight> flights, Integer c) throws SQLException {
        Flight chosen= flights.get(c-1);
        Integer choice =selectOption21(chosen);
        if(choice ==1){
            trav.cancelBooking(chosen);
            TRAV1();
        }


    }


    public static Integer selectOption21(Flight flights){
        Integer y = 0;
        while(true){
            printOption21(flights);
            try{
                y= Integer.parseInt(scan.next());
                while(y !=1){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printOption21(flights);
                    y = Integer.parseInt(scan.next());
                }
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Input.\nPlease Enter Valid Input From The Menu Below:");
            }
        }
        return y;
    }
    public static void printOption21(Flight x){
        String ogAirport =x.getRoute().getOriginAirport().getAirportCode()+", "+x.getRoute().getOriginAirport().getCityName();
        String destAirport = x.getRoute().getDestAirport().getAirportCode()+", "+x.getRoute().getDestAirport().getCityName();
        Date date = new Date(x.getDepartureTime().getTime());
        System.out.println("You have chosen to cancel the Flight with Flight Id: "+x.getId()+
                " and Departure Airport: "+ ogAirport
                +" and Arrival Airport: "+destAirport+".");
        System.out.println("Departure Airport: "+ogAirport+" | Arrival Airport: "
                +destAirport+" | Departure Date: "+date
                +" Departure Time: "+x.getDepartureTime().getTime());

        System.out.println("Available Seats: "+(x.getPlane().getType().getMaxCapacity()-x.getReservedSeats()));
        System.out.println("1) Confirm and Return To Previous Menu");

    }
    public static Integer selectOption2(List<Flight> flights){
        Integer y = 0;
        while(true){
            System.out.println("Pick the Flight you want to cancel a ticket for: ");
            printOption1(flights);
            try{
                y= Integer.parseInt(scan.next());
                while(y <1 || y>flights.size()+1){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    System.out.println("Pick the Flight you want to cancel a ticket for: ");
                    printOption1(flights);
                    y = Integer.parseInt(scan.next());
                }
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Input.\nPlease Enter Valid Input From The Menu Below:");
            }
        }
        return y;
    }


    public static String genCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }


    public static void printOption121(Flight x){
        String ogAirport =x.getRoute().getOriginAirport().getAirportCode()+", "+x.getRoute().getOriginAirport().getCityName();
        String destAirport = x.getRoute().getDestAirport().getAirportCode()+", "+x.getRoute().getDestAirport().getCityName();
        Date date = new Date(x.getDepartureTime().getTime());
        System.out.println("You have chosen to view the Flight with Flight Id: "+x.getId()+
                " and Departure Airport: "+ ogAirport
                +" and Arrival Airport: "+destAirport+".");
        System.out.println("Departure Airport: "+ogAirport+" | Arrival Airport: "
                +destAirport+" | Departure Date: "+date
                +" Departure Time: "+x.getDepartureTime().getTime());

        System.out.println("Available Seats: "+(x.getPlane().getType().getMaxCapacity()-x.getReservedSeats()));
        System.out.println("1) Confirm and Return To Previous Menu");
    }

    public static Integer selectOption121(Flight flight){
        Integer x = 0;
        while(true){
            printOption121(flight);
            try{
                x= Integer.parseInt(scan.next());
                while(x !=1){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printOption121(flight);
                    x = Integer.parseInt(scan.next());
                }
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Input.\nPlease Enter Valid Input From The Menu Below:");
            }
        }
        return x;
    }

    public static void printOption12(){
        System.out.println("1)View Flight Details and Confirm\n2)Quit to cancel operation");
    }
    public static Integer selectOption1(List<Flight> flights){
        Integer y = 0;
        while(true){
            System.out.println("Pick the Flight you want to book a ticket for: ");
            printOption1(flights);
            try{
                y= Integer.parseInt(scan.next());
                while(y <1 || y>flights.size()+1){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    System.out.println("Pick the Flight you want to book a ticket for: ");
                    printOption1(flights);
                    y = Integer.parseInt(scan.next());
                }
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Input.\nPlease Enter Valid Input From The Menu Below:");
            }
        }
        return y;
    }
    public static Integer selectOption12(){
        Integer y = 0;
        while(true){
            printOption12();
            try{
                y= Integer.parseInt(scan.next());
                while(y <1 || y>2){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printOption12();
                    y = Integer.parseInt(scan.next());
                }
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Input.\nPlease Enter Valid Input From The Menu Below:");
            }
        }
        return y;
    }
    public static void printOption1(List<Flight> flights){

        AtomicReference<Integer> count= new AtomicReference<>(1);
        flights.forEach((i)->{
            Route r = i.getRoute();
            Airport og = r.getOriginAirport();
            Airport dest = r.getDestAirport();
            System.out.println(count+") "+og.getAirportCode()+", "+og.getCityName()+" -> "+dest.getAirportCode()+", "+dest.getCityName());
            count.getAndSet(count.get() + 1);
        });
        System.out.println(count+") Quit to previous");

    }
    public static void printTRAV1(){
        System.out.println("1)Book a Ticket\n2)Cancel an Upcoming Trip\n3)Quit to Previous");
    }
    public static Integer selectOptionTRAV1(){
        Integer y = 0;
        while(true){
            printTRAV1();
            try{
                y= Integer.parseInt(scan.next());
                while(y <1 || y>3){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printTRAV1();
                    y = Integer.parseInt(scan.next());
                }
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Input.\nPlease Enter Valid Input From The Menu Below:");
            }
        }
        return y;
    }
}
