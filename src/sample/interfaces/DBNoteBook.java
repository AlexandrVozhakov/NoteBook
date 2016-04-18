package sample.interfaces;

import sample.objects.Book;
import sample.objects.Note;

import java.util.List;

/**
 * Created by av on 09.03.16.
 */
public interface DBNoteBook{

    int addBook(Book book);
    void updateBook(Book book);
    void removeBook(Book book);

    int addNote(Note note);
    void updateNote(Note note);
    void removeNote(Note note);

    List<Note> getNotes(Book book);
    List<Book> getBooks();
    List<Note> searchNotes(String regex);
}
