package org.example.examen3.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase modelo para representar una definición
 */
public class Definition {
    private int idDefinition;
    private int wordId;
    private String definition;
    private String partOfSpeech;
    private List<Example> examples;

    // Constructores
    public Definition() {
        this.examples = new ArrayList<>();
    }

    public Definition(int wordId, String definition, String partOfSpeech) {
        this();
        this.wordId = wordId;
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    public Definition(int idDefinition, int wordId, String definition, String partOfSpeech) {
        this();
        this.idDefinition = idDefinition;
        this.wordId = wordId;
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    // Getters y Setters
    public int getIdDefinition() {
        return idDefinition;
    }

    public void setIdDefinition(int idDefinition) {
        this.idDefinition = idDefinition;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public void addExample(Example example) {
        this.examples.add(example);
    }

    @Override
    public String toString() {
        return definition + " [" + partOfSpeech + "]";
    }
}