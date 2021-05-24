package com.ss.utopia.dao;

import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.BookingUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingUserDAO extends BaseDAO<BookingUser>{

    public BookingUserDAO(Connection conn) {
        super(conn);
    }


    public void addBookingUser(BookingUser bu) throws ClassNotFoundException, SQLException {
        save("INSERT into booking_user (booking_id,user_id) values (?, ?)",
                new Object[] { bu.getBookingId().getId(), bu.getUserId().getId()});
    }

    public void updateBookingUser(BookingUser bu) throws ClassNotFoundException, SQLException {
        save("UPDATE booking_user set user_id = ? where booking_id = ?", new Object[] {
                bu.getUserId().getId(), bu.getBookingId().getId()});
    }

    public void deleteBookingUser(BookingUser bu) throws ClassNotFoundException, SQLException {
        save("delete from booking_user where booking_id = ?", new Object[] { bu.getBookingId().getId() });
    }

    public List<BookingUser> readAllBookingUsers() throws ClassNotFoundException, SQLException {
        return read("select * from booking_user", null);
    }

    @Override
    public List<BookingUser> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<BookingUser> bus = new ArrayList<>();
        while (rs.next()) {
            BookingUser bu = new BookingUser();
            bu.getBookingId().setId(rs.getInt("booking_id"));
            bu.getUserId().setId(rs.getInt("user_id"));
            bus.add(bu);
        }
        return bus;
    }
}
