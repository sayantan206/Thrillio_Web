package com.dey.sayantan.thrillio.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtility {
    private static DBUtility instance = new DBUtility();
    private static final String url = "jdbc:mysql://localhost:3306/jid_thrillio?userSSL = false";
    private static final String uid = "root";
    private static final String password = "developer";
    private static Connection conn;

    public static DBUtility getInstance() {
        return instance;
    }

    //TODO: create connection pool
    private DBUtility() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, uid, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn != null) return conn;
        else {
            try {
                conn = DriverManager.getConnection(url, uid, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

}
