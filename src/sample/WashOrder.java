package sample;

import Foundation.Database;

import javafx.collections.ObservableList;




public class WashOrder {

    /**
     *
     *
     *This method will take our obverablelist "basket" and loop through all the product there is in there and store them in the Database.
     * @param itemsToBasket Contains Cloth object.
     * @param maxOrderNumber This will go in from the Database and check the OrderNumber that was just
     *                       created with the products to store the Wash order to the correct Ordernumber
     */
    public void createWashOrder(ObservableList<Cloth> itemsToBasket, int maxOrderNumber) {



            for (Cloth clothID : itemsToBasket) {

                Database.executeStatement("insert into tblWashOrder (fldOrderNumber, fldClothID) values(" + maxOrderNumber + "," + clothID.getClothID() + ")");

            }


        }


    /**
     *
     * @param itemsToBasket
     * @param maxOrderNumber
     */
    public void insertTotalPrice(ObservableList<Cloth> itemsToBasket, int maxOrderNumber) {



        double totalPrice = 0;
        for (Cloth clothPrice : itemsToBasket) {


            totalPrice = clothPrice.getClothPrice() + totalPrice;


        }
        Database.executeStatement("update tblOrder set fldPrice = "+ totalPrice + "  where fldOrderNumber =" + maxOrderNumber);

    }


}








