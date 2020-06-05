package tech;

import Foundation.Database;

public class Querries {

    public static int getMAxORder (){

        Database.selectSQL("select max (fldOrderNumber) from tblOrder");

        int maxOrderNumber = Integer.parseInt(Database.getData());

        return maxOrderNumber;


    }
}
