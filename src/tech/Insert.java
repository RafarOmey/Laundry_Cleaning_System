package tech;

import Domain.Cloth;
import Foundation.Database;

public class Insert {

    /**
     * @param employeeID employeeID who is logged in
     */
    public static void insertToOrderStatus(int employeeID) {
        Database.executeStatement("insert into tblOrderStatus (fldEmployeeID, fldOrderNumber, fldOrderProgressID) values (" + employeeID + "," + Select.getMaxOrder() + ",1)");

    }

    /**
     * @param clothID clothID to insert into table washOrder
     */
    public static void insertIntoWashOrder(Cloth clothID) {
        Database.executeStatement("insert into tblWashOrder (fldOrderNumber, fldClothID) values(" + Select.getMaxOrder() + "," + clothID + ")");
    }

}
