package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.RouteDAO;
import com.ss.utopia.entity.Route;

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
}