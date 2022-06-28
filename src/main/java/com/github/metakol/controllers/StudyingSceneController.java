package com.github.metakol.controllers;

import com.github.metakol.entities.Collection;
import com.github.metakol.entities.User;

public class StudyingSceneController {
    Collection currentCollection;
    User user;
    public StudyingSceneController(User user, Collection collection){
        this.currentCollection = collection;
        this.user = user;

    }
}
