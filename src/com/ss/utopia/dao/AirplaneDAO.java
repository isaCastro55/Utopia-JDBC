package com.ss.utopia.dao;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.AirplaneType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneDAO extends BaseDAO<Airplane>{

    public AirplaneDAO(Connection conn) {
        super(conn);
    }

    public void addAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
        save("INSERT into airplane (type_id) values (?)",
                new Object[] { airplane.getType().getId()});
    }

    public void updateAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
        save("UPDATE airplane set type_id = ? where id = ?",
                new Object[] { airplane.getType().getId(), airplane.getId()});
    }

    public void deleteAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
        save("delete from airplane where id = ?", new Object[] { airplane.getId() });
    }

    public List<Airplane> readAllAirplanes() throws ClassNotFoundException, SQLException {
        return read("select * from airplane", null);
    }
    public List<Airplane> readAllAirplanesById(Integer id) throws ClassNotFoundException, SQLException {
        return read("select * from airplane where id = ?", new Object[]{id});
    }
    public List<Airplane> readAllAirplanesByType(Integer id) throws ClassNotFoundException, SQLException {
        return read("select * from airplane where type_id = ?", new Object[]{id});
    }



    @Override
    public List<Airplane> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<Airplane> airplanes = new ArrayList<>();
        while (rs.next()) {
            Airplane airplane = new Airplane();
            airplane.setId(rs.getInt("id"));
            AirplaneType at = new AirplaneType();
            at.setId(rs.getInt("type_id"));
            airplane.setType(at);
            airplanes.add(airplane);
        }
        return airplanes;
    }
}
