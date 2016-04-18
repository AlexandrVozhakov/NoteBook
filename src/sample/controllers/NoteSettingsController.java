package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.controlsfx.control.PopOver;
import sample.interfaces.NoteBook;
import sample.interfaces.impls.CollectionNoteBook;
import sample.objects.Note;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Created by av on 05.04.16.
 */
public class NoteSettingsController implements Initializable {


    @FXML
    private Label wordCountLbl;

    @FXML
    private Label charCountLbl;

    @FXML
    private Label dateCreateLbl;

    @FXML
    private Label dateModLbl;

    @FXML
    private MenuButton fontChangeBtn;

    @FXML
    private MenuButton fontSizeBtn;


    Note note;
    NoteBook noteBook = CollectionNoteBook.getInstance();

    public void setInfo(Note note){

        this.note = note;
        int wordCount = note.getText().split(" ").length; //TODO: проблема лишних пробелов между словами

        wordCountLbl.setText("word: " + wordCount);
        charCountLbl.setText("char: " + (note.getText().length() - (wordCount - 1)));
        dateCreateLbl.setText("create date: " + note.getCreateDate());
        dateModLbl.setText("modify date: " + note.getModDate());
    }


    public void actionSave(ActionEvent actionEvent) {

        noteBook.updateNote(note);
        actionClose(actionEvent);
    }


    public void actionClose(ActionEvent actionEvent) {

        Node source = (Node) actionEvent.getSource();
        PopOver popOver = (PopOver) source.getScene().getWindow();
        popOver.hide();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fontSizeBtn.getItems().clear();
        for (int i = 0; i < 10; i++){
            fontSizeBtn.getItems().add(new MenuItem("" + i));
        }
    }
}
