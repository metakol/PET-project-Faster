package com.github.metakol.controllers;

import com.github.metakol.DBEntities.UsersTableColumns;
import com.github.metakol.DBHandler.DBHandler;
import com.github.metakol.Launch;
import com.github.metakol.entities.User;
import com.github.metakol.helpers.JacksonUserWriterReader;
import com.github.metakol.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainSceneController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    void onClickRegister(MouseEvent event) {
        URL url = Launch.class.getResource("scenes/registrate.fxml");
        Scenes.sceneChange(event, url);
    }

    @FXML
    void onClickSignIn(MouseEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (userIsCorrect(login, password)) {
            User user = getUser(login);
            JacksonUserWriterReader.marshall(user);
            URL url = Launch.class.getResource("scenes/userScene.fxml");
            Scenes.sceneChange(event, url, new UserSceneController(user));
        } else {
            System.out.println("Данные не корректны");
        }
    }

    private boolean userIsCorrect(String login, String password) {
        DBHandler handler = new DBHandler();
        handler.open();
        String sql = String.format("SELECT %s, %s FROM %s WHERE %s = '%s';",
                UsersTableColumns.LOGIN.getNameInDB(), UsersTableColumns.PASSWORD.getNameInDB(),
                UsersTableColumns.TABLE_NAME.getNameInDB(), UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try (Statement statement = handler.getStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                if (password.equals(resultSet.getString(UsersTableColumns.PASSWORD.getNameInDB()))) {
                    System.out.println("Успешно");
                    return true;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            handler.close();
        }
        return false;
    }

    private User getUser(String login) {
        User user = null;
        DBHandler handler = new DBHandler();
        handler.open();
        String sql = String.format("SELECT * FROM %s WHERE %s='%s';",
                UsersTableColumns.TABLE_NAME.getNameInDB(), UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try (Statement statement = handler.getStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            user = new User(resultSet.getInt(UsersTableColumns.ID.getNameInDB()),
                    resultSet.getString(UsersTableColumns.LOGIN.getNameInDB()),
                    resultSet.getString(UsersTableColumns.PASSWORD.getNameInDB()),
                    resultSet.getString(UsersTableColumns.NAME.getNameInDB()),
                    resultSet.getBoolean(UsersTableColumns.IS_DARK_THEME_ON.getNameInDB()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            handler.close();
        }
        return user;
    }
}
