package com.github.metakol.controllers;

import com.github.metakol.Launch;
import com.github.metakol.entities.User;
import com.github.metakol.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UserSceneController implements Initializable {

    User user;

    public UserSceneController(User user) {
        System.out.println("Constructor");
        this.user = user;
        System.out.println(user);
    }
    @FXML
    private Button myCollections;
    @FXML
    private Label greetingLabel;
    @FXML
    void onClickAddCollection(MouseEvent event){
        URL url = Launch.class.getResource("scenes/addCollectionScene.fxml");
        Scenes.sceneChange(event, url, new AddCollectionSceneController(user));
    }

    @FXML
    void onClickExit(MouseEvent event) {
        File file = new File("src/main/resources/com/github/metakol/userData/currentUser.json");
        file.delete();
        URL url = Launch.class.getResource("scenes/mainScene.fxml");
        Scenes.sceneChange(event, url);
    }
    @FXML
    void onClickMyCollections(MouseEvent event){
        URL url = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, url, new CollectionsSceneController(user));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize");
        setGreeting();
    }
    private void setGreeting(){
        int currentHourOfDay = LocalTime.now().getHour();
        String greeting;
        if(currentHourOfDay >= 5 && currentHourOfDay <= 12){
            greeting = "Good morning, " + user.getName()+", how are you???";
        }
        else if(currentHourOfDay >= 13 && currentHourOfDay <= 16){
            greeting = "Good day, " + user.getName()+", how are you???";
        }
        else if(currentHourOfDay >= 17 && currentHourOfDay <= 22){
            greeting = "Good evening, " + user.getName()+", how are you???";
        }
        else{
            greeting = "Good night, " + user.getName()+", how are you???";
        }
        greetingLabel.setText(greeting);
    }
}
