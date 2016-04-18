package sample.objects;

import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by av on 12.03.16.
 */
public class Service {

    private static Font globalFont = new Font(null, 16);


    public static String date(){

        return date("dd  MMMM  yyyy  HH:mm:ss");
    }


    public static String date(String param){

        Date date = new Date();
        return new SimpleDateFormat(param).format(date);
    }


    public static String readFile(String path){

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str;
        StringBuffer sb = new StringBuffer();
        try {
            while ((str = in.readLine()) != null) {
                sb.append(str + "\n ");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static void setGlobalFont(Font font){
        globalFont = font;
    }


    public static Font defaultFont(){
        return globalFont;
    }
}
