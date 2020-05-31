package sample;

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

    private String getCustomerName() {
        return customerName;
    }


    private int getPhoneNO() {
        return phoneNO;
    }


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