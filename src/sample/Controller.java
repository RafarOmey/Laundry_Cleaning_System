package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Controller {
    @FXML
    TextField textCustomerName, orderCustomerID, deliveryPointID, textCustomerPhoneNO, textCustomerMail;
    @FXML
    Button buttonCreateCustomer;


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
}
