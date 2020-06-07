package Domain;


import Foundation.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tech.Select;


public class Cloth {

    public Cloth(String clothType,int clothAmount ) {
        this.clothAmount = clothAmount;
        this.clothType = clothType;
    }

    private int clothAmount;


    public int getClothAmount() {
        return clothAmount;
    }

    public void setClothAmount(int clothAmount) {
        this.clothAmount = clothAmount;
    }

    private int clothID;
    private double clothPrice;
    private String clothType;

    public int getClothID() {
        return clothID;
    }

    public double getClothPrice() {
        return clothPrice;
    }

    public Cloth() {

    }

    public Cloth(int clothID, double clothPrice) {
        this.clothID = clothID;
        this.clothPrice = clothPrice;
    }

    public String getClothType() {
        return clothType;
    }


    @Override
    public String toString() {
        return "Cloth{ " +
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


    private ObservableList<Cloth> clothingList = FXCollections.observableArrayList();

    public ObservableList<Cloth> getClothingList() {
        return clothingList;
    }

    public void setClothingList(ObservableList<Cloth> clothingList) {
        this.clothingList = clothingList;
    }

    /**
     * This method will loop through tbl Clothes, and add them to ObservableList
     *
     * @return Returns all Clothings from Database
     */
    public ObservableList<Cloth> populateProductTable() {
        Select.selectTableClothes();

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

            setClothingList(clothingList);
        } while (true);

        return getClothingList();
    }


    /**
     * This will get the daily WashOrder which has the progress ID 1
     * @param dailyWashOrder Observable list will store our information about the Daily WashOrder.
     */
    public void populateDailyWashOrderTable(ObservableList<Cloth> dailyWashOrder) {
        String clothName;
        int clothAmount;
        String entry="";
        int count=0;

        Select.selectClothCount();
        int clothes= Integer.parseInt(Database.getData());

        for ( count = 1; count < clothes ; count++) {

            Select.selectDailyWashOrderAmount(count);


                entry = Database.getData();
                if (!entry.equals("-ND-")) {
                    clothAmount = Integer.parseInt(entry);
                } else {
                    break;
                }

               Select.selectClothName(count);

                entry = Database.getData();
                if (!entry.equals("-ND-")) {
                    clothName = entry;
                } else {
                    break;
                }

                dailyWashOrder.add(new Cloth(clothName,clothAmount ));





        }


    }
}

