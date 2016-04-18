package sample.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by av on 12.03.16.
 */
public abstract class DB {

    protected Connection connection;
    protected Statement statement;
    protected PreparedStatement preparedStatement;
    protected ResultSet resSet;

    abstract protected void create();
    abstract protected void connect();
    abstract protected void close();
}
