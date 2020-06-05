package tech;

import Foundation.Database;

public class StoredP {
    /**
     * @param deliveryPoint where the order is created
     * @param phoneNO       for which customer
     */
    public static void storedCreateOrder(int deliveryPoint, int phoneNO) {
        Database.executeStatement("USE ECO_Laundry_DB EXEC CreateOrder @deliveryPoint = " + deliveryPoint + ", @phoneNO = " + phoneNO);

    }

    /**
     * @param customerName customer name
     * @param phoneNO      customer phone number
     */
    public static void storedCreateCustomer(String customerName, int phoneNO) {
        Database.executeStatement("USE ECO_Laundry_DB EXEC CreateCustomer @customerName =' " + customerName + "', @CustomerPhoneNO = " + phoneNO);

    }

    /**
     * @param progressID  progressID to set to history
     * @param orderNumber for which order number
     * @param employeeID  Which employee did it
     */
    public static void storedChangeLog(int progressID, int orderNumber, int employeeID) {
        Database.executeStatement("USE ECO_Laundry_DB\n" +
                "\n" +
                "EXEC ChangeLog @ProgressID = " + progressID + ", @OrderNumber=" + orderNumber + ",@EmployeeID=" + employeeID);
    }
}
