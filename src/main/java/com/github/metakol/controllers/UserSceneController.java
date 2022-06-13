package com.github.metakol.controllers;

import com.github.metakol.Launch;
import com.github.metakol.entities.User;
import com.github.metakol.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;

public class UserSceneController {

    User user;
    public UserSceneController(User user){
        this.user = user;
        System.out.println(user);
    }
    @FXML
    private Label greetingLabel;

    @FXML
    void onClickExit(MouseEvent event){
        File file = new File("src/main/resources/com/github/metakol/userData/currentUser.json");
        file.delete();
        URL url= Launch.class.getResource("scenes/mainScene.fxml");
        Scenes.sceneChange(event,url);
    }

}
