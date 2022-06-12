package com.github.metakol;

import com.github.metakol.controllers.UserSceneController;
import com.github.metakol.entities.User;
import com.github.metakol.helpers.JacksonUserWriterReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Launch extends Application {

    //тут вот файл дублируется с тем, который в JacksonUserWriterReader лежит, че с дублированием делать?

    //ничё не дублируется, всё по кайфу
    File currentUserFile = new File("src/main/resources/com/github/metakol/userData/currentUser.json");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader;
        Scene scene;
        if (currentUserFile.exists()) {
            User user = JacksonUserWriterReader.unmarshall();
            fxmlLoader = new FXMLLoader(Launch.class.getResource("scenes/userScene.fxml"));
            fxmlLoader.setControllerFactory(c -> new UserSceneController(user));
            // scene.getStylesheets().add(Launch.class.getResource("styles/lightStyle.css").toExternalForm());
        } else {
            fxmlLoader = new FXMLLoader(Launch.class.getResource("scenes/mainScene.fxml"));
            //scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleDark.css").toExternalForm());
            //scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleLight.css").toExternalForm());
        }
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Faster!!!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}