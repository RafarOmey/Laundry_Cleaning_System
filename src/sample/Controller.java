package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.InvocationTargetException;


public class Controller {


    @FXML
    TextField textCustomerName, orderCustomerID, deliveryPointID, textCustomerPhoneNO, addClothesOrderNumber, labelOrderNumber;
    @FXML
    TextField createOrderUN, createOrderPW, confirmOrderUN, confirmOrderPW, confirmON, labelUN, labelPW, createCustomerUN, createCustomerPW, orderNumberSMS, customerOrderDoneUN, customerOrderDonePW;
    @FXML
    Button buttonCreateCustomer, createCustomerTab, createOrderTab, confirmOrderTab, labelTab, genLabel;
    @FXML
    AnchorPane loginCreateOrder, loginSMS, paneCreateCustomer, paneCreateOrder, paneConfirmOrder, paneLabel, paneSMSCustomer, loginConfirmOrder, loginGenerateLabel, loginCreateCustomer;


    @FXML
    TableView<Cloth> tableViewProducts, tableViewBasket;

    @FXML
    TableColumn<Cloth, String> ColClothType, ColClothTypeBasket;
    @FXML
    TableColumn<Cloth, Integer> ColClothID, ColClothIDBasket;

    ObservableList<Cloth> itemsToBasket = FXCollections.observableArrayList();

    public void generateLabel() {
        Order order = new Order();

        order.setOrderNumber(Integer.parseInt(labelOrderNumber.getText()));
        int orderNumber = order.getOrderNumber();

        order.generateLabel(orderNumber);
        order.changeLog(2, orderNumber, Integer.parseInt(labelUN.getText()));
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

        addClothesOrderNumber.setText(String.valueOf(order.getMaxOrderNumber()));


    }

    public void confirmOrder() {
        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(confirmON.getText()));
        order.setEmployeeID(Integer.parseInt(confirmOrderUN.getText()));
        int employeeID = order.getEmployeeID();
        int orderNumber = order.getOrderNumber();
        order.confirmOrder(orderNumber, employeeID);
        order.changeLog(3, orderNumber, employeeID);
    }

    public void customerSMS() {

        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(orderNumberSMS.getText()));
        order.setEmployeeID(Integer.parseInt(customerOrderDoneUN.getText()));
        int employeeID = order.getEmployeeID();
        int orderNumber = order.getOrderNumber();
        order.SMSCustomer(orderNumber, employeeID);
        order.changeLog(4, orderNumber, employeeID);

    }

    public void addToBasket() {


        Cloth selection = tableViewProducts.getSelectionModel().getSelectedItem();


        itemsToBasket.addAll(new Cloth(selection.getClothID(), selection.getClothType()));


        ColClothIDBasket.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        ColClothTypeBasket.setCellValueFactory(new PropertyValueFactory<>("clothType"));


        tableViewBasket.setItems(itemsToBasket);

    }


    public void createWasherOrder() {


        WashOrder washOrder = new WashOrder();
        washOrder.createWashOrder(itemsToBasket, addClothesOrderNumber);


        tableViewBasket.getItems().clear();
        addClothesOrderNumber.clear();
    }

    public void loginConfirmOrder() {

        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(confirmOrderUN.getText()));
            login.setPassword(confirmOrderPW.getText());
            login.userLoginDeliveryP(paneConfirmOrder, loginConfirmOrder, login.getEmployeeID(), login.getPassword());
        }catch(Exception e){
            System.out.println("Input EmployeeID");
        }
    }

    public void loginGenerateLabel() {
        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(labelUN.getText()));
            login.setPassword(labelPW.getText());
            login.userLoginCleaningP(paneLabel, loginGenerateLabel, login.getEmployeeID(), login.getPassword());
        }catch(Exception e){
            System.out.println("Input EmployeeID");
        }


    }

    public void loginCreateCustomer() {

        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(createCustomerUN.getText()));
            login.setPassword(createCustomerPW.getText());
            login.userLoginDeliveryP(paneCreateCustomer, loginCreateCustomer, login.getEmployeeID(), login.getPassword());
        }catch(Exception e){
            System.out.println("Input EmployeeID");
        }

    }

    public void loginCustomerSMS() {
        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(customerOrderDoneUN.getText()));
            login.setPassword(customerOrderDonePW.getText());
            login.userLoginDeliveryP(paneSMSCustomer, loginSMS, login.getEmployeeID(), login.getPassword());
        }catch(Exception e){
            System.out.println("Input EmployeeID");
        }

    }

    public void loginCreateOrder() {
        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(createOrderUN.getText()));
            login.setPassword(createOrderPW.getText());
            login.userLoginDeliveryP(paneCreateOrder, loginCreateOrder, login.getEmployeeID(), login.getPassword());

        }catch(Exception e){
            System.out.println("Input EmployeeID");
        }

        // Populating Cloth tableview
        tableViewProducts.getItems().clear();

        ObservableList<Cloth> clothingList = new Cloth().populateProductTable();

        ColClothID.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        ColClothType.setCellValueFactory(new PropertyValueFactory<>("clothType"));

        tableViewProducts.setItems(clothingList);
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
        loginCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        loginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        loginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        loginSMS.setVisible(false);

    }


    public void showOrderTab() {
        deliveryPointID.clear();
        orderCustomerID.clear();
        createOrderUN.clear();
        createOrderPW.clear();
        paneCreateCustomer.setVisible(false);
        loginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        loginCreateOrder.setVisible(true);
        paneConfirmOrder.setVisible(false);
        loginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        loginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        loginSMS.setVisible(false);


    }


    public void showConfirmOrderTab() {
        confirmON.clear();
        confirmOrderUN.clear();
        confirmOrderPW.clear();

        paneCreateCustomer.setVisible(false);
        loginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        loginCreateOrder.setVisible(false);
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
        loginCreateOrder.setVisible(false);
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
        loginCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        loginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        loginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        loginSMS.setVisible(true);
    }


}
