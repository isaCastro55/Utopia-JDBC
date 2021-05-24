package com.ss.utopia.dao;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.BookingPayment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingPaymentDAO extends BaseDAO<BookingPayment> {
    // sending DAO connection to use for route purposes
    public BookingPaymentDAO(Connection conn) {
        super(conn);
    }


    public void addBookingPayment(BookingPayment bp) throws ClassNotFoundException, SQLException {
        save("INSERT into booking_payment (booking_id, stripe_id, refunded) values (?, ?, ?)",
                new Object[] { bp.getBookingId().getId(), bp.getStripeId(), bp.getRefunded()});
    }

    public void updateBookingPaymentByBookingId(BookingPayment bp) throws ClassNotFoundException, SQLException {
        save("UPDATE booking_payment set stripe_id = ? , refunded = ? where booking_id = ?", new Object[] {
                bp.getStripeId(), bp.getRefunded(), bp.getBookingId().getId() });
    }

    public void deleteBookingPayment(BookingPayment bp) throws ClassNotFoundException, SQLException {
        save("delete from booking_payment where booking_id = ?", new Object[] { bp.getBookingId().getId() });
    }

    public List<BookingPayment> readAllBookingPayments() throws ClassNotFoundException, SQLException {
        return read("select * from booking_payment", null);
    }


    @Override
    public List<BookingPayment> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<BookingPayment> bps = new ArrayList<>();
        while (rs.next()) {
            BookingPayment bp = new BookingPayment();
            bp.getBookingId().setId(rs.getInt("booking_id"));
            bp.setStripeId(rs.getString("stripe_id"));
            bp.setRefunded(rs.getShort("refunded"));
            bps.add(bp);
        }
        return bps;
    }
}
