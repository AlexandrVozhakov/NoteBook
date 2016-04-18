package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.interfaces.NoteBook;
import sample.interfaces.impls.CollectionNoteBook;
import sample.objects.Book;

/**
 * Created by av on 14.03.16.
 */
public class InputInfoController {

    @FXML
    private TextField inputTextField;
    private NoteBook noteBook;
    private Book book;

    public InputInfoController(){
        noteBook = CollectionNoteBook.getInstance();
    }

    @FXML
    public void actionClose(ActionEvent actionEvent) {

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void actionSave(ActionEvent actionEvent) {

        //book.setName(inputTextField.getText().trim());
        actionClose(actionEvent);
    }

    public void setInfo(Book book){

        this.book = book;
        inputTextField.setText(book.getName());
    }

}
