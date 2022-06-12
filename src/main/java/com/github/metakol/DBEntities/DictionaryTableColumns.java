package com.github.metakol.DBEntities;

public enum DictionaryTableColumns {
    TABLE_NAME("Dictionary"),
    ID("ID"),
    USER_ID("IDUser"),
    PHRASE("phrase"),
    TRANSLATION("translation");

    private DictionaryTableColumns(String name){
        this.nameInDB = name;
    }
    private String nameInDB;

    public String getNameInDB(){
        return nameInDB;
    }
}
