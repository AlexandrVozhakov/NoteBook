package sample.interfaces.impls;

import sample.interfaces.DB;
import sample.interfaces.DBNoteBook;
import sample.objects.Book;
import sample.objects.Note;
import sample.objects.Service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by av on 12.03.16.
 */
public class DataBase extends DB implements DBNoteBook {

    private static DataBase ourInstance = new DataBase();
    //private Logger log = Logger.getLogger(DataBase.class.getName());
    private String sqlPath = "src/sample/sql/";
    private String dumpPath = sqlPath + "dump.sql";
    private String dbFilePath = sqlPath + "Notes.s3db";


    public static DataBase getInstance() {
        return ourInstance;
    }


    private DataBase() {

        connect();
        create();
    }


    @Override
    protected void connect() {

        connection = null;
        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);

        } catch (ClassNotFoundException e) { e.printStackTrace();
        } catch (SQLException e) { e.printStackTrace();}
    }


    @Override
    protected void create() {

        String [] str = Service.readFile(dumpPath).split(";");
        ArrayList<String> commands = new ArrayList<String>();

        for(String com : str){
            if(!com.trim().equals(""))
                commands.add(com);
        }

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String command : commands) {
            executeQuery(command);
        }
        if (getBooks().size() == 0)
            addBook(new Book("Personal"));
    }


    @Override
    protected void close() {

        try {
            connection.close();
            statement.close();
            resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void executeQuery(String query){

        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void fillNoteList(List<Note> notes, ResultSet resultSet) throws SQLException {

        while(resultSet.next())
        {
            Note n = new Note(resultSet.getInt("book_id"), resultSet.getString("text"));
            n.setId(resultSet.getInt("id"));
            n.setCreateDate(resultSet.getString("create_date"));
            n.setModDate(resultSet.getString("mod_date"));
            n.setInUp(resultSet.getInt("in_up"));
            n.setFontSize(resultSet.getInt("font_size"));
            n.setFontStyle(resultSet.getString("font_style"));
            notes.add(n);
        }
    }


    @Override
    public List<Note> getNotes(Book book) {

        List<Note> notes = new ArrayList<Note>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM notes WHERE book_id = ?");
            preparedStatement.setInt(1, book.getId());
            resSet = preparedStatement.executeQuery();

            fillNoteList(notes, resSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }


    @Override
    public List<Book> getBooks() {

        List<Book> books = new ArrayList<Book>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM books");
            resSet = preparedStatement.executeQuery();

            while(resSet.next())
            {
                Book book = new Book(resSet.getInt("id"), resSet.getString("name"));
                book.setCreateDate(resSet.getString("create_date"));
                book.setModDate(resSet.getString("mod_date"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


    @Override
    public List<Note> searchNotes(String regex) {

        List<Note> notes = new ArrayList<Note>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM notes WHERE text LIKE ?");
            preparedStatement.setString(1, "%" + regex + "%");
            resSet = preparedStatement.executeQuery();

            fillNoteList(notes, resSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }


    @Override
    public int addBook(Book book) {

        String sql = "INSERT INTO books (name, create_date, mod_date) VALUES (?, ?, ?)";
        int id = 0;
        String date = Service.date();

        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, date);
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if(keys.next()){
                id = keys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }


    @Override
    public void updateBook(Book book) {

        String date = Service.date();

        try {
            preparedStatement = connection.prepareStatement("UPDATE books SET " +
                    "name = ?, mod_date = ? WHERE id = ?");

            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeBook(Book book) {

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM books WHERE id = ?");
            preparedStatement.setInt(1, book.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM notes WHERE book_id = ?");
            preparedStatement.setInt(1, book.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int addNote(Note note) {

        String date = Service.date();
        String sql = "INSERT INTO notes (book_id, create_date, mod_date, in_up, text, " +
                "font_style, font_size) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int id = 0;

        try {

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, note.getBook_id());
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, date);
            preparedStatement.setInt(4, note.getInUp());
            preparedStatement.setString(5, note.getText());
            preparedStatement.setString(6, note.getFontStyle());
            preparedStatement.setInt(7, note.getFontSize());
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if(keys.next()){
                id = keys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }


    @Override
    public void updateNote(Note note) {

        try {
            preparedStatement = connection.prepareStatement("UPDATE notes SET " +
                    "text = ?, mod_date = ?, in_up = ?, font_size = ?, font_style = ? WHERE id = ?");

            preparedStatement.setString(1, note.getText());
            preparedStatement.setString(2, note.getModDate());
            preparedStatement.setInt(3, note.getInUp());
            preparedStatement.setInt(4, note.getFontSize());
            preparedStatement.setString(5, note.getFontStyle());
            preparedStatement.setInt(6, note.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("save " + note.getHeader());
    }


    @Override
    public void removeNote(Note note) {

        executeQuery("DELETE FROM notes WHERE id = " + note.getId());
    }
}
