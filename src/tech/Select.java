package tech;

import Foundation.Database;

public class Select {
public static void selectClothName (int count){
    Database.selectSQL("Select fldTypeOfCloth from tblClothes where fldClothID= "+count);
}
    public static void selectDailyWashOrderAmount (int count){

        Database.selectSQL("SELECT count( tblWashOrder.fldClothID) from tblWashOrder        INNER JOIN tblOrderHistory " +
                " ON tblWashOrder.fldOrderNumber = \n" +
                "                tblOrderHistory.fldOrderNumber where fldOrderProgressID=1 and fldClothID= " +count +
                "\t\t\t\tand  fldDate >=(select (convert(VARCHAR(10),getdate(),101))) AND fldDate <= (select (convert(VARCHAR(10)" +
                ",getdate()+1,101))) ");

    }

    /**
     * We look at the amount of different type of Clothes we have.
     */
    public static void selectClothCount(){
    Database.selectSQL("select count(fldTypeOfCloth) from tblClothes");

}
    /**
     * @return will return the highest orderNumber from tblOrder
     */
    public static int getMaxOrder() {

        Database.selectSQL("select max (fldOrderNumber) from tblOrder");

        int maxOrderNumber = Integer.parseInt(Database.getData());

        return maxOrderNumber;


    }

    /**
     * @param employeeID employeeID to get the name for
     */
    public static void selectEmployeeName(int employeeID) {
        Database.selectSQL("select fldName from tblEmployee where fldEmployeeID=" + employeeID);

    }

    /**
     * @param deliveryPoint delivery pointID
     */
    public static void selectDeliveryPointID(int deliveryPoint) {
        Database.selectSQL("SELECT fldDeliveryPointID FROM tblDeliveryPoint where fldDeliveryPointID =" + deliveryPoint);
    }

    /**
     * @param phoneNO Customers phoneNO
     */
    public static void selectPhoneNumberCustomer(int phoneNO) {
        Database.selectSQL("select fldPhoneNO from tblCustomer where fldPhoneNO= " + phoneNO);
    }

    /**
     * @param orderNumber orderNumber to get the wash orders for
     */
    public static void selectInnerJoinTableClothesWashOrder(int orderNumber) {
        Database.selectSQL(" SELECT tblClothes.fldTypeOfCloth, tblWashOrder.fldClothID, tblWashOrder.fldOrderNumber FROM tblClothes" +
                " INNER JOIN tblWashOrder ON tblClothes.fldClothID = tblWashOrder.fldClothID where fldOrderNumber =" + orderNumber + " ");

    }

    /**
     * @param employeeID get the jobID for the employeeID
     */
    public static void selectJobIDUserLogin(int employeeID) {
        Database.selectSQL("select fldJobID from tblUserLogin where fldEmployeeID=" + employeeID);

    }

    /**
     * @param phoneNO customer phone number so you can get the name belonging to it
     */
    public static void selectCustomerName(int phoneNO) {
        Database.selectSQL("select fldName from tblCustomer where fldPhoneNO =  " + phoneNO);
    }

    /**
     * @param orderNumber orderNumber to get the current progressID for in order status table
     */
    public static void selectMaxProgressIDOrderStatus(int orderNumber) {
        Database.selectSQL("Select top 1 fldOrderProgressID from tblOrderStatus where fldOrderNumber= " + orderNumber + "order by fldOrderProgressID desc");

    }

    /**
     * @param orderNumber orderNumber to get the customers name and where it was created
     */
    public static void selectInnerJoinCustomerOrderDeliveryPoint(int orderNumber) {
        Database.selectSQL("SELECT tblCustomer.fldName,tblCustomer.fldPhoneNO,tblOrder.fldOrderNumber, tblDeliveryPoint.fldDeliveryPointName\n" +
                "FROM ((tblOrder\n" +
                "INNER JOIN tblCustomer ON tblOrder.fldPhoneNO = tblCustomer.fldPhoneNO)\n" +
                "INNER JOIN tblDeliveryPoint ON tblOrder.fldDeliveryPointID = tblDeliveryPoint.fldDeliveryPointID) where tblOrder.fldOrderNumber=" + orderNumber + ";");

    }

    /**
     * @param orderNumber OrderNumber from tblOrderStatus
     */
    public static void selectOrderFromOrderStatus(int orderNumber) {
        Database.selectSQL("select fldOrderNumber from tblOrderStatus where fldOrderNumber = " + orderNumber);
    }

    /**
     * @param orderNumber orderNumber from tableOrderHistory
     */
    public static void selectOrderFromOrderHistory(int orderNumber) {
        Database.selectSQL("select fldOrderNumber from tblOrderHistory where fldOrderNumber = " + orderNumber);

    }

    /**
     * @param employeeID employeeID to get the password for
     */
    public static void selectPasswordUserLogin(int employeeID) {
        Database.selectSQL("select fldPassword from tblUserLogin where fldEmployeeID=" + employeeID);

    }

    public static void selectTableClothes() {
        Database.selectSQL("SELECT * from tblClothes");
    }

    /**
     * @param orderNumber orderNumber to get the current WashOrders for
     */
    public static void selectCountWashOrder(int orderNumber) {
        Database.selectSQL("select count(*) from tblWashOrder where fldOrderNumber=" + orderNumber + "");
    }

    /**
     * @param orderNumber orderNumber to get current Progress in table order history
     */
    public static void selectMaxProgressIDOrderHistory(int orderNumber) {
        Database.selectSQL("select top 1 fldOrderProgressID from tblOrderHistory where fldOrderNumber=" + orderNumber + " order by fldOrderProgressID desc");
    }


}
