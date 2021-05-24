package com.ss.utopia.dao;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportDAO extends BaseDAO<Airport> {

    // sending DAO connection to use for route purposes
    public AirportDAO(Connection conn) {
        super(conn);
    }


    public void addAirport(Airport airport) throws ClassNotFoundException, SQLException {
        save("INSERT into airport (iata_id, city) values (?, ?)",
                new Object[] { airport.getAirportCode(), airport.getCityName()});
    }

    public void updateAirportByCity(Airport airport) throws ClassNotFoundException, SQLException {
        save("UPDATE airport set iata_id = ? where city = ?", new Object[] {
                airport.getAirportCode(), airport.getCityName() });
    }
    public void updateAirportByIata(Airport airport) throws ClassNotFoundException, SQLException {
        save("UPDATE airport set city = ? where iata_id = ?", new Object[] {
                airport.getCityName(),  airport.getAirportCode()});
    }

    public void deleteAirport(Airport airport) throws ClassNotFoundException, SQLException {
        save("delete from airport where iata_id = ?", new Object[] { airport.getAirportCode() });
    }

    public List<Airport> readAllAirports() throws ClassNotFoundException, SQLException {
        return read("select * from airport", null);
    }
    public List<Airport> readAirportByCode(String code) throws ClassNotFoundException, SQLException {
        return read("select * from airport where iata_id = ?", new Object[]{code});
    }


    @Override
    public List<Airport> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<Airport> airports = new ArrayList<>();
        while (rs.next()) {
            Airport airport = new Airport();
            airport.setAirportCode(rs.getString("iata_id"));
            airport.setCityName(rs.getString("city"));
            airports.add(airport);
        }
        return airports;
    }
}
