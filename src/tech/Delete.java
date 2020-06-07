package tech;


import Foundation.Database;

public class Delete {


    /**
     * @param orderNumber to delete from table orderStatus
     */
    public static void deleteFromOrderStatus(int orderNumber) {
        Database.executeStatement("delete from tblOrderStatus where fldOrderNumber =" + orderNumber);
    
    }


}
