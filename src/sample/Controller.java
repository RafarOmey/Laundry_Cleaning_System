package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField orderCustomerID,deliveryPointID;





    public void createOrder(){
        int customerID = Integer.parseInt(orderCustomerID.getText());
        int deliveryPoint = Integer.parseInt(deliveryPointID.getText());


        Order order = new Order();
        order.createOrder(customerID,deliveryPoint);

    }
}
