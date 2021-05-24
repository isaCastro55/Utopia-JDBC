package com.ss.utopia.dao;

import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.BookingGuest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingAgentDAO extends BaseDAO<BookingAgent>{
    public BookingAgentDAO(Connection conn) {
        super(conn);
    }

    public void addBookingAgent(BookingAgent ba) throws ClassNotFoundException, SQLException {
        save("INSERT into booking_agent (booking_id,agent_id) values (?, ?)",
                new Object[] { ba.getBookingId().getId(), ba.getAgentId().getId()});
    }

    public void updateBookingAgent(BookingAgent ba) throws ClassNotFoundException, SQLException {
        save("UPDATE booking_agent set agent_id = ? where booking_id = ?", new Object[] {
                ba.getAgentId().getId(), ba.getBookingId().getId()});
    }

    public void deleteBookingAgent(BookingAgent ba) throws ClassNotFoundException, SQLException {
        save("delete from booking_agent where booking_id = ?", new Object[] { ba.getBookingId().getId() });
    }

    public List<BookingAgent> readAllBookingGuests() throws ClassNotFoundException, SQLException {
        return read("select * from booking_agent", null);
    }

    @Override
    public List<BookingAgent> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<BookingAgent> bas = new ArrayList<>();
        while (rs.next()) {
            BookingAgent ba = new BookingAgent();
            ba.getBookingId().setId(rs.getInt("booking_id"));
            ba.getAgentId().setId(rs.getInt("agent_id"));
            bas.add(ba);
        }
        return bas;
    }
}
