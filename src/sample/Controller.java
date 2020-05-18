package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class Controller {
    @FXML
    TextField textCustomerName, orderCustomerID, deliveryPointID, textCustomerPhoneNO, textCustomerMail,addClothesOrderNumber,labelOrderNumber;
    @FXML
    Button buttonCreateCustomer,createCustomerTab, createOrderTab, confirmOrderTab, labelTab,addJacket,addShirt,addJeans,genLabel;
    @FXML
    AnchorPane paneCreateCustomer, paneCreateOrder, paneConfirmOrder, paneLabel;









    public void generateLabel(){
        Order order = new Order();
        int orderNumber = Integer.parseInt(labelOrderNumber.getText());
        order.generateLabel(orderNumber);
    }

    public void createCustomer() {
        Customer createNewCustomer = new Customer();
        String customerName = (textCustomerName.getText());
        String mail = (textCustomerMail.getText());
        int phoneNO = Integer.parseInt(textCustomerPhoneNO.getText());


        createNewCustomer.insertTnToCustomer(customerName, mail, phoneNO);

    }


    public void createOrder() {
        int customerID = Integer.parseInt(orderCustomerID.getText());
        int deliveryPoint = Integer.parseInt(deliveryPointID.getText());


        Order order = new Order();
        order.createOrder(customerID, deliveryPoint);

    }



    // Switching tabs

    public void showCustomerTab(){
        paneCreateCustomer.setVisible(true);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
    }
    public void showOrderTab(){
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(true);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
    }
    public void showConfirmOrderTab(){
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(true);
        paneLabel.setVisible(false);
    }
    public void showLabelTab(){
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(true);
    }



}
