package com.ss.utopia.dao;

import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Passenger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO extends BaseDAO<Passenger> {

    public PassengerDAO(Connection conn) {
        super(conn);
    }

    public void addPassenger(Passenger pass) throws ClassNotFoundException, SQLException {
        save("INSERT into passenger  booking_id, given_name, family_name, dob, gender,address) values (?,?,?,?,?,?)",
                new Object[] { pass.getBookingId().getId(),pass.getGivenName(),pass.getFamilyName(), pass.getDob(), pass.getGender(), pass.getAddress()});
    }

    public void updatePassenger(Passenger pass) throws ClassNotFoundException, SQLException {
        save("UPDATE passenger set booking_id = ? , given_name = ? , family_name = ? , dob = ? , gender = ? ,address = ? where id = ?",
                new Object[] {  pass.getBookingId().getId(),pass.getGivenName(),pass.getFamilyName(), pass.getDob(), pass.getGender(), pass.getAddress(), pass.getId()});
    }

    public void deletePassenger(Passenger pass) throws ClassNotFoundException, SQLException {
        save("delete from passenger where id = ?", new Object[] { pass.getId() });
    }

    public List<Passenger> readAllFlights() throws ClassNotFoundException, SQLException {
        return read("select * from passenger", null);
    }


    @Override
    public List<Passenger> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<Passenger> passengers = new ArrayList<>();
        while (rs.next()) {
            Passenger pass = new Passenger();
            pass.setId(rs.getInt("id"));
            pass.getBookingId().setId(rs.getInt("booking_id"));
            pass.setGivenName(rs.getString("given_name"));
            pass.setDob(rs.getTimestamp("dob"));
            pass.setGender(rs.getString("gender"));
            pass.setAddress(rs.getString("address"));
            passengers.add(pass);
        }
        return passengers;
    }
}
