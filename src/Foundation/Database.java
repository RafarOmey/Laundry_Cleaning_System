package Foundation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Database {


    private static Connection con;
    private static String port;
    private static String databaseName;
    private static String userName;
    private static String password;
    private static PreparedStatement ps;
    private static int numberOfColumns;
    private static int currentColumnNumber = 1;
    private static ResultSet rs;
    private static String value;
    public static final String noMoreData = "-ND-";
    private static boolean moreData = false;


    static {
        Properties props = new Properties();
        String fileName = "db.properties";
        InputStream input;
        try {
            input = new FileInputStream(fileName);
            props.load(input);
            port = props.getProperty("port", "1433");
            databaseName = props.getProperty("databaseName");
            userName = props.getProperty("userName", "sa");
            password = props.getProperty("password");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");


        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Connects to database with info gotten from db.properties
     */
    private static void connect() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:" + port + ";databaseName=" + databaseName, userName, password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Closes connection
     */
    private static void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Method to execute a select statement
     *
     * @param statement Select statement
     */
    public static void selectSQL(String statement) {

        try {

            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            connect();
            ps = con.prepareStatement(statement);


            rs = ps.executeQuery();
            moreData = rs.next();
            ResultSetMetaData rsmd = rs.getMetaData();
            numberOfColumns = rsmd.getColumnCount();


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * Gets data from the resultset
     *
     * @return next data from resultsets or if there is no data then it returns noMoreData
     */
    public static String getData() {


        if (!moreData) {
            disconnect();
            return noMoreData;
        } else {
            return getNextValue();
        }

    }

    /**
     * Method to get the next value in the result set
     *
     * @return next value in resultset
     */
    public static String getNextValue() {


        try {
            value = rs.getString(currentColumnNumber);
            if (currentColumnNumber >= numberOfColumns) {
                currentColumnNumber = 1;

                moreData = rs.next();
            } else {

                currentColumnNumber++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return value;
    }

    /**
     * This method is used to execute statements other than select
     *
     * @param statement Sql statement to execute
     */
    public static void executeStatement(String statement) {
        try {
            if (ps != null) {
                ps.close();
            }
            connect();
            ps = con.prepareStatement(statement);
            ps.executeUpdate();
            ps.close();

        } catch (RuntimeException | SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }


    }
}






