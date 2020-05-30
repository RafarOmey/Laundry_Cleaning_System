package sample;


import Foundation.Database;
import javafx.scene.control.Label;


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


    public void generateLabel(int orderNumber, int employeeID, Label label) {




        String entry ="";




            int count1 = 1;
            int count2;
            Database.selectSQL("select count(*) from tblWashOrder where fldOrderNumber=" + orderNumber + "");
            count2 = Integer.parseInt(Database.getData());


            String clothName;
            int clothID;
            int orderNumber2;


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
            Database.executeStatement("update tblOrderStatus set fldOrderProgressID = 2, fldEmployeeID = " + employeeID + "where fldOrderNumber =" + orderNumber);

            label.setText("Label Generated");

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
     *
     * @param deliveryPoint this does this
     * @param employeeID    this is this
     */
    public void createOrder(int phoneNO, int deliveryPoint, int employeeID, Label label) {


        int phoneNumberCheck = 0;
        int deliveryPointCheck = 0;

        Database.selectSQL("select fldPhoneNO from tblCustomer where fldPhoneNO= " + phoneNO);

        String entry = Database.getData();
        if (!entry.equals("-ND-")) {
            phoneNumberCheck = Integer.parseInt(entry);
        }

        Database.selectSQL("SELECT fldDeliveryPointID FROM tblDeliveryPoint where fldDeliveryPointID =" + deliveryPoint);
        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            deliveryPointCheck = Integer.parseInt(entry);

        }

        if (phoneNumberCheck == phoneNO && deliveryPointCheck == deliveryPoint) {

            Database.executeStatement("USE ECO_Laundry_DB EXEC CreateOrder @deliveryPoint = " + deliveryPoint + ", @phoneNO = " + phoneNO);


            Database.executeStatement("insert into tblOrderStatus (fldEmployeeID, fldOrderNumber, fldOrderProgressID) values (" + employeeID + "," + getMaxOrderNumber() + ",1)");

            label.setText("OrderNumber: " + getMaxOrderNumber() + " Created");


            Database.selectSQL("select fldName from tblCustomer where fldPhoneNO =  " + phoneNO);

            String customerName = "";
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                customerName = (entry);

            }
            System.out.println("*Sending message to " + phoneNO + "*" + "\n Hello " + customerName + "! \n Your Order Has Been Created! " +
                    "\n OrderNumber: " + getMaxOrderNumber() + "\n Best regards Eco Solutions ");

        } else if (phoneNO != phoneNumberCheck) {
            label.setText("Customer Not in the System");


        } else if (deliveryPoint != deliveryPointCheck) {
            label.setText("Wrong Delivery Point");

        }
    }


    public void confirmOrder(int orderNumber, int employeeID) {
        Database.executeStatement("update  tblOrderStatus set fldEmployeeID =" + employeeID + " , fldOrderProgressID = 3 where fldOrderNumber=" + orderNumber);

        Database.executeStatement("delete from tblWashOrder where fldOrderNumber = " + orderNumber);
    }

    public void messageCustomer(int orderNumber, int employeeID) {
        Database.selectSQL("SELECT tblCustomer.fldName,tblCustomer.fldPhoneNO,tblOrder.fldOrderNumber, tblDeliveryPoint.fldDeliveryPointName\n" +
                "FROM ((tblOrder\n" +
                "INNER JOIN tblCustomer ON tblOrder.fldPhoneNO = tblCustomer.fldPhoneNO)\n" +
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

        Database.executeStatement("delete from tblOrderStatus where fldOrderNumber =" + orderNumber);
    }

    public void changeLog(int progressID, int orderNumber, int employeeID,Label label) {

        int largestProgressNumber=0;
        int progressNumberCheck1 = 0;
        int progressNumberCheck2 = 0;
        int progressNumberCheck3 = 0;
        int progressNumberCheck4 = 0;
        int orderNumberCheck = 0;
        String entry = "";


        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = "+orderNumber+" and fldOrderProgressID = 1" );

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressNumberCheck1 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = "+orderNumber+" and fldOrderProgressID = 2" );

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressNumberCheck2 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = "+orderNumber+" and fldOrderProgressID = 3" );

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressNumberCheck3 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = "+orderNumber+" and fldOrderProgressID = 4" );

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressNumberCheck4 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderNumber from tblOrderStatus where fldOrderNumber = " + orderNumber);

        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            orderNumberCheck = Integer.parseInt(entry);
        }
        if (progressNumberCheck1>largestProgressNumber){
            largestProgressNumber=progressNumberCheck1;
        }
        if (progressNumberCheck2>largestProgressNumber){
            largestProgressNumber=progressNumberCheck2;
        }
        if (progressNumberCheck3>largestProgressNumber){
            largestProgressNumber=progressNumberCheck3;
        }
        if (progressNumberCheck4>largestProgressNumber){
            largestProgressNumber=progressNumberCheck4;
        }

        if(orderNumberCheck==0){
            label.setText("Order number doesn't exist");
        }
        if (largestProgressNumber == 1 && orderNumber == orderNumberCheck&&progressID!=2) {
            label.setText("Order status: Confirmed for washing");

        }
         if (largestProgressNumber == 2 && orderNumber == orderNumberCheck&&progressID!=3) {
            label.setText("Order status: Being Washed");

        } else if (largestProgressNumber == 3 && orderNumber == orderNumberCheck&&progressID!=4) {
            label.setText("Order status: Confirmed and ready to be delivered back");

        }
         else if (largestProgressNumber == 4 ) {
             label.setText("Order status: Has been delivered back");

         }
        else if (progressID==1||largestProgressNumber == 1&&progressID==2||largestProgressNumber==2&&progressID==3||largestProgressNumber==3&&progressID==4) {
            Database.executeStatement("USE ECO_Laundry_DB\n" +
                    "\n" +
                    "EXEC ChangeLog @ProgressID = " + progressID + ", @OrderNumber=" + orderNumber + ",@EmployeeID=" + employeeID);
        }

    }
}








