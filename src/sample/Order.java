package sample;





import Foundation.Database;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Order {




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

   public void createOrder(int customerID, int deliveryPoint){







          Database.executeStatement("INSERT INTO tblOrder (fldCustomerID, fldDeliveryPointID,fldProgressID) values ("+ customerID+","+ deliveryPoint+",1)");










    }






    }



