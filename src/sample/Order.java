package sample;


import Foundation.Database;


public class Order {


    int orderNumber;
    int employeeID;

    int maxOrderNumber;

    public int getEmployeeID() {
        return employeeID;
    }


    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }


    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }


    // Generate label method


    public void generateLabel(int orderNumber) {


        int count1 = 1;
        int count2;
        Database.selectSQL("select count(*) from tblWashOrder where fldOrderNumber=" + orderNumber + "");
        count2 = Integer.parseInt(Database.getData());


        String clothName;
        int clothID;
        int orderNumber2;
        String entry;

        Database.selectSQL(" SELECT tblClothes.fldTypeOfCloth, tblWashOrder.fldClothID, tblWashOrder.fldOrderNumber FROM tblClothes" +
                " INNER JOIN tblWashOrder ON tblClothes.fldClothID = tblWashOrder.fldClothID where fldOrderNumber =" + orderNumber + " ");
        do {


            entry = Database.getData();

            if (!entry.equals("-ND-")) {
                clothName = entry;
            } else {
                break;
            }
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothID = Integer.parseInt(entry);
            } else {
                break;
            }
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                orderNumber2 = Integer.parseInt(entry);
            } else {
                break;
            }


            System.out.println(count1 + " of " + count2 + " clothID= " + clothID + " OrderNumber= " + orderNumber2 + " ClothName= " + clothName);
            count1++;


        } while (true);

        // Updates the order progress in the database when a label is generated
        Database.executeStatement("update tblOrderStatus set fldOrderProgressID = 2 where fldOrderNumber =" + orderNumber);


    }


    public int getOrderNumber() {


        return orderNumber;

    }

    public int getMaxOrderNumber() {
        Database.selectSQL("select max (fldOrderNumber) from tblOrder");

        maxOrderNumber = Integer.parseInt(Database.getData());

        return maxOrderNumber;
    }

    /**
     * this method do this and it has to have this
     * @param customerID this gets this
     * @param deliveryPoint this does this
     * @param employeeID this is this
     */
    public void createOrder(int customerID, int deliveryPoint, int employeeID) {


        Database.executeStatement("INSERT INTO tblOrder (fldCustomerID, fldDeliveryPointID) values (" + customerID + "," + deliveryPoint + ")");


        // TODO: 24-05-2020  remember to change employee ID
        Database.executeStatement("insert into tblOrderStatus (fldEmployeeID, fldOrderNumber, fldOrderProgressID) values (" + employeeID + "," + getMaxOrderNumber() + ",1)");



    }

    public void confirmOrder(int orderNumber, int employeeID) {
        Database.executeStatement("INSERT INTO tblOrderStatus (fldEmployeeID,fldOrderNumber,fldOrderProgressID) values (" + employeeID + "," + orderNumber + ",3)");
    }

    public void SMSCustomer(int orderNumber, int employeeID) {
        Database.selectSQL("SELECT tblCustomer.fldName,tblCustomer.fldPhoneNO,tblOrder.fldOrderNumber, tblDeliveryPoint.fldDeliveryPointName\n" +
                "FROM ((tblOrder\n" +
                "INNER JOIN tblCustomer ON tblOrder.fldCustomerID = tblCustomer.fldCustomerID)\n" +
                "INNER JOIN tblDeliveryPoint ON tblOrder.fldDeliveryPointID = tblDeliveryPoint.fldDeliveryPointID) where tblOrder.fldOrderNumber=" + orderNumber + ";");


        String customerName;
        int phoneNO;
        int orderNumber2;
        String deliveryPName;
        String entry;

        do {
            entry = Database.getData();

            if (!entry.equals("-ND-")) {
                customerName = entry;
            } else {
                break;
            }
            entry = Database.getData();

            if (!entry.equals("-ND-")) {
                phoneNO = Integer.parseInt(entry);
            } else {
                break;
            }
            entry = Database.getData();

            if (!entry.equals("-ND-")) {
                orderNumber2 = Integer.parseInt(entry);
            } else {
                break;
            }
            entry = Database.getData();

            if (!entry.equals("-ND-")) {
                deliveryPName = entry;
            } else {
                break;
            }
            System.out.println("*Sending message to " + phoneNO + "*" + "\n Hello " + customerName + "! \n Your clothes are ready " +
                    "to be picked up at: " + deliveryPName + "\n Ordernumber: " + orderNumber2 + "\n Best regards Eco Solutions ");
        } while (true);
        Database.executeStatement("Update tblOrderStatus set fldOrderProgressID=4,fldEmployeeID=" + employeeID + " where fldOrderNumber=" + orderNumber);

        Database.executeStatement("delete from tblOrderStatus where fldOrderNumber ="+ orderNumber);
    }

    public void changeLog(int progressID, int orderNumber, int employeeID){
        Database.executeStatement("USE ECO_Laundry_DB\n" +
                "\n" +
                "EXEC ChangeLog @ProgressID = "+progressID+", @OrderNumber="+orderNumber+",@EmployeeID="+employeeID);
    }


}








