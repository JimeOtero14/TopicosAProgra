package org.example.examen3.Model;

/**
 * Clase modelo para representar un sinónimo
 */
public class Synonym {
    private int idSyn;
    private int wordId;
    private String synonym;
    private String synonymLang;

    // Constructores
    public Synonym() {}

    public Synonym(int wordId, String synonym, String synonymLang) {
        this.wordId = wordId;
        this.synonym = synonym;
        this.synonymLang = synonymLang;
    }

    public Synonym(int idSyn, int wordId, String synonym, String synonymLang) {
        this.idSyn = idSyn;
        this.wordId = wordId;
        this.synonym = synonym;
        this.synonymLang = synonymLang;
    }

    // Getters y Setters
    public int getIdSyn() {
        return idSyn;
    }

    public void setIdSyn(int idSyn) {
        this.idSyn = idSyn;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getSynonymLang() {
        return synonymLang;
    }

    public void setSynonymLang(String synonymLang) {
        this.synonymLang = synonymLang;
    }

    @Override
    public String toString() {
        return synonym;
    }
}