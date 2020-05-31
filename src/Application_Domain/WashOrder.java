package Application_Domain;

import Foundation.Database;

import javafx.collections.ObservableList;


public class WashOrder {

    /**
     * This method will take our obverablelist "basket" and loop through all the product there is in there and store them in the Database.
     *
     * @param itemsToBasket  Contains Cloth object.
     * @param maxOrderNumber will get The Ordernumber we just created from Database.
     */
    public void createWashOrder(ObservableList<Cloth> itemsToBasket, int maxOrderNumber) {

        for (Cloth clothID : itemsToBasket) {

            Database.executeStatement("insert into tblWashOrder (fldOrderNumber, fldClothID) values(" + maxOrderNumber + "," + clothID.getClothID() + ")");

        }


    }


    /**
     * InsertTotalPrice will get our ObservableList "itemsToBasket" and calculate the total amount the customer has to pay, and then go into the Order table in database and update
     * fldPrice.
     *
     * @param itemsToBasket  ObservableList that contains our Cloth, from there we will get the total amount after we have looped through it.
     * @param maxOrderNumber will get The Ordernumber we just created.
     */
    public void insertTotalPrice(ObservableList<Cloth> itemsToBasket, int maxOrderNumber) {


        double totalPrice = 0;
        for (Cloth clothPrice : itemsToBasket) {


            totalPrice = clothPrice.getClothPrice() + totalPrice;


        }
        Database.executeStatement("update tblOrder set fldPrice = " + totalPrice + "  where fldOrderNumber =" + maxOrderNumber);

    }


}








