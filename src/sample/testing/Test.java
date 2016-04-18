package sample.testing;

import sample.controllers.NoteBookController;
import sample.interfaces.NoteBook;
import sample.interfaces.impls.CollectionNoteBook;
import sample.objects.Book;
import sample.objects.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by av on 29.03.16.
 */
public class Test {

    //NoteBookController controller = new NoteBookController();
    NoteBook noteBook = CollectionNoteBook.getInstance();
    String text = "Hello i am using JavaFx to make an application. i have a small png picture that i want to add to the right side of my textField." +
    "it is not a problem to add the picture to the frame of the textField but for some reason i cannot move the picture to any possition" +
    "(which means that it does not move from the starting possition - which is left)" +
    "my code is the following:";

    public void startTest(){

        List<Book> books = new ArrayList<>();
        List<Note> notes = new ArrayList<>();

        for (int i = 0; i < 100; i++){
            books.add(new Book("new book" + i));
            notes.add(new Note(1, ""));
        }
        System.out.print("time add 100 books : ");
        System.out.println(timeAdd100Books(books) + " ms");

        System.out.print("time remove 100 books : ");
        System.out.println(timeRemove100Books(books) + " ms");

        System.out.print("time add 100 notes : ");
        System.out.println(timeAdd100Notes(notes) + " ms");

        System.out.print("time update 100 notes : ");
        System.out.println(timeUpdate100Notes(notes) + " ms");

        System.out.print("time remove 100 notes : ");
        System.out.println(timeRemove100Notes(notes) + " ms");

    }

    private double timeAdd100Books(List<Book> books){

        long time = System.currentTimeMillis();

        for (Book book : books){
            noteBook.addBook(book);
        }
        time = System.currentTimeMillis() - time;
        return (double)time / 1000;
    }

    private double timeRemove100Books(List<Book> books){

        long time = System.currentTimeMillis();

        for (Book book : books){
            noteBook.removeBook(book);
        }
        time = System.currentTimeMillis() - time;
        return (double)time / 1000;
    }

    private double timeAdd100Notes(List<Note> notes){

        long time = System.currentTimeMillis();

        for (Note note : notes){
            noteBook.addNote(note);
        }
        time = System.currentTimeMillis() - time;
        return (double)time / 1000;
    }

    private double timeRemove100Notes(List<Note> notes){

        long time = System.currentTimeMillis();

        for (Note note : notes){
            noteBook.removeNote(note);
        }
        time = System.currentTimeMillis() - time;
        return (double)time / 1000;
    }

    private double timeUpdate100Notes(List<Note> notes){

        long time = System.currentTimeMillis();

        for (Note note : notes){
            note.setText(text);
            noteBook.updateNote(note);
        }
        time = System.currentTimeMillis() - time;
        return (double)time / 1000;
    }

}
