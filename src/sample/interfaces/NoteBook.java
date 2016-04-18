package sample.interfaces;

import javafx.collections.ObservableList;
import sample.objects.Book;
import sample.objects.Note;

import java.util.List;

/**
 * Created by av on 08.03.16.
 */
public interface NoteBook {

    boolean addBook(Book book);
    boolean updateBook(Book book, String text);
    void removeBook(Book book);
    void changeBook(Book book);
    ObservableList<Book> getBooks();

    void addNote(Note note);
    void updateNote(Note note);
    void removeNote(Note note);
    void findNotes(String regex);
    ObservableList<Note> getNotes();
}
