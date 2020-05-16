package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Controller {
    @FXML TextField textCustomerName, textCustomerPhoneNO, textCustomerMail;
    @FXML Button buttonCreateCustomer;


    public void createCustomer(){
        Customer createNewCustomer = new Customer();
        String customerName = (textCustomerName.getText());
        String mail = (textCustomerMail.getText());
        int phoneNO = Integer.parseInt(textCustomerPhoneNO.getText());


        createNewCustomer.insertTnToCustomer(customerName, mail,phoneNO);


    }


}
