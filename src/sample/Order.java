package sample;


import Foundation.Database;
import javafx.collections.ObservableList;
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
     *
     */
    public void createOrder(int phoneNO, int deliveryPoint, Label label) {


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

        if (deliveryPointCheck == deliveryPoint && phoneNumberCheck == phoneNO ) {

            Database.executeStatement("USE ECO_Laundry_DB EXEC CreateOrder @deliveryPoint = " + deliveryPoint + ", @phoneNO = " + phoneNO);


            Database.executeStatement("insert into tblOrderStatus (fldEmployeeID, fldOrderNumber, fldOrderProgressID) values (" + getEmployeeID() + "," + getMaxOrderNumber() + ",1)");

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


        } else {
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

    public void changeLog(int progressID, int orderNumber,Label label) {

        int largestProgressID=0;
        int orderNumberCheckMessage=0;
        int progressIDCheck1 = 0;
        int progressIDCheck2 = 0;
        int progressIDCheck3 = 0;
        int progressIDCheck4 = 0;
        int orderIDCheck = 0;
        String entry = "";
        int test=getMaxOrderNumber();


        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = "+orderNumber+" and fldOrderProgressID = 1" );

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck1 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = "+orderNumber+" and fldOrderProgressID = 2" );

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck2 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = "+orderNumber+" and fldOrderProgressID = 3" );

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck3 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderProgressID from tblOrderHistory where fldOrderNumber = "+orderNumber+" and fldOrderProgressID = 4" );

        entry = Database.getData();

        if (!entry.equals("-ND-")) {
            progressIDCheck4 = Integer.parseInt(entry);
        }

        Database.selectSQL("select fldOrderNumber from tblOrderStatus where fldOrderNumber = " + orderNumber);

        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            orderIDCheck = Integer.parseInt(entry);
        }
        Database.selectSQL("select fldOrderNumber from tblOrderHistory where fldOrderNumber = " + orderNumber);

        entry = Database.getData();
        if (!entry.equals("-ND-")) {
            orderNumberCheckMessage = Integer.parseInt(entry);
        }
        if (progressIDCheck1>largestProgressID){
            largestProgressID=progressIDCheck1;
        }
        if (progressIDCheck2>largestProgressID){
            largestProgressID=progressIDCheck2;
        }
        if (progressIDCheck3>largestProgressID){
            largestProgressID=progressIDCheck3;
        }
        if (progressIDCheck4>largestProgressID){
            largestProgressID=progressIDCheck4;
        }

        if(orderIDCheck==0&&orderNumberCheckMessage==0){
            label.setText("Order number doesn't exist");
        }
        if (largestProgressID == 1 && orderNumber == orderIDCheck&&progressID!=2) {
            label.setText("Order status: Confirmed and waiting to be washed");

        }
         if (largestProgressID == 2 && orderNumber == orderIDCheck&&progressID!=3) {
            label.setText("Order status: Being Washed");

        } else if (largestProgressID == 3 && orderNumber == orderIDCheck&&progressID!=4) {
            label.setText("Order status: Confirmed and ready to be delivered back");

        }
         else if (largestProgressID == 4 ) {
             label.setText("Order status: Has been delivered back");

         }
        else if (progressID==1&& orderNumberCheckMessage!=test||largestProgressID == 1&&progressID==2||largestProgressID==2&&progressID==3||largestProgressID==3&&progressID==4) {
            Database.executeStatement("USE ECO_Laundry_DB\n" +
                    "\n" +
                    "EXEC ChangeLog @ProgressID = " + progressID + ", @OrderNumber=" + orderNumber + ",@EmployeeID=" +getEmployeeID());
        }

    }




}








