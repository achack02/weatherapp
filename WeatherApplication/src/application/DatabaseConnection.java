//package application;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//
//public class DatabaseConnection {   
//	public static void main(String[]args) {
//		try {	
//				Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection connect =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weather_app", "root", "weatherapp"); //connection established
//			System.out.println("Connected with Database");
//			
//			} catch (Exception e) {
//				e.printStackTrace();
//	
//			}
//	}
//}
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
    

    	try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Loads MySQL JDBC driver
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weather_app?useSSL=false&serverTimezone=UTC","root","weatherapp");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.");
        }
    }

    
 
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connected to DB successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
