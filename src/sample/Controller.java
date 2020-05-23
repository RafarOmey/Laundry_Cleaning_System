package sample;

import Foundation.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class Controller {


    @FXML
    TextField textCustomerName, orderCustomerID, deliveryPointID, textCustomerPhoneNO, addClothesOrderNumber, labelOrderNumber;
    @FXML
    TextField confirmOrderUN,confirmOrderPW, confirmON,labelUN,labelPW, createCustomerUN, createCustomerPW,orderNumberSMS,customerOrderDoneUN,customerOrderDonePW;
    @FXML
    Button buttonCreateCustomer, createCustomerTab, createOrderTab, confirmOrderTab, labelTab, genLabel;
    @FXML
    AnchorPane loginSMS, paneCreateCustomer, paneCreateOrder, paneConfirmOrder, paneLabel, paneSMSCustomer,loginConfirmOrder,loginGenerateLabel,loginCreateCustomer;

    @FXML
    ListView listViewBasket;

    @FXML
    TableView<Cloth> tableViewProducts;

    @FXML
    TableColumn<Cloth, String> ColClothType;
    @FXML
    TableColumn<Cloth, Integer> ColClothID;


    ObservableList<Cloth> clothingList = FXCollections.observableArrayList();


    public void generateLabel() {
        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(labelOrderNumber.getText()));

        order.generateLabel(order.getOrderNumber());
    }

    public void createCustomer() {

        Customer createNewCustomer = new Customer();
        createNewCustomer.setCustomerName(textCustomerName.getText());
        createNewCustomer.setPhoneNO(Integer.parseInt(textCustomerPhoneNO.getText()));




        createNewCustomer.createCustomer(createNewCustomer.getCustomerName(), createNewCustomer.getPhoneNO());

    }

    public void createOrder() {
        int customerID = Integer.parseInt(orderCustomerID.getText());
        int deliveryPoint = Integer.parseInt(deliveryPointID.getText());


        Order order = new Order();
        order.createOrder(customerID, deliveryPoint);


    }

    public void confirmOrder(){
        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(confirmON.getText()));
        order.setEmployeeID(Integer.parseInt(confirmOrderUN.getText()));
        order.confirmOrder(order.getOrderNumber(),order.getEmployeeID());
    }

    public void customerSMS(){

        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(orderNumberSMS.getText()));
        order.setEmployeeID(Integer.parseInt(customerOrderDoneUN.getText()));
        order.SMSCustomer(order.getOrderNumber(),order.getEmployeeID());

    }

    public void loginConfirmOrder(){
        Login login = new Login();
        login.setEmployeeID(Integer.parseInt(confirmOrderUN.getText()));
        login.setPassword(confirmOrderPW.getText());
        login.userLoginDeliveryP(paneConfirmOrder,loginConfirmOrder, login.getEmployeeID(),login.getPassword());
    }

    public void loginGenerateLabel(){
        Login login = new Login();
        login.setEmployeeID(Integer.parseInt(labelUN.getText()));
        login.setPassword(labelPW.getText());
        login.userLoginCleaningP(paneLabel,loginGenerateLabel,login.getEmployeeID(),login.getPassword());

    }

    public void loginCreateCustomer(){

        Login login = new Login();
        login.setEmployeeID(Integer.parseInt(createCustomerUN.getText()));
        login.setPassword(createCustomerPW.getText());
        login.userLoginDeliveryP(paneCreateCustomer,loginCreateCustomer, login.getEmployeeID(),login.getPassword());
    }
    public void loginCustomerSMS(){
        Login login = new Login();
        login.setEmployeeID(Integer.parseInt(customerOrderDoneUN.getText()));
        login.setPassword(customerOrderDonePW.getText());
        login.userLoginDeliveryP(paneSMSCustomer,loginSMS, login.getEmployeeID(),login.getPassword());
    }


    // Switching tabs

    public void showCustomerTab() {
        textCustomerName.clear();
        textCustomerPhoneNO.clear();
        createCustomerUN.clear();
        createCustomerPW.clear();

        paneCreateCustomer.setVisible(false);
        loginCreateCustomer.setVisible(true);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        loginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        loginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        loginSMS.setVisible(false);

    }

    public void showOrderTab() {
        paneCreateCustomer.setVisible(false);
        loginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(true);
        paneConfirmOrder.setVisible(false);
        loginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        loginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        loginSMS.setVisible(false);
        tableViewProducts.getItems().clear();


        Database.selectSQL("SELECT * from tblClothes");

        int clothID;
        String clothType;
        String entry;

        do {


            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothID = Integer.parseInt(entry);
            } else {
                break;
            }

            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothType = entry;
            } else {
                break;
            }


            clothingList.add(new Cloth(clothID, clothType));
        } while (true);


        ColClothID.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        ColClothType.setCellValueFactory(new PropertyValueFactory<>("clothType"));

        tableViewProducts.setItems(clothingList);


    }

    public void showConfirmOrderTab() {
        confirmON.clear();
        confirmOrderUN.clear();
        confirmOrderPW.clear();

        paneCreateCustomer.setVisible(false);
        loginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        loginConfirmOrder.setVisible(true);
        paneSMSCustomer.setVisible(false);
        loginSMS.setVisible(false);
        paneLabel.setVisible(false);
    }

    public void showLabelTab() {
        labelOrderNumber.clear();
        labelUN.clear();
        labelPW.clear();

        paneCreateCustomer.setVisible(false);
        loginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        loginConfirmOrder.setVisible(false);
        loginGenerateLabel.setVisible(true);
        paneSMSCustomer.setVisible(false);
        loginSMS.setVisible(false);
    }

    public void showSMSCustomerTab() {
        orderNumberSMS.clear();
        customerOrderDoneUN.clear();
        customerOrderDonePW.clear();

        paneCreateCustomer.setVisible(false);
        loginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        loginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        loginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        loginSMS.setVisible(true);
    }


}
