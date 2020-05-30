package sample;

import Foundation.Database;
import javafx.scene.control.Label;


public class Customer {
    String customerName;

    int phoneNO;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(int phoneNO) {
        this.phoneNO = phoneNO;
    }

    public void createCustomer(String customerName,  int phoneNO, Label label) {


        Database.selectSQL("select fldPhoneNO from tblCustomer where fldPhoneNO= " + phoneNO);

        int phoneNumberCheck = 0;

        String entry = Database.getData();
        if (!entry.equals("-ND-")) {
            phoneNumberCheck = Integer.parseInt(entry);
        }


        if (phoneNumberCheck == phoneNO){
            label.setText("Customer Phone Number Already Exists!");

    }
      else {
            Database.executeStatement("USE ECO_Laundry_DB EXEC CreateCustomer @customerName =' " + customerName + "', @CustomerPhoneNO = " + phoneNO);
            label.setText("Customer " + customerName + " created");
        }



    }




}