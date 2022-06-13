package com.github.metakol.controllers;

import com.github.metakol.DBEntities.UsersTableColumns;
import com.github.metakol.DBHandler.DBHandler;
import com.github.metakol.Launch;
import com.github.metakol.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class RegisterSceneController {

    @FXML
    private Label invalidPassMessage;
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
        String login = emailField.getText();
        String pass = passField.getText();
        String name = nameField.getText();
        if (isUniqueLogin(login)) {
            if(isPasswordValid(pass)) {
                createAccount(login, pass, name);
                onClickGoBack(event);
            }
        } else {
            System.out.println("Такой логин уже есть!");
        }
    }

    private boolean isPasswordValid(String password){
        String pattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[._!,-])[0-9a-zA-Z!._,-]{7,20}";
        if(password.matches(pattern)){
            System.out.println("Password validation successfully");
            return true;
        }
        else if (password.length() < 7 || password.length() > 20){
            invalidPassMessage.setLayoutY(154);
            invalidPassMessage.setText("Password must contain from 7 to 20 characters.");
                return false;
        }
        else{
            invalidPassMessage.setLayoutY(145);
            invalidPassMessage.setText("Password must contain latin letters, numbers and\n" +
                    "at least one of the characters: !,.-_");
            return false;
        }
    }

    private boolean isUniqueLogin(String login) {
        String sql = String.format("SELECT %s FROM %s WHERE %s = '%s';",
                UsersTableColumns.LOGIN.getNameInDB(), UsersTableColumns.TABLE_NAME.getNameInDB(),
                UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try(DBHandler handler = new DBHandler()){
            ResultSet resultSet = handler.executeQueryStatement(sql);
            if(resultSet.next()){
                System.out.println("Такой юзер уже есть");
                return false;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        System.out.println("О, уникальный логин");
        return true;
    }

    private void createAccount(String login, String pass, String name) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s);",
                UsersTableColumns.TABLE_NAME.getNameInDB(),
                UsersTableColumns.LOGIN.getNameInDB(),UsersTableColumns.PASSWORD.getNameInDB(),
                UsersTableColumns.NAME.getNameInDB(), UsersTableColumns.IS_DARK_THEME_ON.getNameInDB(),
                login, pass, name, '0');
        try(DBHandler handler = new DBHandler()) {
            handler.executeUpdateStatement(sql);
        }
    }
}
