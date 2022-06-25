package com.github.metakol.controllers;

import com.github.metakol.DBEntities.CollectionsTableColumns;
import com.github.metakol.DBHandler.DBHandler;
import com.github.metakol.entities.Collection;
import com.github.metakol.entities.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CollectionsSceneController implements Initializable {

    User user;
    @FXML
    private Button repeatButton;
    @FXML
    private Button studyButton;
    @FXML
    private Button testButton;
    @FXML
    private Button deleteCollectionButton;


    @FXML
    ListView<Collection> collectionsListView;
    ObservableList<Collection> collections;
    public CollectionsSceneController(User user){

        this.user = user;
    }
    @FXML
    void onClickRepeat(){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        collections = FXCollections.observableArrayList();
        collectionsListView.setItems(collections);
        //тут мы передаем класс, отвечающий за то как элементы списка будут отображаться
        collectionsListView.setCellFactory(new Callback<ListView<Collection>,ListCell<Collection>>() {
            @Override
            public ListCell<Collection> call(ListView<Collection> param)
            {
                return new CollectionsListCell();
            }
        });
        //collectionsList.setCellFactory(p -> new CollectionsListCell());
        getCollectionsFromDB();
        setTooltipForButton(repeatButton, "You'll translate phrases\nfrom English into Russian");
        setTooltipForButton(studyButton, "You'll translate phrases\nfrom Russian into English");
        setTooltipForButton(testButton, "You will be given a test \nto check how well you learned the material");
        setTooltipForButton(deleteCollectionButton, "Click this button\n if you want to delete selected collection");
    }

    //этот класс устанавливает формат отображения для элемента списка
    static private class CollectionsListCell extends ListCell<Collection>{
        @Override
        protected void updateItem(Collection collection, boolean arg1) {
            // FX требует, чтобы super.updateItem () был вызван первым
            super.updateItem(collection, arg1);

            // Отображение ячейки, которую вы хотите достичь
            if(collection == null) {
                this.setText("");
            }
            else {
                this.setText("Collection name: " + collection.getName() + " (" + collection.getPhrasesNumber() + " words)");
            }
        }
    }
    private void getCollectionsFromDB(){
        String sqll = "SELECT collection_id, name, words_number FROM collections WHERE user_id =2";
        String sql = String.format("SELECT %s, %s, %s FROM %s WHERE %s = %s",
                CollectionsTableColumns.ID.getNameInDB(), CollectionsTableColumns.NAME.getNameInDB(), CollectionsTableColumns.WORDS_NUMBER.getNameInDB(),
                CollectionsTableColumns.TABLE_NAME.getNameInDB(), CollectionsTableColumns.USER_ID, user.getID());
        try(DBHandler dbHandler = new DBHandler()){
            ResultSet resultSet = dbHandler.executeQueryStatement(sqll);
            while (resultSet.next()){
                System.out.println("HAS NEXT");
                Collection collection = new Collection();
                collection.setID(resultSet.getInt(1));
                collection.setName(resultSet.getString(2));
                collection.setPhrasesNumber(resultSet.getInt(3));
                collections.add(collection);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void setTooltipForButton(Button button, String message){
        Tooltip tooltip = new Tooltip(message);
        tooltip.setShowDelay(Duration.millis(50));
        button.setTooltip(tooltip);
    }


}
