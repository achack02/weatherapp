package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class LoginController {

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField PasswordTF;

    @FXML
    private Label ErrorTF;

    // Called when Login button is clicked
    
    
    
    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameTF.getText();
        String password = PasswordTF.getText();

       
        if (username.isEmpty() || password.isEmpty()) { //When user name or password is empty 
        	ErrorTF.setText("Must Fill In Username and Password!"); //ERROR handle
        	return;
    }
        	try (Connection connect = DatabaseConnection.getConnection()){ //CONNECTING TO MYSQL
            	String query = "SELECT* FROM weather_app.Users WHERE username= ? AND password=?";  //Find matching username and password from database
            	PreparedStatement st = connect.prepareStatement(query);
            
            	st.setString(1,username);
            	st.setString(2, password);
            	
            	ResultSet resultSet = st.executeQuery();

                if (resultSet.next()) {
                    int userId = resultSet.getInt("user_id"); // Retrieve user_id
                	
                // Loads the Forecast page
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("forecast.fxml"));
                    Parent root = loader.load(); //loading fxml

                    ForecastController forecastController = loader.getController();
                    forecastController.setUserId(userId);
                // Opens the current page from the Login button
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                }
            else {
            	 ErrorTF.setText("Invalid username or password!");
            }
                
        	} catch (SQLException e) {
                e.printStackTrace();
                ErrorTF.setText("Database error: " + e.getMessage());
                
            }catch (IOException e) {
                e.printStackTrace();
                ErrorTF.setText("Error loading forecast page.");
                
            
            	}
        	}
        

            
    @FXML
    void handleSignup(ActionEvent event) {
        String username = usernameTF.getText();
        String password = PasswordTF.getText();
    
        
        
        if (username.isEmpty() || password.isEmpty()) { //When user name or password is empty 
        		ErrorTF.setText("Must Fill In Username and Password!"); //ERROR handle
                return; 
        }
        
        try (Connection connect = DatabaseConnection.getConnection()){ 
        	
        	
        String query1 = "SELECT* FROM weather_app.Users WHERE username= ?";//check database if username exists
    	PreparedStatement check = connect.prepareStatement(query1);
    	check.setString(1,username);
    	ResultSet resultSet = check.executeQuery();

    	
    	if (resultSet.next()) {
            ErrorTF.setText("Username already exists.");
           
    	}
    	else {
     
        		String query = "INSERT INTO weather_app.Users (username, password) VALUES (?, ?)"; //Add new users into database
            	PreparedStatement st = connect.prepareStatement(query);
            
            	st.setString(1,username);
            	st.setString(2, password);
            	
            	st.executeUpdate();
            	
            	
            	ErrorTF.setText("Successful Signup! Proceed to login."); 
            	 
    	}
        
    } catch (SQLException e) {
        // Catch SQL exceptions related to the database connection or query
        e.printStackTrace();
        ErrorTF.setText("Database error: " + e.getMessage());
   
    		}
    	}
		
}