package sample;


import Foundation.Database;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;


public class Order {


    private int orderNumber;
    private int employeeID;
    private int maxOrderNumber;
    private int phoneNO;
    private int deliveryPoint;


    public void setPhoneNO(int phoneNO) {
        this.phoneNO = phoneNO;
    }

    public void setDeliveryPoint(int deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }


    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getDeliveryPoint() {
        return deliveryPoint;
    }

    public int getOrderNumber() {


        return orderNumber;

    }

    public int getEmployeeID() {
        return employeeID;
    }

    public int getPhoneNO() {
        return phoneNO;
    }

    /**
     * @return Will return the highest OrderNumber this will be used to create wash order, that way we get the latest ordernumber that was just created.
     */
    public int getMaxOrderNumber() {
        Database.selectSQL("select max (fldOrderNumber) from tblOrder");

        maxOrderNumber = Integer.parseInt(Database.getData());

        return maxOrderNumber;
    }

    // Generate label method

    /**
     * This will take the OrderNumber entered and loop through the table WashOrder and generate a wash label for all of the clothes.
     *
     * @param label is Label FX used to set a text to show status of the order, if it is going to be washed or show status.
     */
    public void generateLabel(Label label) {

        String entry = "";

        int count1 = 1;
        int count2;
        Database.selectSQL("select count(*) from tblWashOrder where fldOrderNumber=" + getOrderNumber() + "");
        count2 = Integer.parseInt(Database.getData());

                        // These 4 variables (clothName, clothID, orderNumber2, progressIDCheck)  are pulled out of the Database so we can make washable Labels
        String clothName;
        int clothID;
        int orderNumber2;
        int progressIDCheck = 0;


        Database.selectSQL("select top 1 fldOrderProgressID from tblOrderHistory where fldOrderNumber=" + getOrderNumber() + " order by fldOrderProgressID desc");
        entry = Database.getData();


        if (!entry.equals("-ND-")) {
            progressIDCheck = Integer.parseInt(entry);
        }
        if (progressIDCheck == 1) {

            Database.selectSQL(" SELECT tblClothes.fldTypeOfCloth, tblWashOrder.fldClothID, tblWashOrder.fldOrderNumber FROM tblClothes" +
                    " INNER JOIN tblWashOrder ON tblClothes.fldClothID = tblWashOrder.fldClothID where fldOrderNumber =" + getOrderNumber() + " ");
            //This Do-While loop will get our 4 entries from the Database and system out print the washable Labels.
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
            Database.executeStatement("update tblOrderStatus set fldOrderProgressID = 2, fldEmployeeID = " + getEmployeeID() + "where fldOrderNumber =" + getOrderNumber());

            label.setText("Label Generated");

        }


    }

    /** We create or Order, and we validate if the customer and delivery points exists before creating.
     * @param label Will be used to show system message of an order if it got created or wrong information have been inputted.
     */
    public void createOrder(Label label) {

        //Variables PhoneNumberCheck and DeliveryPointCheck are used to validate if the customer and delivery point exist.
        int phoneNumberCheck = 0;
        int deliveryPointCheck = 0;

        Database.selectSQL("select fldPhoneNO from tblCustomer where fldPhoneNO= " + getPhoneNO());

        String entry = Database.getData();
        if (!entry.equals("-ND-")) {
            phoneNumberCheck = Integer.parseInt(entry);
        }

        Database.selectSQL("SELECT fldDeliveryPointID FROM tblDeliveryPoint where fldDeliveryPointID =" + getDeliveryPoint());
        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            deliveryPointCheck = Integer.parseInt(entry);

        }

