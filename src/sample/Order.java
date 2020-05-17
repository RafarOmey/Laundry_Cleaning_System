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





    public void generateLabel(int orderNumber){
        try {

            con = DriverManager.getConnection("jdbc:sqlserver://localhost:" + port + ";databaseName=" + databaseName, userName, password);  // to hide the password in file.


            Statement state = con.createStatement();
            Statement state2 = con.createStatement();




            ResultSet rs = state.executeQuery(" SELECT tblWash_Order.fldClothID, tblWash_Order.fldOrderNumber, fldClothes.fldTypeOfCloth FROM fldClothes" +
                    " INNER JOIN tblWash_Order ON fldClothes.fldClothID = tblWash_Order.fldClothID where fldOrderNumber ="+orderNumber+" ");

            ResultSet rs2 = state2.executeQuery("select count(*) from tblWash_Order where fldOrderNumber="+orderNumber+"");

            int count1 = 1;
            int count2 = 0;

            while(rs.next()){

                while(rs2.next()) {
                     count2 = rs2.getInt(1);
                }
                int clothID= (rs.getInt(1));
                int orderNumber2 = rs.getInt(2);
                String clothName = rs.getString(3);

                System.out.println(count1+" of "+count2+" clothID= "+clothID+" OrderNumber= "+orderNumber2 + " ClothName= "+clothName);
                count1++;




            }




            // (5) close the connection
            con.close();


        } catch (SQLException e) {
            e.printStackTrace();
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



