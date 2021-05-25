package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.dao.*;
import com.ss.utopia.entity.*;

public class AdminService {

    ConnectionUtil connUtil = new ConnectionUtil();

    public List<Route> getAllRoutes() throws SQLException {
        Connection conn = null;
        List<Route> routes = new ArrayList<>();
        try {
            conn = connUtil.getConnection();
            RouteDAO rdao = new RouteDAO(conn);
            routes = rdao.readAllRoutes();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return routes;
    }

    public List<Airplane> getAllAirplanes() throws SQLException {
        Connection conn = null;
        List<Airplane> planes = new ArrayList<>();
        try {
            conn = connUtil.getConnection();
            AirplaneDAO rdao = new AirplaneDAO(conn);
            planes = rdao.readAllAirplanes();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return planes;
    }
    public Airplane getAirplaneById(Integer id) throws SQLException {
        Connection conn = null;
        List<Airplane> planes = new ArrayList<>();
        try {
            conn = connUtil.getConnection();
            AirplaneDAO rdao = new AirplaneDAO(conn);
            planes = rdao.readAllAirplanesById(id);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return planes.get(0);
    }
    public void addFlight() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AirportDAO adao = new AirportDAO(conn);
            //> aiportDao.save(IAD)
            //> aiportDao.save(CDG)
//			  > airplaneDAO.save()
            //		  > flightDAO.save()
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }

    }

    public void createNewAirplane(Integer id) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AirplaneTypeDAO adao = new AirplaneTypeDAO(conn);
            AirplaneDAO apdao = new AirplaneDAO(conn);
            AirplaneType type= adao.readAllAirplaneTypesById(id).get(0);

            Airplane plane = new Airplane();
            plane.setType(type);

            apdao.addAirplane(plane);

            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }
    public void updateAirplane(Integer id, Integer  tid) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AirplaneTypeDAO adao = new AirplaneTypeDAO(conn);
            AirplaneDAO apdao = new AirplaneDAO(conn);
            AirplaneType type= adao.readAllAirplaneTypesById(tid).get(0);
            Airplane plane = new Airplane();
            plane.setId(id);
            plane.setType(type);
            apdao.updateAirplane(plane);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
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
    public void deleteAirplane(Integer id) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AirplaneDAO apdao = new AirplaneDAO(conn);
            Airplane plane = apdao.readAllAirplanesById(id).get(0);
            apdao.deleteAirplane(plane);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }


    public void createNewAirport(String code, String city) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AirportDAO apdao = new AirportDAO(conn);

            Airport p = new Airport();
            p.setCityName(city);
            p.setAirportCode(code);

            apdao.addAirport(p);

            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    public Airport getAirportByCode(String id) {
        Connection conn = null;
        List<Airport> ap = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            AirportDAO adao = new AirportDAO(conn);
            ap= adao.readAirportByCode(id);

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
        return ap.get(0);
    }

    public List<Airport> getAirports() {
        Connection conn = null;
        List<Airport> ap = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            AirportDAO adao = new AirportDAO(conn);
            ap= adao.readAllAirports();

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

    public void updateAirport(String id, String city, Integer code) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AirportDAO adao = new AirportDAO(conn);
            Airport p = new Airport();
            p.setAirportCode(id);
            p.setCityName(city);
            if(code==0){
                adao.updateAirportByCity(p);
            }
            else{
                adao.updateAirportByIata(p);
            }
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    public void deleteAirport(String id) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AirportDAO apdao = new AirportDAO(conn);
            Airport ap = apdao.readAirportByCode(id).get(0);
            apdao.deleteAirport(ap);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    public void createRoute(String og, String dest) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            RouteDAO apdao = new RouteDAO(conn);
            AirportDAO adao = new AirportDAO(conn);
            Airport o = adao.readAirportByCode(og).get(0);
            Airport d = adao.readAirportByCode(dest).get(0);
            Route r= new Route();
            r.setOriginAirport(o);
            r.setDestAirport(d);
            apdao.addRoute(r);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    public Route getRouteById(Integer id) {
        Connection conn = null;
        List<Route> r = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            RouteDAO rdao = new RouteDAO(conn);
            r= rdao.readAirportById(id);

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
        return r.get(0);
    }

    public void updateRoute(Integer id, String oid, String did) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            RouteDAO rdao = new RouteDAO(conn);
            AirportDAO apdao= new AirportDAO(conn);
            Airport o = apdao.readAirportByCode(oid).get(0);
            Airport d= apdao.readAirportByCode(did).get(0);
            Route r= new Route();
            r.setId(id);
            r.setDestAirport(d);
            r.setOriginAirport(o);

            rdao.updateRoute(r);

            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    public void deleteRouteById(Integer id) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            RouteDAO rdao = new RouteDAO(conn);
            Route r = rdao.readRouteById(id).get(0);
            rdao.deleteRoute(r);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    public void createFlight(Integer id, Integer rid, Integer aid, Timestamp t, Integer resSeats, Float price) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            RouteDAO rdao = new RouteDAO(conn);
            FlightDAO fdao = new FlightDAO(conn);
            AirplaneDAO adao = new AirplaneDAO(conn);
            Route r = rdao.readRouteById(rid).get(0);
            Airplane p = adao.readAllAirplanesById(aid).get(0);
            Flight f = new Flight();
            f.setId(id);
            f.setRoute(r);
            f.setPlane(p);
            f.setDepartureTime(t);
            f.setReservedSeats(resSeats);
            f.setSeatPrice(price);

            fdao.addFlight(f);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    public Flight getFlightById(Integer id) {Connection conn = null;
        List<Flight> f = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            FlightDAO adao = new FlightDAO(conn);
            f= adao.readFlightsById(id);

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
        return f.get(0);
    }
    public List<Flight> getAllFlights() {Connection conn = null;
        List<Flight> f = new ArrayList<>();
        try{
            conn = connUtil.getConnection();
            FlightDAO adao = new FlightDAO(conn);
            f= adao.readAllFlights();

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
        return f;
    }

    public void updateFlight(Integer id, Integer rid, Integer aid, Timestamp t, Integer resSeats, Float price) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            RouteDAO rdao = new RouteDAO(conn);
            FlightDAO fdao = new FlightDAO(conn);
            AirplaneDAO adao = new AirplaneDAO(conn);
            Route r = rdao.readRouteById(rid).get(0);
            Airplane p = adao.readAllAirplanesById(aid).get(0);
            Flight f = new Flight();
            f.setId(id);
            f.setRoute(r);
            f.setPlane(p);
            f.setDepartureTime(t);
            f.setReservedSeats(resSeats);
            f.setSeatPrice(price);

            fdao.updateFlight(f);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    public void deleteFlight(Integer id) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            FlightDAO fdao = new FlightDAO(conn);
            Flight find = fdao.readFlightsById(id).get(0);
            fdao.deleteFlight(find);
            conn.commit(); //this makes the change permanent.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }

    }
}