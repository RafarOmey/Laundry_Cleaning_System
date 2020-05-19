package sample;

import Foundation.Database;


public class Customer {



    public void insertTnToCustomer(String customerName, String mail, int phoneNO) {


        Database.executeStatement("insert into tblCustomer (fldName ,fldMail, fldPhoneNo) values('" + customerName + "','" + mail + "' , " + phoneNO + ")");

    }


}