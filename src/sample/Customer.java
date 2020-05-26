package sample;

import Foundation.Database;


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

    public void createCustomer(String customerName,  int phoneNO) {


        Database.executeStatement("insert into tblCustomer (fldName , fldPhoneNo) values('" + customerName +  "' , " + phoneNO + ")");



    }




}