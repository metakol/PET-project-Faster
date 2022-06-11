package com.github.metakol.helpers;
import java.io.File;
import java.io.IOException;

import com.github.metakol.entities.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUserWriterReader {
    //куда пишем объект и откуда потом берем
    private static File currentUserFile = new File("userData/currentUser.json");

    //записывает
    public static void marshall(User user){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(currentUserFile, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //читает
    public static void unmarshall(){
        User currentUser = new User();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readValue(currentUserFile, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
