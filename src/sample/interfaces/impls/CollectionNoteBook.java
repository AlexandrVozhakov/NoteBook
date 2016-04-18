package sample.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.interfaces.DBNoteBook;
import sample.interfaces.NoteBook;
import sample.objects.Book;
import sample.objects.Note;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by av on 08.03.16.
 */
public class CollectionNoteBook implements NoteBook {

    private ObservableList<Book> books = FXCollections.observableArrayList();
    private ObservableList<Note> notes = FXCollections.observableArrayList();

    private Comparator<? super Note> noteComparator = (n1, n2) -> {

        if(n1.getInUp() > n2.getInUp()) {
            return -1;
        } else if(n1.getInUp() < n2.getInUp()) {
            return 1;
        } else {
            return n2.getModDate().compareToIgnoreCase(n1.getModDate());
        }
    }; //TODO: доделать компаратор


    private DBNoteBook dbNoteBook = DataBase.getInstance();
    public static NoteBook instance = new CollectionNoteBook();
    private Book book;
    private Note note;
    private boolean saveFlag = true;
    private int counter = 0;


    private CollectionNoteBook(){

        fillBookList();
        this.book = books.get(0);
        fillNoteList(this.book);
        //this.note = this.books.size() > 0 ? notes.get(0) : new Note();
        this.note = notes.get(0); //TODO: проблема если нет созданых заметок
        //LocalDateTime ldt = LocalDateTime.parse("2016-04-1312:09:00");
    }


    public static NoteBook getInstance(){
        return instance;
    }


    @Override
    public boolean addBook(Book book) {

        if (this.containsBook(book)) {
            return false;
        }

        int id = dbNoteBook.addBook(book);
        book.setId(id);
        books.add(book);
        return true;
    }


    @Override
    public boolean updateBook(Book book, String text) {

        if (this.containsBook(text))
            return false;

        book.update(text);
        dbNoteBook.updateBook(book);
        return true;
    }


    @Override
    public void removeBook(Book book) {

        // первая книга не удаляется
        if (book.getId() == 1)
            return;
        dbNoteBook.removeBook(book);
        books.remove(book);
    }


    @Override
    public void changeBook(Book book) {

        if (book != null || book.getId() != this.book.getId()) {

            this.book = book;
            fillNoteList(book);
        }
    }


    @Override
    public void addNote(Note note) {

        int id = dbNoteBook.addNote(note);
        note.setId(id);
        notes.add(0, note);
        sortNotes();
    }


    @Override
    public void updateNote(Note note) {

        /* не сортировать если редактируется отсортированная заметка */
        if (this.note != note){ //TODO: сортировать в любом случае при закреплении-откреплении заметки
            sortNotes();
        }
        this.note = note;
        saveNote();
    }


    @Override
    public void removeNote(Note note) {

        if (note != null) {

            dbNoteBook.removeNote(note);
            notes.remove(note);
        }
    }


    @Override
    public void findNotes(String regex) {

        List<Note> notes = dbNoteBook.searchNotes(regex);
        this.notes.clear();
        addAllNotes(notes);
    }


    private void addAllNotes(List<Note> notes) {

        this.notes.addAll(notes.stream().collect(Collectors.toList()));
    }


    private void saveNote(){ //TODO: организовать корректность сохранения при смене заметки

        counter = 0;

        if(saveFlag) {

            saveFlag = false;

            final Thread thread = new Thread(() -> {

                do {
                    try {
                        Thread.sleep(100);
                        counter++;
                    } catch (InterruptedException e) {e.printStackTrace();}
                } while (counter < 6);
                //TODO: подобрать время ожидания
                dbNoteBook.updateNote(CollectionNoteBook.this.note);
                saveFlag = true;
            });
            thread.start();
        }
    }


    private void fillBookList() {

        List<Book> books = dbNoteBook.getBooks();
        this.books.addAll(books.stream().collect(Collectors.toList()));
    }


    private void fillNoteList(Book book) {

        List<Note> notes = dbNoteBook.getNotes(book);
        this.notes.clear(); // TODO: может тормозить

//        SortedList<Note> sortedList = new SortedList<>(this.notes);
//        sortedList.setComparator((n1, n2) -> n2.getDate().compareToIgnoreCase(n1.getCreateDate()));

        addAllNotes(notes);
        sortNotes();
    }


    private void sortNotes(){
        Collections.sort(notes, noteComparator);
    }


    public ObservableList<Book> getBooks() {
        return books;
    }

    public ObservableList<Note> getNotes() {
        return notes;
    }


    public int countNote(){
        return notes.size();
    }


    public int countBook(){
        return books.size();
    }


    private boolean containsBook(Book book){

        for (Book b : books){
            if (b.getName().equalsIgnoreCase(book.getName())) {
                return true;
            }
        }
        return false;
    }


    private boolean containsBook(String name){
        return containsBook(new Book(name));
    }
}
