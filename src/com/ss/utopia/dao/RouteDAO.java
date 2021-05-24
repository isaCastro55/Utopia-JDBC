package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;

public class RouteDAO extends BaseDAO<Route> {

    // sending DAO connection to use for route purposes
    public RouteDAO(Connection conn) {
        super(conn);
    }

    public void addRoute(Route route) throws ClassNotFoundException, SQLException {
        save("INSERT into route (origin_id, destination_id) values (?, ?)",
                new Object[] { route.getOriginAirport().getAirportCode(), route.getDestAirport().getAirportCode() });
    }

    public void updateRoute(Route route) throws ClassNotFoundException, SQLException {
        save("UPDATE route set origin_id = ?, destination_id = ? where id = ?", new Object[] {
                route.getOriginAirport().getAirportCode(), route.getDestAirport().getAirportCode(), route.getId() });
    }

    public void deleteRoute(Route route) throws ClassNotFoundException, SQLException {
        save("delete from route where id = ?", new Object[] { route.getId() });
    }

    public List<Route> readAllRoutes() throws ClassNotFoundException, SQLException {
        return read("select * from route", null);
    }
    public List<Route> readRouteById(Integer id) throws ClassNotFoundException, SQLException {
        return read("select * from route where id= ?", new Object[] {id});
    }

    public List<Route> readAllRoutesByAirportCode(String airportCode, String ac) throws ClassNotFoundException, SQLException {
        return read("select * from route where origin_id = ? AND destination_id = ?",
                new Object[] {airportCode, ac});
    }

    @Override
    public List<Route> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<Route> routes = new ArrayList<>();
        while (rs.next()) {
            Route route = new Route();
            route.setId(rs.getInt("id"));
            Airport og = new Airport();
            og.setAirportCode(rs.getString("origin_id"));
            route.setOriginAirport(og);
            Airport dest = new Airport();
            dest.setAirportCode(rs.getString("destination_id"));
            route.setDestAirport(dest);
            routes.add(route);
        }
        return routes;
    }

}