package sample;


import Foundation.Database;



public class Order {


    int orderNumber;
    int employeeID;

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getOrderNumber() {
        return orderNumber;
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
        Database.executeStatement("update tblOrderStatus set fldOrderProgressID = 2 where fldOrderNumber ="+orderNumber);

    }

    public void createOrder(int customerID, int deliveryPoint) {


        Database.executeStatement("INSERT INTO tblOrder (fldCustomerID, fldDeliveryPointID,fldProgressID) values (" + customerID + "," + deliveryPoint + ",1)");



    }

    public void confirmOrder(int orderNumber,int employeeID){
        Database.executeStatement("INSERT INTO tblOrderStatus (fldEmployeeID,fldOrderNumber,fldOrderProgressID) values ("+ employeeID + ","+ orderNumber+",3)");
    }

    public void SMSCustomer(int orderNumber,int employeeID){
        Database.selectSQL("SELECT tblCustomer.fldName,tblCustomer.fldPhoneNO,tblOrder.fldOrderNumber, tblDeliveryPoint.fldDeliveryPointName\n" +
                "FROM ((tblOrder\n" +
                "INNER JOIN tblCustomer ON tblOrder.fldCustomerID = tblCustomer.fldCustomerID)\n" +
                "INNER JOIN tblDeliveryPoint ON tblOrder.fldDeliveryPointID = tblDeliveryPoint.fldDeliveryPointID) where tblOrder.fldOrderNumber="+ orderNumber+";");

        String customerName;
        int phoneNO;
        int orderNumber2;
        String deliveryPName;
        String entry;

        do{
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
            System.out.println("*Sending message to "+phoneNO+"*"+"\n Hello "+ customerName+"! \n Your clothes are ready " +
                    "to be picked up at: "+ deliveryPName+"\n Ordernumber: "+orderNumber2+"\n Best regards Eco Solutions ");
        }while(true);
        Database.executeStatement("Update tblOrderStatus set fldOrderProgressID=4,fldEmployeeID=" + employeeID + " where fldOrderNumber="+orderNumber);

    }


}








