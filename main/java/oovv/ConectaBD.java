package oovv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBD {
    public static Connection getConnection(String driver, String url, String username, String password, String bd) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, username, password);
        return con;

    }
}
