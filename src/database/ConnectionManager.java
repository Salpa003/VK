package database;

import database.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";

    private ConnectionManager() {

    }

    public static Connection openConnection() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY)); // Загрузка драйвера
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("PostgreSQL Driver not found.");
        }
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
