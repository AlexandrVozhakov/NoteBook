package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.PopOver;
import sample.interfaces.NoteBook;
import sample.interfaces.impls.CollectionNoteBook;
import sample.objects.Book;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by av on 03.04.16.
 */
public class EditBookController {

    @FXML
    private TextField bookNameFld;
    @FXML
    private Button delBookBtn;
    @FXML
    private Label dateCreateLbl;
    @FXML
    private Label dateModifyLbl;
    @FXML
    private Label bookSizeLbl;


    NoteBook noteBook;
    Book book;

    public EditBookController(){
        noteBook = CollectionNoteBook.getInstance();
    }


    public void actionClose(ActionEvent actionEvent) {

        Node source = (Node) actionEvent.getSource();
        PopOver popOver = (PopOver) source.getScene().getWindow();
        popOver.hide();
    }


    public void actionSave(ActionEvent actionEvent) {

        String text = bookNameFld.getText().trim();

        if (!text.equals(book.getName())){
            noteBook.updateBook(book, text);
        }
        actionClose(actionEvent);
    }


    public void deleteBook(ActionEvent actionEvent) {

        noteBook.removeBook(book);
        actionClose(actionEvent);
    }


    public void setInfo(Book book){

        this.book = book;

        if (book.getId() == 1)
            delBookBtn.setOpacity(0);
        else
            delBookBtn.setOpacity(1.0);

        bookNameFld.setText(book.getName());
        bookSizeLbl.setText(((CollectionNoteBook)noteBook).countNote() + " notes");
        dateCreateLbl.setText("create: " + book.getCreateDate());
        dateModifyLbl.setText("modify: " + book.getModDate());
    }
}
