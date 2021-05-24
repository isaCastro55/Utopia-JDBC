package com.ss.utopia.dao;

import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO extends BaseDAO<Booking> {
    public BookingDAO(Connection conn) {
        super(conn);
    }

    public void addBooking(Booking booking) throws ClassNotFoundException, SQLException {
        save("INSERT into booking (is_active, confirmation_code) values (?, ?)",
                new Object[] { booking.getIsActive(), booking.getConfirmCode() });
    }

    public void updateBooking(Booking booking) throws ClassNotFoundException, SQLException {
        save("UPDATE booking set is_active = ?, confirmation_code = ? where id = ?", new Object[] {
                booking.getIsActive(), booking.getConfirmCode(), booking.getId() });
    }

    public void deleteBooking(Booking booking) throws ClassNotFoundException, SQLException {
        save("delete from booking where id = ?", new Object[] { booking.getId() });
    }

    public List<Booking> readAllBookings() throws ClassNotFoundException, SQLException {
        return read("select * from booking", null);
    }
    public List<Booking> readBookingByConfirm(String confirm) throws ClassNotFoundException, SQLException {
        return read("select * from booking where confirmation_code = ?", new Object[]{confirm});
    }

    @Override
    public List<Booking> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<Booking> bookings = new ArrayList<>();
        while (rs.next()) {
            Booking booking = new Booking();
            booking.setId(rs.getInt("id"));
            booking.setIsActive(rs.getShort("is_active"));
            booking.setConfirmCode(rs.getString("confirmation_code"));
            bookings.add(booking);
        }
        return bookings;
    }
}
