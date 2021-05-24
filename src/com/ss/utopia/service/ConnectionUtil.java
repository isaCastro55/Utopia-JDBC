package com.ss.utopia.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(getProperty("driver"));
        Connection conn = DriverManager.getConnection(getProperty("url"), getProperty("username"),
                getProperty("password"));
        // ensures transactions dont commit unless all statements complete successsfully
        conn.setAutoCommit(Boolean.FALSE);
        return conn;
    }

    // use property file incase need to change variables which are final. Dont want to recompile every time change is made
    public String getProperty(String propertyName) {
        try (InputStream input = new FileInputStream("resources/db.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(propertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}