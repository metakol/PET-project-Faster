package com.github.metakol.controllers;

import com.github.metakol.Launch;
import com.github.metakol.entities.Collection;
import com.github.metakol.entities.User;
import com.github.metakol.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class IntermediateSceneController implements Initializable {
    Logger logger = LogManager.getRootLogger();

    static final HashMap<CollectionsSceneButtons, String> MESSAGES = new HashMap<>(){{
        this.put(CollectionsSceneButtons.REPEAT_BUTTON, "You will be given English words from chosen collection.Your task is to write Russian translation " +
                "for every word. If you can't write the translation, you will be able to see description " +
                "for every word, which you wrote by yourself during adding this collection. Good luck!");
        this.put(CollectionsSceneButtons.STUDY_BUTTON, "You will be given Russian words from chosen collection.Your task is to write English translation " +
                "for every word. If you can't write the translation, you will be able to see description " +
                "for every word, which you wrote by yourself during adding this collection. Good luck!");
        this.put(CollectionsSceneButtons.TEST_BUTTON, "You will be given the test, consisting of three parts. In the first part you need to write"+
                "translation for either Russian or English words, than in the second part you will be given anagrams" +
                "of English words, you need to write correct word(also in English).Good luck!");
    }};
    static final HashMap<CollectionsSceneButtons, URL> URLS = new HashMap<>(){{
        this.put(CollectionsSceneButtons.REPEAT_BUTTON, Launch.class.getResource("scenes/repeatingScene.fxml"));
        this.put(CollectionsSceneButtons.TEST_BUTTON, Launch.class.getResource("scenes/testingScene.fxml"));
        this.put(CollectionsSceneButtons.STUDY_BUTTON, Launch.class.getResource("scenes/studyingScene.fxml"));
    }};
    private HashMap<CollectionsSceneButtons, Object> controllerInstances = new HashMap<>();
    User user;
    Collection currentCollection;
    CollectionsSceneButtons buttonInvokedThisScene;
    @FXML
    private Label messageLabel;
    public IntermediateSceneController(User user, Collection col, CollectionsSceneButtons buttonInvokedThisScene){
        this.user = user;
        this.currentCollection = col;
        this.buttonInvokedThisScene = buttonInvokedThisScene;

        controllerInstances.put(CollectionsSceneButtons.REPEAT_BUTTON, new RepeatingSceneController(user, currentCollection));
        controllerInstances.put(CollectionsSceneButtons.STUDY_BUTTON, new StudyingSceneController(user, currentCollection));
        controllerInstances.put(CollectionsSceneButtons.TEST_BUTTON, new TestingSceneController(user, currentCollection));
    }
    @Override
    public void initialize(URL var1, ResourceBundle var2){
        logger.info("ON INTERMEDIATE SCENE");
        setMessageLabel();
    }
    private void setMessageLabel(){
        messageLabel.setText(MESSAGES.get(buttonInvokedThisScene));
    }
    @FXML
    private void onClickLetsGo(MouseEvent event){
        Scenes.sceneChange(event, URLS.get(buttonInvokedThisScene),
                controllerInstances.get(buttonInvokedThisScene));
    }
    @FXML
    private void onClickGoBack(MouseEvent event){
        URL url = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, url, new CollectionsSceneController(user));
    }

}
