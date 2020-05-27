package sample;

import Foundation.Database;

import javafx.collections.ObservableList;

import javafx.scene.control.TextField;


public class WashOrder {


    public void createWashOrder(ObservableList<Cloth> itemsToBasket, TextField addClothesOrderNumber) {


        for (Cloth clothID : itemsToBasket) {
            Database.executeStatement("insert into tblWashOrder (fldOrderNumber, fldClothID) values(" + addClothesOrderNumber.getText() + "," + clothID.getClothID() + ")");


        }


    }

    public void insertTotalPrice(ObservableList<Cloth> itemsToBasket, TextField addClothesOrderNumber) {


        double totalPrice = 0;
        for (Cloth clothPrice : itemsToBasket) {


            totalPrice = clothPrice.getClothPrice() + totalPrice;


        }
        Database.executeStatement("update tblOrder set fldPrice = "+ totalPrice + "  where fldOrderNumber =" + addClothesOrderNumber.getText());

    }


}








