package com.ss.utopia.ui;

import com.ss.utopia.entity.*;
import com.ss.utopia.service.EmployeeService;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class EmployeeUI {
    private static Scanner scan = new Scanner (new InputStreamReader(System.in));
    private static EmployeeService emp = new EmployeeService();

    public static void ui() {
        isEmployee();
    }
    public static void isEmployee(){

        System.out.print("Please Enter Your Username: ");
        String user = scan.nextLine();
        System.out.print("Please Enter Your Password: ");
        String pass= scan.nextLine();

        List<User> users = emp.getUserByUserPass(user,pass);
        if(users.size()==0 ){
            System.out.println("User not found");
        }
        else if(users.get(0).getRole().getId()!=1){
            System.out.println("User not Employee");
        }
        else{
            EMP1();
        }

    }

    public static void EMP1() {
        Integer choice = selectOptionEMP1();
        System.out.println(choice);

        if (choice != 2 ) {
            List<Flight> flights = emp.getAllFlights();
            EMP2(flights);
        }

        return;
    }
    public static void EMP2(List<Flight> flights){
        Integer c = selectOptionEMP2(flights);
        if(c!=flights.size()+1){
            EMP3(flights, c);
        }
        else{
            EMP1();
        }


    }
    public static void EMP3(List<Flight> flights, Integer pos){
        Integer choice =selectOptionEMP3();
        Flight flight = flights.get(pos-1);
        switch(choice){
            case 1:
                option1(flights, pos, flight);
                break;
            case 2:
                option2(flights, pos, flight);
                break;
            case 3:
                EMP2(flights);
                break;
        }


    }
    public static void option1(List<Flight> f,Integer pos,Flight flight){
        Integer choice = selectOptionOption1(flight);
        if(choice==1){
            EMP3(f,pos);
        }
    }

    public static boolean quit(String x){
        if(x.equals("quit")){
            return true;
        }
        return false;
    }
    public static void option2(List<Flight> f,Integer pos,Flight x){
        Scanner sc = new Scanner(new InputStreamReader(System.in));
        printOption2(x);
        String input = "";
        Flight newFlight= new Flight();
        newFlight.setId(x.getId());
        System.out.println("Enter a new Departure Airport Code from the ones listed Below or N/A for no change: ");
        printAirports();
        input= sc.nextLine();
        while(!input.equals("quit")){
            Airport ap = new Airport();
            if(!input.equals("N/A")){
                ap.setAirportCode(input);
            }
            else{
                ap = x.getRoute().getOriginAirport();
            }

            System.out.println("Enter a new Arrival Airport Code from the ones listed below or N/A for no change:");
            printAirports();
            input= sc.nextLine();
            if(quit(input)){
                break;
            }
            Airport app = new Airport();
            if(!input.equals("N/A")){
                app.setAirportCode(input);
            }
            else{
                app = x.getRoute().getDestAirport();
            }

            System.out.println("Enter AirplaneType_id from the ones listed below or N/A for no change:");
            printATypes();
            input=sc.nextLine();
            if(quit(input)){
                break;
            }
            AirplaneType planeT=new AirplaneType();
            if(!input.equals("N/A")){
                planeT.setId(Integer.parseInt(input));
            }
            else{
                planeT.setId(x.getPlane().getId());
            }

            System.out.println("Enter new Departure Time (yyyy-mm-dd hh:mm:ss.SSS) or N/A for no change");
            input=sc.nextLine();
            if(quit(input)){
                break;
            }
            Timestamp timestamp;
            if(!input.equals("N/A")){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                Date parsedDate = null;
                try {
                    parsedDate = dateFormat.parse(input);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                 timestamp = new java.sql.Timestamp(parsedDate.getTime());
            }
            else{
                 timestamp = x.getDepartureTime();
            }

            System.out.println("Enter new number of reserved seats or N/A for no change");
            input = sc.nextLine();
            Integer seats=0;
            if(quit(input)){
                break;
            }
            if(!input.equals("N/A")){
                seats=Integer.parseInt(input);
            }
            else{
                seats=x.getReservedSeats();
            }

            System.out.println("Enter new Seat Price or N/A for no change");
            Float price;
            input = sc.nextLine();
            if(quit(input)){
                break;
            }
            if(!input.equals("N/A")){
                price=Float.parseFloat(input);
            }
            else{
                price=x.getSeatPrice();
            }
            try {
                emp.update(x.getId(), ap, app, planeT, timestamp,seats, price);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            input ="quit";
        }
        EMP3(f, pos);


    }
    public static void printATypes(){
        List<AirplaneType> airp= emp.getAirplaneTypes();
        airp.forEach((i)->{
            System.out.println("ID: "+i.getId());
        });
    }
    public static void printAirports(){
        List<Airport> airp= emp.getAllAirports();
        airp.forEach((i)->{
            System.out.println(i.getAirportCode());
        });
    }
    public static void printOption2(Flight x){
        String ogAirport =x.getRoute().getOriginAirport().getAirportCode()+", "+x.getRoute().getOriginAirport().getCityName();
        String destAirport = x.getRoute().getDestAirport().getAirportCode()+", "+x.getRoute().getDestAirport().getCityName();
        Date date = new Date(x.getDepartureTime().getTime());
        System.out.println("You have chosen to view the Flight with Flight Id: "+x.getId()+
                " and Departure Airport: "+ ogAirport
                +" and Arrival Airport: "+destAirport+".");
        System.out.println("Enter 'quit' at any prompt to cancel operation.");
    }

    public static void printOption1(Flight x){
        String ogAirport =x.getRoute().getOriginAirport().getAirportCode()+", "+x.getRoute().getOriginAirport().getCityName();
        String destAirport = x.getRoute().getDestAirport().getAirportCode()+", "+x.getRoute().getDestAirport().getCityName();
        Date date = new Date(x.getDepartureTime().getTime());
        System.out.println("You have chosen to view the Flight with Flight Id: "+x.getId()+
                " and Departure Airport: "+ ogAirport
                +" and Arrival Airport: "+destAirport+".");
        System.out.println("Departure Airport: "+ogAirport+" | Arrival Airport: "
                +destAirport+" | Departure Date: "+date
                +" Departure Time: "+x.getDepartureTime().toString());

        System.out.println("Available Seats: "+(x.getPlane().getType().getMaxCapacity()-x.getReservedSeats()));
        System.out.println("1) Return To Previous Menu");
    }

    public static void printEMP1() {
        System.out.println("1) Enter Flights You Manage\n2) Quit To Previous");
    }
    public static void printEMP3() {
        System.out.println("1) View more details about the flight\n2) Update the details of the flight\n3) Quit to Previous");
    }

    public static void printEMP2(List<Flight> flights) {
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


    public static Integer selectOptionEMP1(){

        Integer x = 0;
        while(true){
            printEMP1();
            try{
                x= Integer.parseInt(scan.nextLine());
                while(x <1 || x > 2){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printEMP1();
                    x = Integer.parseInt(scan.nextLine());
                }
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Input.\nPlease Enter Valid Input From The Menu Below:");
            }
        }
        return x;
    }
    public static Integer selectOptionEMP2(List<Flight> flights){
        Integer y = 0;
        while(true){
            printEMP2(flights);
            try{
                y= Integer.parseInt(scan.next());
                while(y <1 || y>flights.size()+1){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printEMP2(flights);
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
    public static Integer selectOptionEMP3(){
        Integer y = 0;
        while(true){
            printEMP3();
            try{
                y= Integer.parseInt(scan.next());
                while(y <1 || y>4){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printEMP3();
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
    public static Integer selectOptionOption1(Flight flight){
        Integer x = 0;
        while(true){
            printOption1(flight);
            try{
                x= Integer.parseInt(scan.next());
                while(x !=1){
                    System.out.println("Invalid Choice.\nPlease Enter Valid Choice From Menu Below:");
                    printOption1(flight);
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

}
