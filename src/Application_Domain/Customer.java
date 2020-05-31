package Application_Domain;

import Foundation.Database;
import javafx.scene.control.Label;


public class Customer {
    private String customerName;

    private int phoneNO;

    public void setPhoneNO(int phoneNO) {
        this.phoneNO = phoneNO;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }


    public int getPhoneNO() {
        return phoneNO;
    }

    /**
     * This Method will Create a Customer in the Database and make sure aswell to check if customer doesn't already
     * exists in the database.
     *
     * @param label This Label will be used as a confirmation Message if the customer got created or not.
     */
    public void createCustomer(Label label) {


        Database.selectSQL("select fldPhoneNO from tblCustomer where fldPhoneNO= " + getPhoneNO());

        int phoneNumberCheck = 0;

        String entry = Database.getData();
        if (!entry.equals("-ND-")) {
            phoneNumberCheck = Integer.parseInt(entry);
        }


        if (phoneNumberCheck == getPhoneNO()) {
            label.setText("Customer Phone Number Already Exists!");

        } else {
            Database.executeStatement("USE ECO_Laundry_DB EXEC CreateCustomer @customerName =' " + getCustomerName() + "', @CustomerPhoneNO = " + getPhoneNO());
            label.setText("Customer " + getCustomerName() + " created");
        }


    }


}