package com.ss.utopia.dao;

import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightBookingDAO extends BaseDAO<FlightBooking> {
    public FlightBookingDAO(Connection conn) {
        super(conn);
    }

    public void addFlightBooking(FlightBooking fb) throws ClassNotFoundException, SQLException {
        save("INSERT into flight_bookings (flight_id, booking_id) values (?,?)",
                new Object[] { fb.getFlightId().getId(), fb.getBookingId().getId()});
    }

//    public void updateFlightBooking(FlightBooking fb) throws ClassNotFoundException, SQLException {
//        save("UPDATE flight_booking set route_id = ? ,airplane_id = ? , departure_time = ? ,reserved_seats = ? , seat_price = ? where id = ?",
//                new Object[] { flight.getRoute().getId(),flight.getPlane().getId(),flight.getDepartureTime(), flight.getReservedSeats(), flight.getSeatPrice(), flight.getId()});
//    }

    public void deleteFlightBooking(FlightBooking fb) throws ClassNotFoundException, SQLException {
        save("delete from flight_bookings where flight_id = ? AND booking_id = ?", new Object[] { fb.getFlightId().getId(), fb.getBookingId().getId() });
    }

    public List<FlightBooking> readAllFlightBookings() throws ClassNotFoundException, SQLException {
        return read("select * from flight_bookings", null);
    }
    public List<FlightBooking> readFlightBookingByFlight_id(Integer id) throws ClassNotFoundException, SQLException {
        return read("select * from flight_bookings where flight_id = ?", new Object[]{id});
    }


    @Override
    public List<FlightBooking> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<FlightBooking> fbs = new ArrayList<>();
        while (rs.next()) {
            FlightBooking fb = new FlightBooking();
            Flight f = new Flight();
            fb.setFlightId(f);
            fb.getFlightId().setId(rs.getInt("flight_id"));
            Booking b= new Booking();
            fb.setBookingId(b);
            fb.getBookingId().setId(rs.getInt("booking_id"));
            fbs.add(fb);
        }
        return fbs;
    }
}
