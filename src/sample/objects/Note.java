package sample.objects;

import javafx.scene.text.Text;

/**
 * Created by av on 08.03.16.
 */
public class Note {

    private int id;
    private int book_id;
    private String header;
    private String createDate;
    private String modDate;
    private String text;
    private String fontStyle;
    private int fontSize;
    private int inUp;


    public Note() {
        this(0,"");
    }


    public Note(int book_id){
        this(book_id, "");
    }


    public Note(int book_id, String text){

        String date = Service.date();
        this.setText(text);
        this.setBook_id(book_id);
        this.setCreateDate(date);
        this.setModDate(date);
        this.setInUp(0);
        this.setFontSize((int)Service.defaultFont().getSize());
        this.setFontStyle(Service.defaultFont().getStyle());
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }


    public int getBook_id() {
        return book_id;
    }


    public String getHeader() {
        return header;
    }


    public String getText() {
        return text;
    }


    public String getCreateDate() {
        return  this.createDate;
    }


    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getTime() {
        return createDate.substring(createDate.lastIndexOf(" "));
    }


    public void setHeader(String text) {

        if (text.contains("\n")) {

            String t = text.substring(0, text.indexOf("\n"));
            this.header = t;
        }
        else if (text.trim().equals("")) {
            this.header = "New note";
        }
        else {
            this.header = text;
        }

        //TODO: контролировать длинну заголовка
    }


    private boolean lengthCheck(String text){
        return getStringLength(text) > 148;
    }


    private int getStringLength(String text){

        Text t = new Text(text);
        t.applyCss();
        return  (int) t.getLayoutBounds().getWidth();
    }


    public void setText(String text) {

        this.text = text;
        setHeader(text);
    }


    public String getModDate() {
        return modDate;
    }


    public void setModDate(String modDate) {
        this.modDate = modDate;
    }


    public void update(String text){

        setText(text);
        setModDate(Service.date());
    }


    public void setInUp(int inUp) {
        this.inUp = inUp;
    }


    public int getInUp() {
        return inUp;
    }


    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }


    public String getFontStyle() {
        return fontStyle;
    }


    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }


    public int getFontSize() {
        return fontSize;
    }
}
