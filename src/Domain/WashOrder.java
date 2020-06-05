package Domain;


import javafx.collections.ObservableList;
import tech.Insert;
import tech.Update;


public class WashOrder {

    /**
     * This method will take our obverablelist "basket" and loop through all the product there is in there and store them in the Database.
     *
     * @param itemsToBasket  Contains Cloth object.
     */
    public void createWashOrder(ObservableList<Cloth> itemsToBasket) {


        for (Cloth clothID : itemsToBasket) {
            Insert.insertIntoWashOrder(clothID.getClothID());


        }


    }


    /**
     * InsertTotalPrice will get our ObservableList "itemsToBasket" and calculate the total amount the customer has to pay, and then go into the Order table in database and update
     * fldPrice.
     *
     * @param itemsToBasket  ObservableList that contains our Cloth, from there we will get the total amount after we have looped through it.
     */
    public void insertTotalPrice(ObservableList<Cloth> itemsToBasket) {


        double totalPrice = 0;
        for (Cloth clothPrice : itemsToBasket) {


            totalPrice = clothPrice.getClothPrice() + totalPrice;


        }
        Update.updatePriceTblOrder(totalPrice);
    }







}








