package org.example.examen3.Model;

/**
 * Clase modelo para representar un ejemplo de uso
 */
public class Example {
    private int idExample;
    private int definitionId;
    private String example;

    // Constructores
    public Example() {}

    public Example(int definitionId, String example) {
        this.definitionId = definitionId;
        this.example = example;
    }

    public Example(int idExample, int definitionId, String example) {
        this.idExample = idExample;
        this.definitionId = definitionId;
        this.example = example;
    }

    // Getters y Setters
    public int getIdExample() {
        return idExample;
    }

    public void setIdExample(int idExample) {
        this.idExample = idExample;
    }

    public int getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(int definitionId) {
        this.definitionId = definitionId;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return example;
    }
}