package com.github.metakol.controllers;

import com.github.metakol.Launch;
import com.github.metakol.entities.Collection;
import com.github.metakol.entities.Phrase;
import com.github.metakol.entities.User;
import com.github.metakol.helpers.Scenes;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

public class RepeatingSceneController implements Initializable {
    Logger logger = LogManager.getRootLogger();
    Collection currentCollection;
    User user;

    public RepeatingSceneController(User user, Collection collection){
        this.currentCollection = collection;
        this.user = user;
    }
    Phrase currentPhrase;
    int answeredPhrasesNumber;
    @FXML
    private Label repeatingCollectionNameLabel;
    @FXML
    private TextField userTranslationField;
    @FXML
    private Label phraseLabel;
    @FXML
    private Label promptLabel;
    @FXML
    private Label allPhrasesNumberLabel;
    @FXML
    private Label answeredPhrasesNumberLabel;
    @FXML
    private Button readyButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("ON REPEATING SCENE CONTROLLER");
        repeatingCollectionNameLabel.setText("Current collection: " + currentCollection.getName());
        allPhrasesNumberLabel.setText("/" + currentCollection.getPhrasesNumber());
        setTooltipForReadyButton();
        Collections.shuffle(currentCollection.getPhrases());
        setNextPhrase();
    }
    private void setTooltipForReadyButton(){
        Tooltip tooltip = new Tooltip("You can press Enter instead \n of clicking on this button.");
        tooltip.setShowDelay(Duration.millis(50));
        readyButton.setTooltip(tooltip);
    }
    @FXML
    private void onKeyPressedInUserTranslationField(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            onClickReady(event);
        }
    }
    @FXML
    private void onClickReady(Event event){
        hideDescriptionLabel();
        validateUserAnswer();
        boolean isNotAnsweredPhrasePresent = setNextPhrase();
        if(!isNotAnsweredPhrasePresent){
            phraseLabel.setText("All phrases are answered. You can click on Go back button to return to your collections list.");
        }
    }
    private boolean setNextPhrase(){
        userTranslationField.setText("");
        int i = 0;
        while(i < currentCollection.getPhrases().size()) {
            if(!currentCollection.getPhrases().get(i).isAnswered()) {
                currentPhrase = currentCollection.getPhrases().get(i);
                phraseLabel.setText(currentPhrase.getPhrase());
                return true;
            }
            else{
                i++;
            }
        }
        return false;
    }

    private void validateUserAnswer(){
        if(currentPhrase.getTranslation().trim().equals(userTranslationField.getText())){
            currentPhrase.setAnswered(true);
            answeredPhrasesNumberLabel.setText(String.valueOf(++answeredPhrasesNumber));
        }
        else{
            movePhraseToRandomPositionOfPhrasesList();
        }
    }

    private void movePhraseToRandomPositionOfPhrasesList(){
        Random random = new Random();
        int randomIndex = random.nextInt(currentCollection.getPhrases().size());//from 0 to size-1
        currentCollection.getPhrases().remove(currentPhrase);
        currentCollection.getPhrases().add(randomIndex, currentPhrase);
    }
    @FXML
    private void onClickSeeDescription(){
        promptLabel.setText("Description: " + currentPhrase.getDescription());
    }
    @FXML
    private void onClickSeeTranslation() {
        promptLabel.setText("Translation: " + currentPhrase.getTranslation());

    }
    private void hideDescriptionLabel(){
        promptLabel.setText("");
    }
    @FXML
    public void onClickGoBack(MouseEvent event){
        URL url = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, url, new CollectionsSceneController(user));
    }

}
