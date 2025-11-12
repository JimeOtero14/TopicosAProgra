package org.example.examen3.Controller;

import org.example.examen3.DAO.*;
import org.example.examen3.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Controlador principal de la aplicación (patrón MVC)
 */
public class MainController {

    // Componentes de búsqueda
    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cmbSearchType;
    @FXML private ComboBox<String> cmbLanguage;
    @FXML private Button btnSearch;

    // Lista de palabras encontradas
    @FXML private ListView<Word> lstWords;

    // Información de la palabra seleccionada
    @FXML private Label lblWordTitle;
    @FXML private TextArea txtDefinitions;
    @FXML private TextArea txtSynonyms;
    @FXML private TextArea txtNewDefinition;

    // Formulario para nueva palabra
    @FXML private TextField txtNewWord;
    @FXML private ComboBox<String> cmbNewLanguage;
    @FXML private ComboBox<String> cmbPartOfSpeech;
    @FXML private TextField txtNewSynonym;
    @FXML private TextField txtNewExample;
    @FXML private Button btnAddWord;
    @FXML private Button btnAddDefinition;
    @FXML private Button btnAddSynonym;
    @FXML private Button btnAddExample;
    @FXML private Button btnDeleteWord;

    // DAOs
    private WordDAO wordDAO;
    private DefinitionDAO definitionDAO;
    private SynonymDAO synonymDAO;
    private ExampleDAO exampleDAO;

    // Palabra seleccionada actualmente
    private Word selectedWord;

    /**
     * Inicialización del controlador
     */
    @FXML
    public void initialize() {
        try {
            // Inicializar DAOs
            wordDAO = new WordDAO();
            definitionDAO = new DefinitionDAO();
            synonymDAO = new SynonymDAO();
            exampleDAO = new ExampleDAO();

            // Configurar ComboBoxes
            setupComboBoxes();

            // Configurar ListView
            setupListView();

            // Cargar todas las palabras al inicio
            loadAllWords();

            System.out.println("Controlador inicializado correctamente");

        } catch (SQLException e) {
            showError("Error de conexión", "No se pudo conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Configura los ComboBoxes con sus valores
     */
    private void setupComboBoxes() {
        // Tipos de búsqueda
        cmbSearchType.setItems(FXCollections.observableArrayList(
                "Exacta", "Parcial", "Por idioma"
        ));
        cmbSearchType.setValue("Parcial");

        // Idiomas
        ObservableList<String> languages = FXCollections.observableArrayList(
                "es", "en", "fr", "de", "it", "pt"
        );
        cmbLanguage.setItems(languages);
        cmbLanguage.setValue("es");
        cmbNewLanguage.setItems(languages);
        cmbNewLanguage.setValue("es");

        // Categorías gramaticales
        cmbPartOfSpeech.setItems(FXCollections.observableArrayList(
                "sustantivo", "verbo", "adjetivo", "adverbio", "pronombre",
                "preposición", "conjunción", "interjección"
        ));
        cmbPartOfSpeech.setValue("sustantivo");
    }

    /**
     * Configura el ListView de palabras
     */
    private void setupListView() {
        lstWords.setCellFactory(param -> new ListCell<Word>() {
            @Override
            protected void updateItem(Word word, boolean empty) {
                super.updateItem(word, empty);
                if (empty || word == null) {
                    setText(null);
                } else {
                    setText(word.getWord() + " [" + word.getLanguage() + "]");
                }
            }
        });

        // Listener para selección de palabra
        lstWords.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        loadWordDetails(newValue);
                    }
                }
        );
    }

    /**
     * Carga todas las palabras del diccionario
     */
    private void loadAllWords() {
        try {
            List<Word> words = wordDAO.findAll();
            lstWords.setItems(FXCollections.observableArrayList(words));
        } catch (SQLException e) {
            showError("Error", "No se pudieron cargar las palabras: " + e.getMessage());
        }
    }

    /**
     * Busca palabras según el criterio seleccionado
     */
    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().trim();
        String searchType = cmbSearchType.getValue();

        if (searchText.isEmpty() && !searchType.equals("Por idioma")) {
            showWarning("Campo vacío", "Por favor ingrese un término de búsqueda");
            return;
        }

