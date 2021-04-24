package com.geordin.bdutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
// this should create a single DB connection connection which can be imported.
    private PostgresConnection(){}
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            String url="jdbc:postgresql://localhost:5432/postgres";
            String username="postgres";
            String password="password"; //move this/these to an environmental variable eventually
            connection= DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
    public static Connection getConnection(){
        return connection;
    }

}


