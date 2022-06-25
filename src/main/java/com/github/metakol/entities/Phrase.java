package com.github.metakol.entities;

public class Phrase {
    String phrase;
    String translation;
    String description;
    public Phrase(String phrase, String translation, String description){
        this.phrase = phrase;
        this.description = description;
        this.translation = translation;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
