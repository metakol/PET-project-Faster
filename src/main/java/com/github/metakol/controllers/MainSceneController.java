package com.github.metakol.controllers;

import com.github.metakol.Launch;
import com.github.metakol.helpers.Scenes;
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
    private Button btnSwapTheme;

    public boolean isDarkThemeOn = false;

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
    void onClickSignIn(MouseEvent event) {
        //TODO Подключение к бд, проверка юзера, авторизация
        System.out.println(loginField.getText());
        System.out.println(passwordField.getText());
    }


    private boolean isFirstClickOnSwapScene = true;

    @FXML
    void onClickSwapTheme(MouseEvent event) {



        /* TODO Темы
         * Две наши цветовые темы добавлены в классе Launch
         * А в методе swapTheme() мы меняем их положение, так как он отображает ту, что идёт позже
         * А если в коде будем добавлять тему, как было у тебя, а не свапать их местами, то к окну у нас будет подключенно
         * куча одинаковых файлов. Поэтому вроде пока что оптимальный вариант.
         * */
        Scene scene = ((Button) event.getSource()).getScene();

        /*
        * Смысл в том, что когда ты свапаешься на другую сцену и потом обратно сюда, в Launch у тебя уже
        * стили не инициализируются, соответственно, они по нулям. И нужно их снова инициализировать...
        * */
        if (scene.getStylesheets().size()==0) {
            scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleDark.css").toExternalForm());
            scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleLight.css").toExternalForm());
            System.out.println("Initialization themes...");
        }

        swapTheme(scene);
        System.out.println(scene.getStylesheets());
        if (isDarkThemeOn) {
            isDarkThemeOn = false;
            btnSwapTheme.setText("Switch to the dark theme");
        } else {
            isDarkThemeOn = true;
            btnSwapTheme.setText("Switch to the light theme");
        }
    }

    private void swapTheme(Scene scene) {
        //We have dark and light theme.
        String bufURL = scene.getStylesheets().get(0);
        scene.getStylesheets().set(0, scene.getStylesheets().get(1));
        scene.getStylesheets().set(1, bufURL);
    }


//    public Button goButton;
//    public Button register;
//    public Button darkThemeButton;
//    public TextField loginField;
//    public TextField passwordField;

//    public void goButtonClicked(){
//      User currentUser = new User();
//        currentUser.setLogin(loginField.getCharacters().toString());
//        currentUser.setPassword(passwordField.getCharacters().toString());
////        System.out.println(currentUser);
//    }
//    public void goToRegisterScene(){
//        //Close current
//        Stage stage = (Stage) register.getScene().getWindow();
//        // do what you have to do
//        stage.close();
//        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("scenes/registrate.fxml"));
//        Parent scene = null;
//        try {
//            scene = (Parent) fxmlLoader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setTitle("Registration");
//        stage.setScene(new Scene(scene));
//        if(isDarkThemeOn){
//            scene.getStylesheets().add(Launch.class.getResource("styles/registerStyleDark.css").toExternalForm());
//        }
//        else{
//            scene.getStylesheets().add(Launch.class.getResource("styles/registerStyleLight.css").toExternalForm());
//        }
//        stage.show();
//    }

}
