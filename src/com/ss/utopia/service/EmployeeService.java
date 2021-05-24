package com.ss.utopia.service;

import com.ss.utopia.dao.*;
import com.ss.utopia.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    ConnectionUtil connUtil = new ConnectionUtil();




    public List<AirplaneType> getAirplaneTypes( ){
        Connection conn = null;
        List<AirplaneType> ap = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            AirplaneTypeDAO apdao = new AirplaneTypeDAO(conn);
            ap= apdao.readAllAirplaneTypes();

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
        return ap;
    }

    public void update(Integer id,Airport og, Airport dest, AirplaneType type, Timestamp depTime, Integer resSeats, Float price) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AirportDAO portDao = new AirportDAO(conn);
            AirplaneDAO apdao = new AirplaneDAO(conn);
            RouteDAO rdao = new RouteDAO(conn);
            FlightDAO fdao = new FlightDAO(conn);
            Route r= new Route();

            Airport o=portDao.readAirportByCode(og.getAirportCode()).get(0);
            Airport d=portDao.readAirportByCode(dest.getAirportCode()).get(0);
            r.setDestAirport(d);
            r.setOriginAirport(o);

            rdao.addRoute(r);
            r=rdao.readAllRoutesByAirportCode(o.getAirportCode(),d.getAirportCode()).get(0);

            Airplane plane = new Airplane();
            plane.setType(type);
            plane=apdao.readAllAirplanesByType(plane.getType().getId()).get(0);

            Flight f= new Flight();
            f.setId(id);
            f.setRoute(r);
            f.setPlane(plane);
            f.setDepartureTime(depTime);
            f.setReservedSeats(resSeats);
            f.setSeatPrice(price);

            fdao.updateFlight(f);
            conn.commit(); //this makes the change permanent.
            System.out.println("Updated Successfully");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }

    }



    public List<Airport> getAllAirports(){
        Connection conn = null;
        List<Airport> airports = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            AirportDAO adao = new AirportDAO(conn);
            airports= adao.readAllAirports();

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
        return airports;
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



    public List<User> getUserByUserPass(String user, String pass) {
        Connection conn = null;
        List<User> users = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            UserDAO adao = new UserDAO(conn);
            users= adao.readUserByUserPass(user,pass);

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
}
