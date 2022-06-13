package com.github.metakol.DBEntities;

public enum UsersTableColumns {
    TABLE_NAME("Users"),
    ID("ID"),
    LOGIN("login"),
    PASSWORD("password"),
    NAME("name"),
    IS_DARK_THEME_ON("isDarkThemeOn");

    private UsersTableColumns(String name) {
        this.nameInDB = name;
    }

    private String nameInDB;

    public String getNameInDB() {
        return nameInDB;
    }
}
