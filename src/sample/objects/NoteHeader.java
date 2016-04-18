package sample.objects;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sample.interfaces.NoteBook;
import sample.interfaces.impls.CollectionNoteBook;


/**
 * Created by av on 13.04.16.
 */
public class NoteHeader extends HBox{

    CheckBox checkBox;
    Text header;
    Text date;
    NoteBook noteBook = CollectionNoteBook.getInstance();


    public NoteHeader(Note note){

        checkBox = new CheckBox();
        checkBox.getStylesheets().add(getClass().getResource("../css/check_box.css").toExternalForm());
        checkBox.setSelected(note.getInUp() == 1 ? true : false);
        initListener(checkBox, note);
        header = new Text(note.getHeader());
        header.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
        header.setWrappingWidth(320);

        date = new Text(note.getModDate());
        date.setFill(Color.GRAY);
        date.setFont(Font.font(null, FontWeight.NORMAL, 14));
        VBox vBox = new VBox(header, date);
        this.getChildren().addAll(checkBox, vBox);
    }


    private void initListener(CheckBox checkBox, Note note){

        checkBox.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if (isNowSelected) {
                note.setInUp(1);

            } else {
                note.setInUp(0);
            }
            noteBook.updateNote(note);
        });
    }
}
