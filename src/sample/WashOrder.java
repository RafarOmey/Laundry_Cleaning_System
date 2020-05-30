package sample;

import Foundation.Database;

import javafx.collections.ObservableList;




public class WashOrder {


    public void createWashOrder(ObservableList<Cloth> itemsToBasket, int maxOrderNumber) {



            for (Cloth clothID : itemsToBasket) {

                Database.executeStatement("insert into tblWashOrder (fldOrderNumber, fldClothID) values(" + maxOrderNumber + "," + clothID.getClothID() + ")");

            }


        }



    public void insertTotalPrice(ObservableList<Cloth> itemsToBasket, int maxOrderNumber) {



        double totalPrice = 0;
        for (Cloth clothPrice : itemsToBasket) {


            totalPrice = clothPrice.getClothPrice() + totalPrice;


        }
        Database.executeStatement("update tblOrder set fldPrice = "+ totalPrice + "  where fldOrderNumber =" + maxOrderNumber);

    }


}








