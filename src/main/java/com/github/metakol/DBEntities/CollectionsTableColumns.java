package com.github.metakol.DBEntities;

public enum CollectionsTableColumns {
    TABLE_NAME("Collections"),
    ID("ID"),
    USER_ID("IDUser"),
    NAME("name");
    private CollectionsTableColumns(String name){
        this.nameInDB = name;
    }
    private String nameInDB;

    public String getNameInDB(){
        return nameInDB;
    }
}
