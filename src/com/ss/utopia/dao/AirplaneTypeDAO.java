package com.ss.utopia.dao;

import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Airport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneTypeDAO extends BaseDAO<AirplaneType>{

    public AirplaneTypeDAO(Connection conn) {
        super(conn);
    }

    public void addAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
        save("INSERT into airplane_type (max_capacity) values (?)",
                new Object[] { airplaneType.getMaxCapacity()});
    }

    public void updateAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
        save("UPDATE airplane_type set max_capacity = ? where id = ?",
                new Object[] { airplaneType.getMaxCapacity(), airplaneType.getId()});
    }

    public void deleteAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
        save("delete from airplane_type where id = ?", new Object[] { airplaneType.getId() });
    }

    public List<AirplaneType> readAllAirplaneTypes() throws ClassNotFoundException, SQLException {
        return read("select * from airplane_type", null);
    }
    public List<AirplaneType> readAllAirplaneTypesById(Integer id) throws ClassNotFoundException, SQLException {
        return read("select * from airplane_type where id = ?", new Object[]{id});
    }


    @Override
    public List<AirplaneType> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<AirplaneType> airplaneTypes = new ArrayList<>();
        while (rs.next()) {
            AirplaneType airplaneType = new AirplaneType();
            airplaneType.setMaxCapacity(rs.getInt("max_capacity"));
            airplaneType.setId(rs.getInt("id"));
            airplaneTypes.add(airplaneType);
        }
        return airplaneTypes;
    }
}
