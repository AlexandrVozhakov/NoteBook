package sample.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import sample.interfaces.NoteBook;
import sample.interfaces.impls.CollectionNoteBook;
import sample.objects.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class NoteBookController {

    @FXML
    private CustomTextField searchTextField;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private Label dateNoteLabel;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    private ListView<Note> noteListView;
    @FXML
    private Button noteSettings;

    private boolean userChangeText = false;

    private NoteBook noteBook;
    private Stage mainStage;

    //private ResourceBundle resourceBundle;


    @FXML
    private void initialize() {

        //resourceBundle = resources;

        noteBook = CollectionNoteBook.getInstance();
        noteListView.setCellFactory(param -> new NoteListCell());

        bookListView.setItems(noteBook.getBooks());
        noteListView.setItems(noteBook.getNotes());

        bookListView.setEditable(true);
        setupClearButtonField(searchTextField);
        initListeners();
        bookListView.getSelectionModel().select(0);
        noteListView.getSelectionModel().select(0);
    }


    private void initListeners() {


        bookListView.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.SECONDARY || event.getClickCount() == 2){
                showEditBookMenu(event);
            }
        });

        bookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            noteBook.changeBook(getSelectedBook());
            noteListView.scrollTo(0);
            noteListView.getSelectionModel().select(0);
        });

        noteListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setNoteTextArea();
        });

        noteTextArea.textProperty().addListener((observableValue, s1, s2) -> {

            if (userChangeText) {

                Note note = getSelectedNote();

                if (note == null) { // if book is empty
                    note = new Note(getSelectedBook().getId(), s2); // create new note
                    noteBook.addNote(note);
                    noteListView.getSelectionModel().select(0);
                }
                note.update(noteTextArea.getText().trim());
                noteBook.updateNote(note);
                noteListView.refresh();
            }
        });

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            String text = searchTextField.getText().trim();
            if(!text.equals("")) {
                noteBook.findNotes(text);
            } else {

                if (newValue.length() == 0)
                    System.out.println("n");
            }

        });

        noteSettings.setOnMouseClicked(event -> showNoteSettings(event));
    }


    private void showEditBookMenu(MouseEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent fxmlMain = load(fxmlLoader, "../fxml/EditBookView.fxml");
        EditBookController bookController = fxmlLoader.getController();
        bookController.setInfo(bookListView);

        PopOver popOver = new PopOver();
        popOver.setContentNode(fxmlMain);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
        popOver.show(mainStage, mainStage.getX(), event.getScreenY());
    }


    private Parent load(FXMLLoader fxmlLoader, String path){

        fxmlLoader.setLocation(getClass().getResource(path));
        Parent fxmlMain = null;

        try {
            fxmlMain = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlMain;
    }


    private void showNoteSettings(MouseEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent fxmlMain = load(fxmlLoader, "../fxml/NoteSettingsView.fxml");
        NoteSettingsController settingsController = fxmlLoader.getController();
        settingsController.setInfo(getSelectedNote());

        PopOver popOver = new PopOver();
        popOver.setPrefWidth(300);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
        popOver.setContentNode(fxmlMain);
        popOver.setOpacity(0.8);
        popOver.show(mainStage, event.getScreenX() - popOver.getPrefWidth()/1.1, event.getScreenY());
    }


    private void setNoteTextArea(){

        Note note = getSelectedNote();
        if (note == null)
            note = new Note(); // для установки пустого текста если удаляется последняя заметка

        userChangeText = false; // блокируем слушателя textarea
        noteTextArea.setText(note.getText());
        dateNoteLabel.setText(note.getCreateDate());
        userChangeText = true;
    }


    public void addBook(){

        Book book = new Book();
        if (noteBook.addBook(book))
            bookListView.getSelectionModel().select(book);
    }


    public void removeBook(){

        noteBook.removeBook(getSelectedBook());
    }


    public void addNote(){

        Note note = new Note(getSelectedBook().getId());
        noteBook.addNote(note);
        noteListView.getSelectionModel().select(note);
    }


    public void removeNote() {

        noteBook.removeNote(getSelectedNote());
    }


    private Book getSelectedBook(){
        return bookListView.getSelectionModel().getSelectedItem();
    }


    private Note getSelectedNote(){
        return noteListView.getSelectionModel().getSelectedItem();
    }


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    private void setupClearButtonField(CustomTextField textField){

        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(true, textField, textField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}