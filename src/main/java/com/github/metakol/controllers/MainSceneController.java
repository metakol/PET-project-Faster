package com.github.metakol.controllers;

import com.github.metakol.DBEntities.UsersTableColumns;
import com.github.metakol.DBHandler.DBHandler;
import com.github.metakol.Launch;
import com.github.metakol.entities.User;
import com.github.metakol.helpers.JacksonUserWriterReader;
import com.github.metakol.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainSceneController {

    @FXML
    private Label invalidLoginOrPasswordMessage;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    void onClickRegister(MouseEvent event) {
        URL url = Launch.class.getResource("scenes/registration.fxml");
        Scenes.sceneChange(event, url);
    }

    @FXML
    void onClickSignIn(MouseEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (userIsCorrect(login, password)) {
            User user = getUserFromDB(login);
            JacksonUserWriterReader.marshall(user);
            URL url = Launch.class.getResource("scenes/userScene.fxml");
            Scenes.sceneChange(event, url, new UserSceneController(user));
        } else {
            System.out.println("Данные не корректны");
        }
    }

    private boolean userIsCorrect(String login, String password) {
        String sql = String.format("SELECT %s, %s FROM %s WHERE %s = '%s';",
                UsersTableColumns.LOGIN.getNameInDB(), UsersTableColumns.PASSWORD.getNameInDB(),
                UsersTableColumns.TABLE_NAME.getNameInDB(), UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try (DBHandler handler = new DBHandler()) {
            ResultSet resultSet = handler.executeQueryStatement(sql);
            if (resultSet.next()) {
                if (password.equals(resultSet.getString(UsersTableColumns.PASSWORD.getNameInDB()))) {
                    System.out.println("Успешно");
                    return true;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        showInvalidLoginOrPasswordMessage();
        return false;
    }

    private void showInvalidLoginOrPasswordMessage() {
        invalidLoginOrPasswordMessage.setStyle("-fx-font-family: Calibri; -fx-text-fill: #990000; -fx-font-size: 14px");
        invalidLoginOrPasswordMessage.setText("Invalid login or password, try again or go to register");
    }

    @FXML
    private void hideInvalidLoginOrPasswordMessage() {
        invalidLoginOrPasswordMessage.setText("");
    }

    private User getUserFromDB(String login) {
        User user = null;
        String sql = String.format("SELECT * FROM %s WHERE %s ='%s';",
                UsersTableColumns.TABLE_NAME.getNameInDB(), UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try (DBHandler handler = new DBHandler()) {
            ResultSet resultSet = handler.executeQueryStatement(sql);
            user = new User(resultSet.getInt(UsersTableColumns.ID.getNameInDB()),
                    resultSet.getString(UsersTableColumns.LOGIN.getNameInDB()),
                    resultSet.getString(UsersTableColumns.PASSWORD.getNameInDB()),
                    resultSet.getString(UsersTableColumns.NAME.getNameInDB()),
                    resultSet.getBoolean(UsersTableColumns.IS_DARK_THEME_ON.getNameInDB()));
        } catch (SQLException throwables ) {
            throwables.printStackTrace();
        }
        return user;
    }
}
