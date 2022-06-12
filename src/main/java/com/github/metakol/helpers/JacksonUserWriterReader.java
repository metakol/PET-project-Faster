package com.github.metakol.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.metakol.entities.User;

import java.io.File;
import java.io.IOException;

public class JacksonUserWriterReader {
    //куда пишем объект и откуда потом берем
    private static File currentUserFile = new File("src/main/resources/com/github/metakol/userData/currentUser.json");

    //записывает
    public static void marshall(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(currentUserFile, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //читает
    public static User unmarshall() {
        User currentUser = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            currentUser = mapper.readValue(currentUserFile, User.class);
            System.out.println(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }
}
