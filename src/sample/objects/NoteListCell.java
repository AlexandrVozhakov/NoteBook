package sample.objects;

import javafx.scene.control.ListCell;


/**
 * Created by av on 21.03.16.
 */
public class NoteListCell extends ListCell<Note> {

    @Override
    protected void updateItem(Note item, boolean empty) {

        super.updateItem(item, empty);

        if ( empty || item == null ) {

            setText(null);
            setGraphic(null);

        } else {

            NoteHeader header = new NoteHeader(item);
            setGraphic(header);
        }
    }
}
