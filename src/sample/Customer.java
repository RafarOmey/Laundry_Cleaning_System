package sample;

import Foundation.Database;


public class Customer {



    public void insertTnToCustomer(String customerName, String mail, int phoneNO) {

        Database db = new Database();
        db.insertSQL("insert into tblCustomer (fldName ,fldMail, fldPhoneNo) values('" + customerName + "','" + mail + "' , " + phoneNO + ")");

    }


}