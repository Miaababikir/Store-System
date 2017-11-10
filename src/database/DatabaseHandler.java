package database;



import javax.swing.*;
import java.sql.*;

/**
 * Created by Mosab on 11/28/2016.
 */
public class DatabaseHandler {

    private static DatabaseHandler handler;

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    private DatabaseHandler()
    {
        createConnection();
        setupProductTable();
        setupUsersTable();
    }

    public static DatabaseHandler getInctance()
    {
        if(handler == null)
        {
            handler = new DatabaseHandler();
        }

        return handler;
    }

    public void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupProductTable(){

        String TABLE_NAME = "Product";
        try
        {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet table = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (table.next())
            {
                System.out.println(TABLE_NAME + "Ready to go!");
            }
            else
            {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("

                        + " id VARCHAR (200) PRIMARY KEY , \n"
                        + " name VARCHAR (200),\n"
                        + " price VARCHAR (200),\n"
                        + " date VARCHAR (200)\n"
                        + ")"

                );
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setupUsersTable()
    {
        String TABLE_NAME = "USERS";
        try
        {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet table = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (table.next())
            {
                System.out.println(TABLE_NAME + "Ready to go!");
            }
            else
            {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("

                        + " userID VARCHAR (200) PRIMARY KEY , \n"
                        + "userPassword VARCHAR (200))"

                );
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet execQuery(String query)
    {
        ResultSet resultSet ;

        try
        {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
            
        return resultSet;
        
    }

    public boolean execAction(String qu)
    {
        try
        {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }


}
