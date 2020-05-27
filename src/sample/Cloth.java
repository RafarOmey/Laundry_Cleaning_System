package sample;


import Foundation.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @version
 * @author
 */
public class Cloth {
    /**
     * 
     * @param clothID
     * @param clothType
     */
    public Cloth(int clothID, String clothType) {

        this.clothID = clothID;
        this.clothType = clothType;
    }

    public Cloth(String clothType) {
        this.clothType = clothType;
    }

    private String clothType;




    public Cloth(int clothID) {
        this.clothID = clothID;
    }

    public Cloth() {

    }


    public String getClothType() {
        return clothType;
    }



    public int getClothID() {
        return clothID;
    }



    private int clothID;



    @Override
    public String toString() {
        return "Cloth{" +
                "clothType='" + clothType + '\'' +
                ", clothID=" + clothID +
                '}';
    }

    ObservableList<Cloth> clothingList = FXCollections.observableArrayList();

    /**
     * This method will loop through tbl Clothes, and add them to ObservableList
     * @return Returns all Clothings from Database
     */
    public ObservableList<Cloth> populateProductTable(){
        Database.selectSQL("SELECT        fldClothID, fldTypeOfCloth\n" +
                "FROM            tblClothes");

        int clothID;
        String clothType;
        String entry;


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



            clothingList.add(new Cloth(clothID, clothType));
        } while (true);

return clothingList;
    }
}
