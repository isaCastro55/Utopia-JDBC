package com.ss.utopia.dao;

import com.ss.utopia.entity.Airport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
    public static Connection conn = null;

    // who ever is using me needs to send me a connection, use that connection for all queries
    //DAOs not making connection instead using connection that is being given to them
    public BaseDAO(Connection conn) {
        this.conn = conn;
    }
    public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //TODO: use java 8 streams or foreach
        if(vals!=null) {
            int count = 1;
            for(Object o: vals) {
                pstmt.setObject(count, o);
                count++;
            }
        }
        pstmt.executeUpdate();
    }

    public List<T> read(String sql, Object[] vals) throws ClassNotFoundException, SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //TODO: use java 8 streams or foreach
        if(vals!=null) {
            int count = 1;
            for(Object o: vals) {
                pstmt.setObject(count, o);
                count++;
            }
        }
        return extractData(pstmt.executeQuery());
    }

    abstract public List<T> extractData(ResultSet rs) throws ClassNotFoundException, SQLException;

}