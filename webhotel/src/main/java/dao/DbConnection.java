package dao;

import global.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(
                    Constants.Db.URL,
                    Constants.Db.USERNAME,
                    Constants.Db.PASSWORD);
        }
        return conn;
    }
}
