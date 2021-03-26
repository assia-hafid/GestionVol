package sample.models;

import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.DriverManager;

public class
DBConnection {

    private Connection connection;
    private static DBConnection instance = new DBConnection();

    private DBConnection() {
        try {
//          Class.forName("com.mysql.jdbc.Driver");
//          System.out.println("driver loaded successfully");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_vol","root","");
            System.out.println("connection établie avec la base de donnée");
            
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Echec de connexion!");
        }
    }

    public Connection getConnection(){
        return connection;
    }
    public static DBConnection getInstance(){
            return instance;
    }


}
