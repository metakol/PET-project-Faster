package com.github.metakol.controllers;

import com.github.metakol.DBHandler.DBHandler;
import com.github.metakol.Launch;
import com.github.metakol.entities.User;
import com.github.metakol.helpers.Scenes;

//
import com.github.metakol.helpers.JacksonUserWriterReader;
//

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;

public class MainSceneController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    

    @FXML
    void onClickRegister(MouseEvent event) {
        /* TODO Темы
         * Вот я думаю, как бы сделать так, чтобы приложение темы выбирала само
         * То есть мы тут поставили например тему светлую, и он знал, что везде надо ставить светлую тему
         * И это всё без лишнего кода
         * Это кстати к вопросу о том, чтобы он запоминал юзера авторизированного, та же тема
         * Я вот что придумал:
         * - хранить информацию о теме приложения в отдельном локальном файле(xml/json например)
         * - сделать класс, который будет содержать например статик поля и эти поля будут иметь системные настройки
         * Думаю рил взять xml или json, и в этом файле записывать всё.
         * */
        URL url = Launch.class.getResource("scenes/registrate.fxml");
        Scenes.sceneChange(event, url);
    }

    @FXML
    User onClickSignIn(MouseEvent event) {
        //TODO Подключение к бд, проверка юзера, авторизация
        User user = new User();
        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        //пишет объект юзера в userData/currentUser.json
        JacksonUserWriterReader.marshall(user);
        //
        System.out.println(loginField.getText());
        System.out.println(passwordField.getText());
        DBHandler handler=new DBHandler();
        handler.connection();
        return user;
    }
        
    


}
