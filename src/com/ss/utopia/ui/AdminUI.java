package com.ss.utopia.ui;

import com.ss.utopia.entity.*;
import com.ss.utopia.service.AdminService;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private static Scanner scan = new Scanner(new InputStreamReader(System.in));
    private static AdminService admin = new AdminService();

    public static void ui(){
        try {
            isAdmin();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void isAdmin() throws SQLException {

        System.out.print("Please Enter Your Username: ");
        String user = scan.nextLine();
        System.out.print("Please Enter Your Password: ");
        String pass= scan.nextLine();

        List<User> users = admin.getUserByUserPass(user,pass);
        if(users.size()==0 ){
            System.out.println("User not found");
        }
        else if(users.get(0).getRole().getId()!=2){
            System.out.println("User not Admin");
        }
        else{
            start();
        }

    }
    public static void start(){
        System.out.println("CRUD Operation on \n1) airplane\n2) airport\n3) route\n4) flight\n5) Quit");
        Integer choice = Integer.parseInt(scan.nextLine());

        switch(choice){
            case 1:
                airplane();
                break;
            case 2:
                airport();
                break;
            case 3:
                route();
                break;
            case 4:
                flight();
                break;
            case 5:
                break;

        }
    }


    public static void airplane(){
        String op = getCRUD();
        if(op.equals("C")){
            // create new airplane
            System.out.println("Enter type_id:");
            Integer id= Integer.parseInt(scan.nextLine());
            try {
                admin.createNewAirplane(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if(op.equals("R")){
            // read airplane by id
            System.out.println("Read by id? y/n");
            String in=scan.nextLine();
            if(in.equals("y")){
                System.out.println("Enter Airplane id: ");
                Integer id = Integer.parseInt(scan.nextLine());
                try {
                    Airplane plane =admin.getAirplaneById( id);
                    System.out.println("Airplane: ");
                    printPlane(plane);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else{
                try {
                    System.out.println("Airplanes: ");
                    List<Airplane> planes=admin.getAllAirplanes( );
                    printPlanes(planes);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else if(op.equals("U")){
            // update airplane
            System.out.println("Enter Airplane id:");
            Integer id = Integer.parseInt(scan.nextLine());
            System.out.println("Enter new Airplane Type id:");
            Integer tid = Integer.parseInt(scan.nextLine());
            try {
                admin.updateAirplane(id,tid);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else if(op.equals("D")){
            // Delete airplane
            System.out.println("Enter Airplane id:");
            Integer id = Integer.parseInt(scan.nextLine());
            try {
                admin.deleteAirplane(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            start();
        }
        start();
    }
    public static void route(){
        String op = getCRUD();
        if(op.equals("C")){
            // create new airplane
            System.out.println("Enter origin_id:");
            String og= scan.nextLine();
            System.out.println("Enter Destination_id:");
            String dest= scan.nextLine();

            try {
                admin.createRoute(og, dest);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if(op.equals("R")){
            // read airplane by id
            System.out.println("Read by id? y/n");
            String in=scan.nextLine();
            if(in.equals("y")){
                System.out.println("Enter Route id: ");
                Integer id = Integer.parseInt(scan.nextLine());
                Route route =admin.getRouteById( id);
                System.out.println("Route: ");
                printRoute(route);
            }
            else{
                try {
                    System.out.println("Routes: ");
                    List<Route> r=admin.getAllRoutes( );
                    printRoutes(r);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else if(op.equals("U")){
            // update route
            System.out.println("Enter Route id:");
            Integer id = Integer.parseInt(scan.nextLine());
            System.out.println("Enter new Origin Code:");
            String oid = scan.nextLine();
            System.out.println("Enter new Destination Code:");
            String did = scan.nextLine();
            try {
                admin.updateRoute(id,oid,did);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else if(op.equals("D")){
            // Delete route
            System.out.println("Enter route id:");
            Integer id = Integer.parseInt(scan.nextLine());
            try {
                admin.deleteRouteById(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            start();
        }
        start();
    }
    public static void flight(){
        String op = getCRUD();
        if(op.equals("C")){
            // create new flight
            System.out.println("Enter flight ID:");
            Integer id= Integer.parseInt(scan.nextLine());
            System.out.println("Enter Route ID:");
            Integer rid = Integer.parseInt(scan.nextLine());
            System.out.println("Enter Airplane ID:");
            Integer aid= Integer.parseInt(scan.nextLine());
            System.out.println("Enter new Departure Time (yyyy-mm-dd hh:mm:ss.SSS)");
            String input=scan.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = null;
            try {
                parsedDate = parsedDate = dateFormat.parse(input);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            System.out.println("Enter Reserved Seats:");
            Integer resSeats = Integer.parseInt(scan.nextLine());
            System.out.println("Enter Seat Price:");
            Float price = Float.parseFloat(scan.nextLine());

            try {
                admin.createFlight(id, rid,aid,timestamp,resSeats,price);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if(op.equals("R")){
            // read Flight by id
            System.out.println("Read by id? y/n");
            String in=scan.nextLine();
            if(in.equals("y")){
                System.out.println("Enter Flight id: ");
                Integer id = Integer.parseInt(scan.nextLine());
                Flight f =admin.getFlightById( id);
                System.out.println("Flight: ");
                printFlight(f);
            }
            else{
                System.out.println("Flights: ");
                List<Flight> flights=admin.getAllFlights( );
                printFlights(flights);
            }
        }
        else if(op.equals("U")){
            // update flight
            System.out.println("Enter flight ID:");
            Integer id= Integer.parseInt(scan.nextLine());
            System.out.println("Enter Route ID:");
            Integer rid = Integer.parseInt(scan.nextLine());
            System.out.println("Enter Airplane ID:");
            Integer aid= Integer.parseInt(scan.nextLine());
            System.out.println("Enter new Departure Time (yyyy-mm-dd hh:mm:ss.SSS)");
            String input=scan.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = null;
            try {
                parsedDate = parsedDate = dateFormat.parse(input);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            System.out.println("Enter Reserved Seats:");
            Integer resSeats = Integer.parseInt(scan.nextLine());
            System.out.println("Enter Seat Price:");
            Float price = Float.parseFloat(scan.nextLine());
            try {
                admin.updateFlight(id, rid,aid,timestamp,resSeats,price);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else if(op.equals("D")){
            // Delete airplane
            System.out.println("Enter Flight id:");
            Integer id = Integer.parseInt(scan.nextLine());
            try {
                admin.deleteFlight(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            start();
        }
        start();
    }

    private static void printFlight(Flight f) {
        System.out.println("\nID: "+f.getId()+" Route_ID: "+f.getRoute().getId()+" Airplane_ID: "+f.getPlane().getId()+
                " \nDeparture Time: "+f.getDepartureTime().toString()+" Reserved Seats: "+f.getReservedSeats()+" Seat_price: "+f.getSeatPrice());
    }

    public static void printPlane(Airplane p){
        System.out.println("ID: "+p.getId()+" Type: "+p.getType().getId());
    }
    public static void printAirport(Airport p){
        System.out.println("Code: "+p.getAirportCode()+" City: "+p.getCityName());
    }
    public static void printRoute(Route r){
        System.out.println("ID: "+r.getId()+" Origin Airport: "+r.getOriginAirport().getAirportCode()+" Destination Airport: "+r.getDestAirport().getAirportCode());
    }
    public static void printRoutes(List<Route> r){
        r.forEach(p -> printRoute(p));
    }
    public static void printFlights(List<Flight> f){
        f.forEach(p -> printFlight(p));
    }
    public static void printPlanes(List<Airplane> planes){
        planes.forEach(p -> printPlane(p));
    }
    public static void printAirports(List<Airport> ap){
        ap.forEach(p -> printAirport(p));
    }
    public static void airport(){
        String op= getCRUD();
        if(op.equals("C")){
            System.out.println("Enter Airport Code:");
            String code= scan.nextLine();
            System.out.println("Enter City:");
            String city = scan.nextLine();
            try {
                admin.createNewAirport(code,city);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else if(op.equals("R")){
            // read airport by code
            System.out.println("Read by code? y/n");
            String in=scan.nextLine();
            if(in.equals("y")){
                System.out.println("Enter Airport Code: ");
                String id = scan.nextLine();
                Airport airport =admin.getAirportByCode( id);
                System.out.println("Airport: ");
                printAirport(airport);

            }
            else{
                System.out.println("Airports: ");
                List<Airport> ap=admin.getAirports( );
                printAirports(ap);
            }
        }
        else if(op.equals("U")){
            // update airport
            System.out.println("Enter Airport code:");
            String id =scan.nextLine();
            System.out.println("Enter Airport city:");
            String city = scan.nextLine();
            System.out.println("1) Update By code\n2) Update by City:");
            Integer n = Integer.parseInt(scan.nextLine());
            if(n==1){
                try {
                    admin.updateAirport(id, city,1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else{
                try {
                    admin.updateAirport(id, city, 0);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        }
        else if(op.equals("D")){
            // Delete airport
            System.out.println("Enter Airport Code:");
            String id = scan.nextLine();
            try {
                admin.deleteAirport(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            start();
        }
        start();
    }
    public static String getCRUD(){
        System.out.println("Enter Operation \nC)\nR)\nU)\nD)\nQ) Quit/Back");
        String operation = scan.nextLine();
        return operation;
    }
}
