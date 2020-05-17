package sample;





import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Order {

    private static Connection con;
    private static String port;
    private static String databaseName;
    private static String userName;
    private static String password;



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
            System.out.println("Database Ready");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }









    public void createOrder(int customerID, int deliveryPoint){

        try {

            con = DriverManager.getConnection("jdbc:sqlserver://localhost:" + port + ";databaseName=" + databaseName, userName, password);  // to hide the password in file.


            Statement state = con.createStatement();

            state.execute("INSERT INTO tblOrder (fldCustomerID, fldDeliveryPointID,fldProgressID) values ("+ customerID+","+ deliveryPoint+",1)");








            // (5) close the connection
            con.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }


    }





    }



