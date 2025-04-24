package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ForecastController {
	@FXML
    private ComboBox<String> weatherTF;  //temperatures will be added here..
    @FXML
    private ComboBox<String> clothingTF; // clothing list using this dropdown
    @FXML
    private Label ErrorLabel;
	
    private int userId;
    
    @FXML
    public void initialize() {

    	           // CONNECT API
    	ObservableList<String> temperatureRanges = FXCollections.observableArrayList(
                "Below 30 degrees F", "30-50 degrees F", "50-60 degrees F", "60-70 degrees F",
                "70-80 degrees F", "Above 80 degrees F");
    			weatherTF.setItems(temperatureRanges);
    			
    	ObservableList<String> clothingOptions = FXCollections.observableArrayList
    	    			("T-shirt", "Long-Sleeve Shirt", "Hoodie", "Sweater", "Tank","Cardigan", 
    	    			"Dress", "Shorts", "Jeans","Skirt", "Sweatpants","Leggings",  "Jacket",
    	    			"Vest", "Hat", "Sunglasses", "Sneakers", "Boots", "Flip-flops", "Crocs");
    	    			clothingTF.setItems(clothingOptions);
}
    
    @FXML
    public void savePreferences(ActionEvent event) {
        String selectedRange = weatherTF.getValue();  // Get selected range
        String selectedClothing = clothingTF.getValue();  // Get selected clothing

        if (selectedRange == null || selectedClothing == null) {
            ErrorLabel.setText("Please select both a temperature range and clothing.");
            return;
        }

        try (Connection connect = DatabaseConnection.getConnection()) {

            // Insert clothing preferences into the database for the logged-in user
            String query = "INSERT INTO weather_app.ClothingRecommendation (user_id, temperature_range, clothing_item) VALUES (?, ?, ?)";
            PreparedStatement st = connect.prepareStatement(query);
           
            st.setInt(1, userId);
            st.setString(2, selectedRange);
            st.setString(3, selectedClothing);

            st.executeUpdate();

        System.out.println("Preferences saved");
        

    }   catch (SQLException e){
            e.printStackTrace();
            ErrorLabel.setText("Database error: " + e.getMessage());
        }
    }
    
	@FXML
	private void goToForecast(ActionEvent event) {
	    try {
	        Parent forecastRoot = FXMLLoader.load(getClass().getResource("forecast.fxml"));
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(forecastRoot);
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	 
	    
	}
}

public void setUserId(int userId) {
    this.userId = userId;

}
}
