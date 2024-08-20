package org.david.bookscrud.connection;

import com.mysql.cj.jdbc.Driver;
import org.david.bookscrud.models.Credentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnetionSingleton {

    private static GetConnetionSingleton instance = getInstance();
    private Connection connection ;

    public static GetConnetionSingleton getInstance(){
        if (instance == null) {
            instance = new GetConnetionSingleton();
        }
        return instance;
    }

    public Connection getConnection(Credentials credentials){
        try {
            connection = DriverManager.getConnection(credentials.getUrl(), credentials.getUsername(), credentials.getPassword());
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Error en la conexion: " + e.getMessage());
            System.exit(0);
        }
        return this.connection;
    }
}
