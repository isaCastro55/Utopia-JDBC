package com.ss.utopia.dao;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO extends BaseDAO<Flight>{

    public FlightDAO(Connection conn) {
        super(conn);
    }

    public void addFlight(Flight flight) throws ClassNotFoundException, SQLException {
        save("INSERT into flight (id, route_id, airplane_id, departure_time, reserved_seats, seat_price) values (?,?,?,?,?,?)",
                new Object[] { flight.getId(),flight.getRoute().getId(),flight.getPlane().getId(),flight.getDepartureTime(), flight.getReservedSeats(), flight.getSeatPrice()});
    }

    public void updateFlight(Flight flight) throws ClassNotFoundException, SQLException {
        save("UPDATE flight set route_id = ? ,airplane_id = ? , departure_time = ? ,reserved_seats = ? , seat_price = ? where id = ?",
                new Object[] { flight.getRoute().getId(),flight.getPlane().getId(),flight.getDepartureTime(), flight.getReservedSeats(), flight.getSeatPrice(), flight.getId()});
    }

    public void deleteFlight(Flight flight) throws ClassNotFoundException, SQLException {
        save("delete from flight where id = ?", new Object[] { flight.getId() });
    }

    public List<Flight> readAllFlights() throws ClassNotFoundException, SQLException {
        return read("select * from flight", null);
    }
    public List<Flight> readFlightsById(Integer id) throws ClassNotFoundException, SQLException {
        return read("select * from flight where id= ?", new Object[]{id});
    }


    @Override
    public List<Flight> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<Flight> flights = new ArrayList<>();
        while (rs.next()) {
            Flight flight = new Flight();
            flight.setId(rs.getInt("id"));
            Route r = new Route();
            r.setId(rs.getInt("route_id"));
            flight.setRoute(r);
            Airplane plane = new Airplane();
            plane.setId(rs.getInt("airplane_id"));
            flight.setPlane(plane);
            flight.setDepartureTime(rs.getTimestamp("departure_time"));
            flight.setReservedSeats(rs.getInt("reserved_seats"));
            flight.setSeatPrice(rs.getFloat("seat_price"));
            flights.add(flight);
        }
        return flights;
    }
}
