package com.github.metakol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Launch extends Application {

    //тут вот файл дублируется с тем, который в JacksonUserWriterReader лежит, че с дублированием делать?
    File currentUserFile = new File("userData/currentUser.json");
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainFxmlLoader = new FXMLLoader(Launch.class.getResource("scenes/mainScene.fxml"));
        FXMLLoader subFxmlLoader = new FXMLLoader(Launch.class.getResource("scenes/userScene.fxml"));
        Scene scene;
        if (currentUserFile.exists()) {
            scene = new Scene(subFxmlLoader.load());
            scene.getStylesheets().add(Launch.class.getResource("styles/lightStyle.css").toExternalForm());
        } else {
            scene = new Scene(mainFxmlLoader.load());
            scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleDark.css").toExternalForm());
            scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleLight.css").toExternalForm());
        }
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}