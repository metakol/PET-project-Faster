package com.github.metakol.controllers;

import com.github.metakol.Launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSceneController {
    public boolean isDarkThemeOn = false;
    public MainSceneController(){

    }
    public Button goButton;
    public Button register;
    public Button darkThemeButton;
    public TextField loginField;
    public TextField passwordField;
    public void changeGoColor(){
        goButton.setStyle("-fx-background-color:#b2ff66");
    }

    public void enableDarkTheme(){
        Scene scene = (Scene) darkThemeButton.getScene();
        scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleDark.css").toExternalForm());
        isDarkThemeOn = true;
    }
    public void goButtonClicked(){
//        User currentUser = new User();
//        currentUser.setLogin(loginField.getCharacters().toString());
//        currentUser.setPassword(passwordField.getCharacters().toString());
//        System.out.println(currentUser);
    }
    public void goToRegisterScene(){
        //Close current
        Stage stage = (Stage) register.getScene().getWindow();
        // do what you have to do
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("scenes/registrate.fxml"));
        Parent scene = null;
        try {
            scene = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registration");
        stage.setScene(new Scene(scene));
        if(isDarkThemeOn){
            scene.getStylesheets().add(Launch.class.getResource("styles/registerStyleDark.css").toExternalForm());
        }
        else{
            scene.getStylesheets().add(Launch.class.getResource("styles/registerStyleLight.css").toExternalForm());
        }
        stage.show();
    }

}
