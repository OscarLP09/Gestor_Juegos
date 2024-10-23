package org.example.gestorjuegosjdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static Connection con;


    static {
        String url = "jdbc:mysql://localhost:3306/ad";
        String user ="root";
        //definir la variable de desarrollo
        String pass = System.getenv("DB_PASSWORD");
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getCon(){ return con; }


}
