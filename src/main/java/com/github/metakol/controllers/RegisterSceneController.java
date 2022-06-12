package com.github.metakol.controllers;

import com.github.metakol.DBEntities.UsersTableColumns;
import com.github.metakol.DBHandler.DBHandler;
import com.github.metakol.Launch;
import com.github.metakol.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
        String login = emailField.getText();
        String pass = passField.getText();
        String name = nameField.getText();
        if (isUniqueLogin(login)) {
            createAccount(login, pass, name);
            onClickGoBack(event);
        } else {
            System.out.println("Такой логин уже есть!");
        }
    }

    private boolean isUniqueLogin(String login) {
        DBHandler handler = new DBHandler();
        handler.open();
        String sql = String.format("SELECT %s FROM %s WHERE %s = '%s';",
                UsersTableColumns.LOGIN.getNameInDB(), UsersTableColumns.TABLE_NAME.getNameInDB(),
                UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try(Statement statement = handler.getStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            if(resultSet.next()){
                System.out.println("Такой юзер уже есть");
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            handler.close();
        }
        System.out.println("О, уникальный логин");
        return true;
    }

    private void createAccount(String login, String pass, String name) {
        DBHandler handler = new DBHandler();
        handler.open();
        try(Statement statement = handler.getStatement()){
            String s = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s);",
                    UsersTableColumns.TABLE_NAME.getNameInDB(),
                    UsersTableColumns.LOGIN.getNameInDB(),UsersTableColumns.PASSWORD.getNameInDB(),
                    UsersTableColumns.NAME.getNameInDB(), UsersTableColumns.IS_DARK_THEME_ON.getNameInDB(),
                    login, pass, name, '0');
            String sql = "INSERT INTO Users (login,password,name,isDarkThemeOn) VALUES ('"+login+"','"+pass+"','"+name+"','0')";
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            handler.close();
        }
    }
}
