package sample.objects;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by av on 08.03.16.
 */
public class Book {

    //private String name;
    private SimpleStringProperty name = new SimpleStringProperty("");
    private int id;
    private String createDate;
    private String modDate;


    public Book(){
        this("New book");
    }


    public Book(String name){
        this(0, name);
    }


    public Book(int id, String name){

        setId(id);
        this.name = new SimpleStringProperty(name);
        setCreateDate(Service.date());
    }


    public String getName() {
        return name.get();
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getCreateDate() {
        return createDate;
    }


    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getModDate() {
        return modDate;
    }


    public void setModDate(String modDate) {
        this.modDate = modDate;
    }


    public void update(String text){

        this.name.set(text);
        setModDate(Service.date());
    }


    @Override
    public String toString() {
        return name.get();
    }
}
