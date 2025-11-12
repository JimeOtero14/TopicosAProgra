package org.example.examen3.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase modelo para representar una palabra del diccionario
 */
public class Word {
    private int idWord;
    private String word;
    private String language;
    private Timestamp createdAt;
    private List<Definition> definitions;
    private List<Synonym> synonyms;

    // Constructores
    public Word() {
        this.definitions = new ArrayList<>();
        this.synonyms = new ArrayList<>();
    }

    public Word(String word, String language) {
        this();
        this.word = word;
        this.language = language;
    }

    public Word(int idWord, String word, String language, Timestamp createdAt) {
        this();
        this.idWord = idWord;
        this.word = word;
        this.language = language;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public int getIdWord() {
        return idWord;
    }

    public void setIdWord(int idWord) {
        this.idWord = idWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public void addDefinition(Definition definition) {
        this.definitions.add(definition);
    }

    public List<Synonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Synonym> synonyms) {
        this.synonyms = synonyms;
    }

    public void addSynonym(Synonym synonym) {
        this.synonyms.add(synonym);
    }

    @Override
    public String toString() {
        return word + " (" + language + ")";
    }
}

