package com.github.metakol.entities;

public class User {
    private int ID;
    private String login;
    private String password;
    private String name;
    private boolean isDarkThemeOn=false;

    public User() {
    }

    public User(int ID, String login, String password, String name, boolean isDarkThemeOn) {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.name = name;
        this.isDarkThemeOn = isDarkThemeOn;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDarkThemeOn() {
        return isDarkThemeOn;
    }

    public void setDarkThemeOn(boolean darkThemeOn) {
        isDarkThemeOn = darkThemeOn;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", isDarkThemeOn=" + isDarkThemeOn +
                '}';
    }
}
