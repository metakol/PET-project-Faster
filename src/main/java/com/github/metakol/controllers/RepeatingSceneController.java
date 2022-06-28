package com.github.metakol.controllers;

import com.github.metakol.entities.Collection;
import com.github.metakol.entities.User;

public class RepeatingSceneController {
    Collection currentCollection;
    User user;
    public RepeatingSceneController(User user, Collection collection){
        this.currentCollection = collection;
        this.user = user;

    }
}
