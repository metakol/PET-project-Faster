package com.github.metakol.controllers;

import com.github.metakol.Launch;
import com.github.metakol.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class RegisterSceneController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    @FXML
    private TextField nameField;

    @FXML
    void onClickGoBack(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/mainScene.fxml"));
    }

    @FXML
    void onClickRegister(MouseEvent event) {
        System.out.println(emailField.getText());
        System.out.println(passField.getText());
        System.out.println(nameField.getText());

        onClickGoBack(event);
    }
}
