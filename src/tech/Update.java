package tech;

import Foundation.Database;

public class Update {
    /**
     * @param totalPrice price to add to table order
     */
    public static void updatePriceTblOrder(double totalPrice) {
        Database.executeStatement("update tblOrder set fldTotalPricePaid = " + totalPrice + "  where fldOrderNumber =" + Select.getMaxOrder());

    }

    /**
     * @param orderProgressID update the orderprogressID
     * @param employeeID      Which employee updated
     * @param orderNumber     what orderNumber to update
     */
    public static void updateOrderStatus(int orderProgressID, int employeeID, int orderNumber) {
        Database.executeStatement("update tblOrderStatus set fldOrderProgressID = " + orderProgressID + ", fldEmployeeID = " + employeeID + "where fldOrderNumber =" + orderNumber);
    }

}
