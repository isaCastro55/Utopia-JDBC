package com.ss.utopia.service;

import com.ss.utopia.dao.*;
import com.ss.utopia.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravelerService {
    ConnectionUtil connUtil = new ConnectionUtil();

    public void bookTicket(Flight f, Booking b) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookingDAO bdao = new BookingDAO(conn);
            FlightBookingDAO fbdao = new FlightBookingDAO(conn);
            bdao.addBooking(b);
            FlightBooking fb = new FlightBooking();
            fb.setFlightId(f);
            fb.setBookingId(bdao.readBookingByConfirm(b.getConfirmCode()).get(0));
            fbdao.addFlightBooking(fb);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }

    }
    public void cancelBooking(Flight f) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookingDAO bdao = new BookingDAO(conn);
            FlightBookingDAO fbdao = new FlightBookingDAO(conn);
            FlightBooking fb = fbdao.readFlightBookingByFlight_id(f.getId()).get(0);
            bdao.deleteBooking(fb.getBookingId());
            fbdao.deleteFlightBooking(fb);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }

    }
    public List<User> getTravByUserPass(String u, String p){
        Connection conn = null;
        List<User> users = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            UserDAO adao = new UserDAO(conn);
            users= adao.readUserByUserPass(u,p);

        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return users;

    }

    public List<Flight> getAllFlights() {
        Connection conn = null;
        List<Flight> flights = new ArrayList<>();
        try {
            conn = connUtil.getConnection();
            FlightDAO fdao = new FlightDAO(conn);
            RouteDAO rdao = new RouteDAO(conn);
            AirportDAO adao= new AirportDAO(conn);
            AirplaneDAO apdao = new AirplaneDAO(conn);
            AirplaneTypeDAO atdao = new AirplaneTypeDAO(conn);

            flights = fdao.readAllFlights();
            List<Route> routes = new ArrayList<Route>();
            flights.forEach((i)->{
                try {
                    routes.add(rdao.readRouteById(i.getRoute().getId()).get(0));
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            });
            List<Airport> ogCities = new ArrayList<>();
            List<Airport> destCities = new ArrayList<>();
            routes.forEach((i)->{
                try {
                    ogCities.add(adao.readAirportByCode(i.getOriginAirport().getAirportCode()).get(0));
                    destCities.add(adao.readAirportByCode(i.getDestAirport().getAirportCode()).get(0));
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            });
            List<Airplane> planes = new ArrayList<>();
            flights.forEach((i)->{
                try {

                    planes.add(apdao.readAllAirplanesById(i.getPlane().getId()).get(0));
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            });
            List<AirplaneType> maxCap = new ArrayList<>();

            planes.forEach((i)->{
                try {

                    maxCap.add(atdao.readAllAirplaneTypesById(i.getType().getId()).get(0));
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            });
            for(int i=0;i<flights.size();i++){
                Route r = flights.get(i).getRoute();
                r.setDestAirport(destCities.get(i));
                r.setOriginAirport(ogCities.get(i));
                flights.get(i).setRoute(r);
                Airplane a = flights.get(i).getPlane();
                a.setType(maxCap.get(i));
                flights.get(i).setPlane(a);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return flights;
    }






}
