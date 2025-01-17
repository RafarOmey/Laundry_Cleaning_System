package Domain;


import Foundation.Database;
import javafx.scene.control.Label;
import tech.*;


public class Order {


    private int orderNumber;
    private int employeeID;
    private int phoneNO;
    private int deliveryPoint;
    private int progressID;


    public int getMaxOrderNumber() {
        return Select.getMaxOrder();
    }


    public void setProgressID(int progressID) {
        this.progressID = progressID;
    }

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

    public int getProgressID() {
        return progressID;
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
     * This will take the OrderNumber entered and loop through the table WashOrder and generate a wash label for all of the clothes.
     *
     * @param label is Label FX used to set a text to show status of the order, if it is going to be washed or show status.
     */
    public void generateLabel(Label label) {

        String entry = "";

        int count1 = 1;
        int count2;
        Select.selectCountWashOrder(getOrderNumber());
        count2 = Integer.parseInt(Database.getData());

        // These 4 variables (clothName, clothID, orderNumber2, progressIDCheck)  are pulled out of the Database so we can make washable Labels
        String clothName;
        int clothID;
        int orderNumber2;
        int progressIDCheck = 0;

        Select.selectMaxProgressIDOrderHistory(getOrderNumber());
        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck = Integer.parseInt(entry);
        }
        if (progressIDCheck == 1) {

            Select.selectInnerJoinTableClothesWashOrder(getOrderNumber());
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

            Update.updateOrderStatus(2, getEmployeeID(), getOrderNumber());

            label.setText("Label Generated");

        }


    }

    /**
     * We create or Order, and we validate if the customer and delivery points exists before creating.
     *
     * @param label Will be used to show system message of an order if it got created or wrong information have been inputted.
     */
    public void createOrder(Label label) {

        //Variables PhoneNumberCheck and DeliveryPointCheck are used to validate if the customer and delivery point exist.
        int phoneNumberCheck = 0;
        int deliveryPointCheck = 0;


        Select.selectPhoneNumberCustomer(getPhoneNO());

        String entry = Database.getData();
        if (!entry.equals("-ND-")) {
            phoneNumberCheck = Integer.parseInt(entry);
        }

        Select.selectDeliveryPointID(getDeliveryPoint());

        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            deliveryPointCheck = Integer.parseInt(entry);

        }


        if (deliveryPointCheck == getDeliveryPoint() && phoneNumberCheck == getPhoneNO()) {

            StoredP.storedCreateOrder(getDeliveryPoint(), getPhoneNO());


            Insert.insertToOrderStatus(getEmployeeID());


            label.setText("OrderNumber: " + Select.getMaxOrder() + " Created");


            Select.selectCustomerName(getPhoneNO());

            String customerName = "";
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                customerName = (entry);

            }
            System.out.println("*Sending message to " + getPhoneNO() + "*" + "\n Hello " + customerName + "! \n" +
                    " Your Order Has Been Created! " +
                    "\n OrderNumber: " + Select.getMaxOrder() + "\n Best regards Eco Solutions ");
            setOrderNumber(Select.getMaxOrder());


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
        String entry = "";
        int progressID = 0;

        Select.selectMaxProgressIDOrderStatus(getOrderNumber());

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressID = Integer.parseInt(entry);
        }

        if (progressID == 2) {
            Update.updateOrderStatus(3, getEmployeeID(), getOrderNumber());

        }
    }

    /**
     * It Messages the Customer with information of the Order, and it is ready to be picked up at the delivery station it got picked up in.
     */
    public void messageCustomer(Label label) {

        String customerName;
        int phoneNO;
        int orderNumber2;
        String deliveryPName;
        String entry;
        int progressID = 0;

        Select.selectMaxProgressIDOrderStatus(getOrderNumber());

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressID = Integer.parseInt(entry);
        }
        if (progressID == 3) {


            Select.selectInnerJoinCustomerOrderDeliveryPoint(getOrderNumber());


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

            Update.updateOrderStatus(4, getEmployeeID(), getOrderNumber());

            Delete.deleteFromOrderStatus(getOrderNumber());

            label.setText("Message sent!");
        }

    }

    /**
     * This method will execute the changelog Stored Procedure in the Database. But before it does that it will go in and validate the OrderNumber and see if it
     * is valid to be changed.
     *
     * @param label label to set responses texts
     */
    public void changeLog(Label label) {


        int largestProgressID = 0;
        int orderNumberCheckMessage = 0;
        int orderIDCheck = 0;
        String entry = "";


        Select.selectMaxProgressIDOrderHistory(getOrderNumber());

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            largestProgressID = Integer.parseInt(entry);
        }


        Select.selectOrderFromOrderStatus(getOrderNumber());

        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            orderIDCheck = Integer.parseInt(entry);
        }
        Select.selectOrderFromOrderHistory(getOrderNumber());

        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            orderNumberCheckMessage = Integer.parseInt(entry);
        }


        if (orderIDCheck == 0 && orderNumberCheckMessage == 0 && getProgressID() != 1) {
            label.setText("Order number doesn't exist");
        }
        if (largestProgressID == 1 && getOrderNumber() == orderIDCheck && getProgressID() != 2 && getProgressID() != 1) {
            label.setText("Order status: Created and waiting to be washed");

        }
        if (largestProgressID == 2 && getOrderNumber() == orderIDCheck && getProgressID() != 3 && getProgressID() != 1) {
            label.setText("Order status: Being Washed");

        } else if (largestProgressID == 3 && getOrderNumber() == orderIDCheck && getProgressID() != 4 && getProgressID() != 1) {
            label.setText("Order status: Confirmed and waiting to be delivered back");

        } else if (largestProgressID == 4 && getOrderNumber() == orderNumberCheckMessage && getProgressID() != 1) {
            label.setText("Order status: Has been delivered back");

        } else if (getProgressID() == 1 && orderNumberCheckMessage != Select.getMaxOrder() ||
                largestProgressID == 1 && getProgressID() == 2 || largestProgressID == 2 && getProgressID() == 3 ||
                largestProgressID == 3 && getProgressID() == 4) {

            StoredP.storedChangeLog(getProgressID(), getOrderNumber(), getEmployeeID());
        }

    }


}








