package com.github.metakol.controllers;

import com.github.metakol.DBEntities.CollectionsTableColumns;
import com.github.metakol.DBEntities.PhrasesTableColumns;
import com.github.metakol.DBHandler.DBHandler;
import com.github.metakol.entities.Collection;
import com.github.metakol.entities.User;
import com.github.metakol.entities.Phrase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddCollectionSceneController {
    Collection collection;

    User user;

    public AddCollectionSceneController(User user){
        this.user = user;
        wordsList = FXCollections.observableList(new ArrayList<Phrase>());
    }


    @FXML
    private TableView wordsTable;
    @FXML
    private TableColumn<Phrase, String> phraseColumn;
    @FXML
    private TableColumn<Phrase, String> translationColumn;
    @FXML
    private TableColumn<Phrase, String> descriptionColumn;
    private ObservableList<Phrase> wordsList;

    @FXML
    private TextField collectionNameField;
    @FXML
    private TextField phraseField;
    @FXML
    private TextField translationField;
    @FXML
    private TextField descriptionField;

    @FXML
    void onClickAddWord(MouseEvent event){
        phraseColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("phrase"));
        translationColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("translation"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("description"));

        wordsList.add(new Phrase(phraseField.getText(), translationField.getText(), descriptionField.getText()));
        wordsTable.setItems(wordsList);
        clearWordEntityFields();
    }
    @FXML
    void onClickComplete(MouseEvent event){
        if(isCollectionNameUnique(collectionNameField.getText().trim())){
            collection = new Collection(collectionNameField.getText().trim(), wordsList.size(), wordsList);

            downloadCollectionIntoDB(collection);

            int downloadedCollectionId = getCollectionIdFromDB(collection);
            if(downloadedCollectionId != -1){
                for(Phrase phrase:wordsList){

                    downloadPhraseIntoDB(phrase, downloadedCollectionId);
                    System.out.println("Слово успешно добавлена");
                }
            }
            else{
                System.out.println("Коллекции с таким именем не существует.");
            }
        }
        else{
            System.out.println("Не получится создать эту коллекцию, т.к ...");
        }

    }
    private boolean isCollectionNameUnique(String collectionName){
        String sql = String.format("SELECT * FROM %s WHERE %s = '%s';",
                CollectionsTableColumns.TABLE_NAME.getNameInDB(), CollectionsTableColumns.NAME.getNameInDB(), collectionName);
        try(DBHandler handler = new DBHandler()){
            ResultSet resultSet = handler.executeQueryStatement(sql);
            if (resultSet.next()){
                System.out.println("Коллекция с таким именем уже есть");
                return false;
            }
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    private void downloadCollectionIntoDB(Collection collection){
        String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES ('%s', %s, %s);",
                CollectionsTableColumns.TABLE_NAME.getNameInDB(), CollectionsTableColumns.NAME.getNameInDB(),
                CollectionsTableColumns.USER_ID.getNameInDB(), CollectionsTableColumns.WORDS_NUMBER.getNameInDB(),
                collection.getName(), user.getID(), collection.getPhrasesNumber());
        try(DBHandler handler = new DBHandler()){
            handler.executeUpdateStatement(sql);
        }
    }
    private int getCollectionIdFromDB(Collection collection){
        int collectionId = -1;
        String sql = String.format("SELECT %s FROM %s WHERE %s='%s';", CollectionsTableColumns.ID.getNameInDB(),
                CollectionsTableColumns.TABLE_NAME.getNameInDB(), CollectionsTableColumns.NAME.getNameInDB(),
                collection.getName());
        try(DBHandler handler = new DBHandler()){
            ResultSet resultSet = handler.executeQueryStatement(sql);
            if(resultSet.next()) {
                collectionId = resultSet.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return collectionId;
    }
    private void downloadPhraseIntoDB(Phrase phrase, int collectionId){
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, '%s','%s','%s');",
                PhrasesTableColumns.TABLE_NAME.getNameInDB(),
                PhrasesTableColumns.COLLECTION_ID.getNameInDB(), PhrasesTableColumns.PHRASE.getNameInDB(),
                PhrasesTableColumns.TRANSLATION.getNameInDB(), PhrasesTableColumns.DESCRIPTION.getNameInDB(),
                collectionId, phrase.getPhrase(), phrase.getTranslation(),phrase.getDescription());
        try(DBHandler dbHandler = new DBHandler()){
            dbHandler.executeUpdateStatement(sql);
        }
    }
    private void clearWordEntityFields(){
        phraseField.setText("");
        descriptionField.setText("");
        translationField.setText("");
    }

}
