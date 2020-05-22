package sample;


import Foundation.Database;



public class Order {




    // Generate label method
    public void generateLabel(int orderNumber) {


        int count1 = 1;
        int count2;
        Database.selectSQL("select count(*) from tblWashOrder where fldOrderNumber=" + orderNumber + "");
        count2 = Integer.parseInt(Database.getData());


        String clothName;
        int clothID;
        int orderNumber2;
        String entry;

        // Select statement to innerjoin tblClothes and tbl Washorder
        Database.selectSQL(" SELECT tblClothes.fldTypeOfCloth, tblWashOrder.fldClothID, tblWashOrder.fldOrderNumber FROM tblClothes" +
                " INNER JOIN tblWashOrder ON tblClothes.fldClothID = tblWashOrder.fldClothID where fldOrderNumber =" + orderNumber + " ");
        //Loop to show how a label would look like
        do {


            entry = Database.getData();

            if (!entry.equals("-ND-")) {
                clothName = entry;
            } else {
                break;
            }
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothID = Integer.parseInt(entry);
            } else {
                break;
            }
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                orderNumber2 = Integer.parseInt(entry);
            } else {
                break;
            }


            System.out.println(count1 + " of " + count2 + " clothID= " + clothID + " OrderNumber= " + orderNumber2 + " ClothName= " + clothName);
            count1++;


        } while (true);

        // Updates the order progress in the database when a label is generated
        Database.executeStatement("update tblOrderStatus set fldOrderProgressID = 2 where fldOrderNumber ="+orderNumber);

    }

    public void createOrder(int customerID, int deliveryPoint) {


        Database.executeStatement("INSERT INTO tblOrder (fldCustomerID, fldDeliveryPointID,fldProgressID) values (" + customerID + "," + deliveryPoint + ",1)");


    }


}