        if (deliveryPointCheck == getDeliveryPoint() && phoneNumberCheck == getPhoneNO()) {

            Database.executeStatement("USE ECO_Laundry_DB EXEC CreateOrder @deliveryPoint = " + getDeliveryPoint() + ", @phoneNO = " + getPhoneNO());


            Database.executeStatement("insert into tblOrderStatus (fldEmployeeID, fldOrderNumber, fldOrderProgressID) values (" + getEmployeeID() + "," + getMaxOrderNumber() + ",1)");

            label.setText("OrderNumber: " + getMaxOrderNumber() + " Created");


            Database.selectSQL("select fldName from tblCustomer where fldPhoneNO =  " + getPhoneNO());

            String customerName = "";
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                customerName = (entry);

            }
            System.out.println("*Sending message to " + getPhoneNO() + "*" + "\n Hello " + customerName + "! \n Your Order Has Been Created! " +
                    "\n OrderNumber: " + getMaxOrderNumber() + "\n Best regards Eco Solutions ");

        } else if (getPhoneNO() != phoneNumberCheck) {
            label.setText("Customer Not in the System");


        } else {
            label.setText("Wrong Delivery Point");


        }
    }

    /**
     * Will Confirm the Order before the last stage of the Process so it can be delivered back to the customer. It goes into the Database and Changes the ProgressID.
     */
    public void confirmOrder() {
        Database.executeStatement("update  tblOrderStatus set fldEmployeeID =" + getEmployeeID() + " , fldOrderProgressID = 3 where fldOrderNumber=" + getOrderNumber());

        Database.executeStatement("delete from tblWashOrder where fldOrderNumber = " + getOrderNumber());
    }

    /**
     * It Messages the Customer with information of the Order, and it is ready to be picked up at the delivery station it got picked up in.
     */
    public void messageCustomer() {
        Database.selectSQL("SELECT tblCustomer.fldName,tblCustomer.fldPhoneNO,tblOrder.fldOrderNumber, tblDeliveryPoint.fldDeliveryPointName\n" +
                "FROM ((tblOrder\n" +
                "INNER JOIN tblCustomer ON tblOrder.fldPhoneNO = tblCustomer.fldPhoneNO)\n" +
                "INNER JOIN tblDeliveryPoint ON tblOrder.fldDeliveryPointID = tblDeliveryPoint.fldDeliveryPointID) where tblOrder.fldOrderNumber=" + getOrderNumber() + ";");


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
        Database.executeStatement("Update tblOrderStatus set fldOrderProgressID=4,fldEmployeeID=" + getEmployeeID() + " where fldOrderNumber=" + getEmployeeID());

        Database.executeStatement("delete from tblOrderStatus where fldOrderNumber =" + getOrderNumber());
    }

    /**
     * This method will execute the changelog Stored Procedure in the Database. But before it does that it will go in and validate the OrderNumber and see if it
     * is valid to be changed.
     * @param progressID
     * @param label
     */
    public void changeLog(int progressID, Label label) {

        int largestProgressID = 0;
        int orderNumberCheckMessage = 0;
        int progressIDCheck1 = 0;
        int progressIDCheck2 = 0;
        int progressIDCheck3 = 0;
        int progressIDCheck4 = 0;
        int orderIDCheck = 0;
        String entry = "";
        int test = getMaxOrderNumber();


        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = " + getMaxOrderNumber() + " and fldOrderProgressID = 1");

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck1 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = " + getMaxOrderNumber() + " and fldOrderProgressID = 2");

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck2 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = " + getMaxOrderNumber() + " and fldOrderProgressID = 3");

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck3 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = " + getMaxOrderNumber() + " and fldOrderProgressID = 4");

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck4 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderNumber from tblOrderStatus where fldOrderNumber = " + getMaxOrderNumber());

        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            orderIDCheck = Integer.parseInt(entry);
        }
        Database.selectSQL("select fldOrderNumber from tblOrderHistory where fldOrderNumber = " + getMaxOrderNumber());

        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            orderNumberCheckMessage = Integer.parseInt(entry);
        }
        if (progressIDCheck1 > largestProgressID) {
            largestProgressID = progressIDCheck1;
        }
        if (progressIDCheck2 > largestProgressID) {
            largestProgressID = progressIDCheck2;
        }
        if (progressIDCheck3 > largestProgressID) {
            largestProgressID = progressIDCheck3;
        }
        if (progressIDCheck4 > largestProgressID) {
            largestProgressID = progressIDCheck4;
        }

        if (orderIDCheck == 0 && orderNumberCheckMessage == 0) {
            label.setText("Order number doesn't exist");
        }
        if (largestProgressID == 1 && getOrderNumber() == orderIDCheck && progressID != 2) {
            label.setText("Order status: Confirmed and waiting to be washed");

        }
        if (largestProgressID == 2 && getOrderNumber() == orderIDCheck && progressID != 3) {
            label.setText("Order status: Being Washed");

        } else if (largestProgressID == 3 && getOrderNumber() == orderIDCheck && progressID != 4) {
            label.setText("Order status: Confirmed and ready to be delivered back");

        } else if (largestProgressID == 4) {
            label.setText("Order status: Has been delivered back");

        } else if (progressID == 1 && orderNumberCheckMessage != test || largestProgressID == 1 && progressID == 2 || largestProgressID == 2 && progressID == 3 || largestProgressID == 3 && progressID == 4) {
            Database.executeStatement("USE ECO_Laundry_DB\n" +
                    "\n" +
                    "EXEC ChangeLog @ProgressID = " + progressID + ", @OrderNumber=" + getMaxOrderNumber() + ",@EmployeeID=" + getEmployeeID());
        }

    }


}








