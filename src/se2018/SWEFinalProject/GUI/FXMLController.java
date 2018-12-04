package se2018.SWEFinalProject.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class FXMLController {
	
	@FXML TextField authorField;
	@FXML TextField titleField;
	@FXML TextField pointsField;
	@FXML VBox todoColumnVBox;
	@FXML Button submitButton;
	 
    @FXML 
    protected void handleAddStoryButtonAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Layouts/add_story_window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Story!");
            stage.setScene(new Scene(root, 450, 700));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    protected void handleBacklogButtonAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Layouts/burndown_window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Backlog");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    protected void handleBurndownButtonAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Layouts/burndown_window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Burndown Chart");
            stage.setScene(new Scene(root, 800, 700));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    protected void handleRefreshButtonAction(ActionEvent event) {
    	
    	//TODO Make for loop and for every story retrieved from server create a new VBOX and append
    	
    	VBox storyPane = new VBox();
    	storyPane.setPrefHeight(80);
    	storyPane.setPrefWidth(300);
    	storyPane.setStyle("-fx-background-color: RGB(130,229,130);");
    	
    	// TODO Set Text here for 
    	storyPane.getChildren().add(new Text("Author: "));
    	storyPane.getChildren().add(new Text("Title: "));
    	storyPane.getChildren().add(new Text("Points: "));
    	storyPane.setOnMouseClicked(e -> {
            System.out.println("Clicked");
            Parent root;
            try {
              
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/Story_Details.fxml"));
                
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Story Details");
                stage.setScene(new Scene(root, 450, 700));
                stage.show();
                
               
                StoryController controller = loader.getController();
                controller.displayAuthorField.setText("JeehadiJonny");
                controller.displayTitleField.setText("Get this Bread (yeet)");
                controller.displayPointsField.setText("5");
                
            }
            catch (IOException d) {
                d.printStackTrace();
            }
        });
    	todoColumnVBox.getChildren().add(storyPane);
    	
    	
    	System.out.println(storyPane.getParent().idProperty().getValue());
    	
    	
    }
    
    @FXML 
    protected void handleStoryClick(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleAddStorySubmitButtonAction(ActionEvent event) {
    	System.out.println(authorField.getText());
    	System.out.println(titleField.getText());
    	System.out.println(pointsField.getText());

    	Stage stage = (Stage) submitButton.getScene().getWindow();
    	stage.close();
    	System.out.println("window closed");
    }
    
    
}
