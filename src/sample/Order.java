package sample;





import Foundation.Database;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Order {





/*
    public void hold(){
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


 */
    public void generateLabel(int orderNumber){




        int count1 = 1;
        int count2;
        Database.selectSQL("select count(*) from tblWash_Order where fldOrderNumber="+orderNumber+"");
        count2= Integer.parseInt(Database.getData());



        String clothName;
        int clothID;
        int orderNumber2;
        String entry;

        Database.selectSQL(" SELECT tblClothes.fldTypeOfCloth, tblWash_Order.fldClothID, tblWash_Order.fldOrderNumber FROM tblClothes" +
                " INNER JOIN tblWash_Order ON tblClothes.fldClothID = tblWash_Order.fldClothID where fldOrderNumber ="+orderNumber+" ");
        do {




            entry=Database.getData();

             if(!entry.equals("-ND-")){
                 clothName = entry;
             }
             else{
                 break;
             }
             entry=Database.getData();
            if(!entry.equals("-ND-")){
                clothID = Integer.parseInt(entry);
            }
            else{
                break;
            }
            entry=Database.getData();
            if(!entry.equals("-ND-")){
                orderNumber2 = Integer.parseInt(entry);
            }
            else{
                break;
            }






            System.out.println(count1+" of "+count2+" clothID= "+clothID+" OrderNumber= "+orderNumber2 + " ClothName= "+clothName);
            count1++;


        } while (true);

    }

   /* public void createOrder(int customerID, int deliveryPoint){

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




*/

    }



