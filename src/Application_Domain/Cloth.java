package Application_Domain;


import Foundation.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author
 */
public class Cloth {

    public int getClothID() {
        return clothID;
    }

    public double getClothPrice() {
        return clothPrice;
    }

public Cloth (){

}
 public Cloth (int clothID, double clothPrice){
        this.clothID = clothID;
        this.clothPrice = clothPrice;
 }

    public String getClothType() {
        return clothType;
    }

    private int clothID;
    private double clothPrice;
    private String clothType;


    @Override
    public String toString() {
        return "Cloth{" +
                "clothID=" + clothID +
                ", clothPrice=" + clothPrice +
                ", clothType='" + clothType + '\'' +
                ", clothingList=" + clothingList +
                '}';
    }

    public Cloth(int clothID, String clothType, double clothPrice) {
        this.clothID = clothID;
        this.clothType = clothType;
        this.clothPrice = clothPrice;
    }


    ObservableList<Cloth> clothingList = FXCollections.observableArrayList();


    /**
     * This method will loop through tbl Clothes, and add them to ObservableList
     * @return Returns all Clothings from Database
     */
    public ObservableList<Cloth> populateProductTable() {
        Database.selectSQL("SELECT * from tblClothes");

        int clothID;
        String clothType;
        String entry;
        double clothPrice;


        do {


            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothID = Integer.parseInt(entry);
            } else {
                break;
            }

            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothType = entry;
            } else {
                break;
            }

            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothPrice = Double.parseDouble(entry);
            } else {
                break;
            }


            clothingList.add(new Cloth(clothID, clothType, clothPrice));
        } while (true);

        return clothingList;
    }

}