        try {
            List<Word> results;

            switch (searchType) {
                case "Exacta":
                    results = wordDAO.findByExactMatch(searchText);
                    break;
                case "Parcial":
                    results = wordDAO.findByPartialMatch(searchText);
                    break;
                case "Por idioma":
                    results = wordDAO.findByLanguage(cmbLanguage.getValue());
                    break;
                default:
                    results = wordDAO.findAll();
            }

            lstWords.setItems(FXCollections.observableArrayList(results));

            if (results.isEmpty()) {
                showInfo("Sin resultados", "No se encontraron palabras con ese criterio");
            }

        } catch (SQLException e) {
            showError("Error de búsqueda", "Error al buscar palabras: " + e.getMessage());
        }
    }

    /**
     * Carga los detalles de una palabra seleccionada
     */
    private void loadWordDetails(Word word) {
        selectedWord = word;
        lblWordTitle.setText(word.getWord() + " (" + word.getLanguage() + ")");

        try {
            // Cargar definiciones
            List<Definition> definitions = definitionDAO.findByWordId(word.getIdWord());
            StringBuilder defText = new StringBuilder();

            for (Definition def : definitions) {
                defText.append("• [").append(def.getPartOfSpeech()).append("] ")
                        .append(def.getDefinition()).append("\n");

                // Cargar ejemplos de esta definición
                List<Example> examples = exampleDAO.findByDefinitionId(def.getIdDefinition());
                for (Example ex : examples) {
                    defText.append("   Ejemplo: ").append(ex.getExample()).append("\n");
                }
                defText.append("\n");
            }
            txtDefinitions.setText(defText.toString());

            // Cargar sinónimos
            List<Synonym> synonyms = synonymDAO.findByWordId(word.getIdWord());
            StringBuilder synText = new StringBuilder();
            for (Synonym syn : synonyms) {
                synText.append("• ").append(syn.getSynonym()).append("\n");
            }
            txtSynonyms.setText(synText.toString());

        } catch (SQLException e) {
            showError("Error", "No se pudieron cargar los detalles: " + e.getMessage());
        }
    }

    /**
     * Agrega una nueva palabra
     */
    @FXML
    private void handleAddWord() {
        String word = txtNewWord.getText().trim();
        String language = cmbNewLanguage.getValue();

        if (word.isEmpty()) {
            showWarning("Campo vacío", "Por favor ingrese una palabra");
            return;
        }

        try {
            Word newWord = new Word(word, language);
            int wordId = wordDAO.insert(newWord);
            newWord.setIdWord(wordId);

            showSuccess("Éxito", "Palabra agregada correctamente");
            txtNewWord.clear();
            loadAllWords();

        } catch (SQLException e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Agrega una definición a la palabra seleccionada
     */
    @FXML
    private void handleAddDefinition() {
        if (selectedWord == null) {
            showWarning("No hay palabra seleccionada", "Por favor seleccione una palabra de la lista");
            return;
        }

        String defText = txtNewDefinition.getText().trim();
        String partOfSpeech = cmbPartOfSpeech.getValue();

        if (defText.isEmpty()) {
            showWarning("Campo vacío", "Por favor ingrese una definición");
            return;
        }

        try {
            Definition definition = new Definition(selectedWord.getIdWord(), defText, partOfSpeech);
            definitionDAO.insert(definition);

            showSuccess("Éxito", "Definición agregada correctamente");
            txtNewDefinition.clear();
            loadWordDetails(selectedWord);

        } catch (SQLException e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Agrega un sinónimo a la palabra seleccionada
     */
    @FXML
    private void handleAddSynonym() {
        if (selectedWord == null) {
            showWarning("No hay palabra seleccionada", "Por favor seleccione una palabra de la lista");
            return;
        }

        String synText = txtNewSynonym.getText().trim();

        if (synText.isEmpty()) {
            showWarning("Campo vacío", "Por favor ingrese un sinónimo");
            return;
        }

        try {
            Synonym synonym = new Synonym(selectedWord.getIdWord(), synText, selectedWord.getLanguage());
            synonymDAO.insert(synonym);

            showSuccess("Éxito", "Sinónimo agregado correctamente");
            txtNewSynonym.clear();
            loadWordDetails(selectedWord);

        } catch (SQLException e) {
            showError("Error", "No se pudo agregar el sinónimo: " + e.getMessage());
        }
    }

    /**
     * Elimina la palabra seleccionada
     */
    @FXML
    private void handleDeleteWord() {
        if (selectedWord == null) {
            showWarning("No hay palabra seleccionada", "Por favor seleccione una palabra de la lista");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Eliminar palabra?");
        alert.setContentText("Se eliminará '" + selectedWord.getWord() + "' y todos sus datos relacionados");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                wordDAO.delete(selectedWord.getIdWord());
                showSuccess("Éxito", "Palabra eliminada correctamente");
                selectedWord = null;
                lblWordTitle.setText("");
                txtDefinitions.clear();
                txtSynonyms.clear();
                loadAllWords();
            } catch (SQLException e) {
                showError("Error", "No se pudo eliminar la palabra: " + e.getMessage());
            }
        }
    }

    // Métodos de utilidad para mostrar alertas
    private void showError(String title, String message) {
        showAlert(AlertType.ERROR, title, message);
    }

    private void showWarning(String title, String message) {
        showAlert(AlertType.WARNING, title, message);
    }

    private void showSuccess(String title, String message) {
        showAlert(AlertType.INFORMATION, title, message);
    }

    private void showInfo(String title, String message) {
        showAlert(AlertType.INFORMATION, title, message);
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}