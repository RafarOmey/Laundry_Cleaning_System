package Domain;


import Foundation.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tech.Select;


public class Cloth {
    private int clothID;
    private double clothPrice;
    private String clothType;
    private int clothAmount;

    public Cloth(String clothType, int clothAmount) {
        this.clothAmount = clothAmount;
        this.clothType = clothType;
    }

    public Cloth() {

    }

    public Cloth(int clothID, double clothPrice) {
        this.clothID = clothID;
        this.clothPrice = clothPrice;
    }

    public void setClothID(int clothID) {
        this.clothID = clothID;
    }

    public void setClothPrice(double clothPrice) {
        this.clothPrice = clothPrice;
    }

    public void setClothType(String clothType) {
        this.clothType = clothType;
    }


    public int getClothAmount() {
        return clothAmount;
    }

    public void setClothAmount(int clothAmount) {
        this.clothAmount = clothAmount;
    }


    public int getClothID() {
        return clothID;
    }

    public double getClothPrice() {
        return clothPrice;
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


        String entry;



        do {


            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                setClothID(Integer.parseInt(entry));
            } else {
                break;
            }

            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                setClothType(entry);
            } else {
                break;
            }

            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                setClothPrice(Double.parseDouble(entry));
            } else {
                break;
            }

            clothingList.add(new Cloth(getClothID(), getClothType(), getClothPrice()));

            setClothingList(clothingList);
        } while (true);

        return getClothingList();
    }


    /**
     * This will get the daily WashOrder which has the progress ID 1
     *
     * @param dailyWashOrder Observable list will store our information about the Daily WashOrder.
     */
    public void populateDailyWashOrderTable(ObservableList<Cloth> dailyWashOrder) {

        String entry = "";
        int count = 0;

        Select.selectClothCount();
        int clothes = Integer.parseInt(Database.getData());

        for (count = 1; count < clothes; count++) {

            Select.selectDailyWashOrderAmount(count);


            entry = Database.getData();
            if (!entry.equals("-ND-")) {
               setClothAmount(Integer.parseInt(entry));
            } else {
                break;
            }

            Select.selectClothName(count);

            entry = Database.getData();
            if (!entry.equals("-ND-")) {
               setClothType(entry);
            } else {
                break;
            }

            dailyWashOrder.add(new Cloth(getClothType(), getClothAmount()));


        }


    }
}

