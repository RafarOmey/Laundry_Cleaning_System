package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class Controller {

    // TODO: 27-05-2020 change first letter to small 
    @FXML
    TextField tfCustomerName, tfOrderCustomerID, tfDeliveryPointID, tfCustomerPhoneNO, tfAddClothesOrderNumber, tfLabelOrderNumber;
    @FXML
    TextField tfCreateOrderUN, tfCreateOrderPW, tfConfirmOrderUN, tfConfirmOrderPW, tfConfirmON, tfLabelUN, tfLabelPW, tfCreateCustomerUN, tfCreateCustomerPW, tfOrderNumberSMS, tfCustomerOrderDoneUN, tfCustomerOrderDonePW;
    @FXML
    Button buttonCreateCustomer, buttonCreateCustomerTab, buttonCreateOrderTab, buttonConfirmOrderTab, buttonLabelTab, buttonGenLabel;
    @FXML
    AnchorPane paneLoginCreateOrder, paneLoginSMS, paneCreateCustomer, paneCreateOrder, paneConfirmOrder, paneLabel, paneSMSCustomer, paneLoginConfirmOrder, paneLoginGenerateLabel, paneLoginCreateCustomer;

    @FXML
    Label labelCustomerSuccess, labelOrderCreated, labelSuccess, labelOrderConfirmed;
    @FXML
    TableView<Cloth> tableViewProducts, tableViewBasket;

    @FXML
    TableColumn<Cloth, String> colClothType, colClothTypeBasket;
    @FXML
    TableColumn<Cloth, Integer> colClothID, colClothIDBasket;

    ObservableList<Cloth> itemsToBasket = FXCollections.observableArrayList();

    public void generateLabel() {
        Order order = new Order();

        order.setOrderNumber(Integer.parseInt(tfLabelOrderNumber.getText()));
        int orderNumber = order.getOrderNumber();

        order.generateLabel(orderNumber);
        order.changeLog(2, orderNumber, Integer.parseInt(tfLabelUN.getText()));

        labelSuccess.setText("Label Generated");
    }

    public void createCustomer() {

        Customer createNewCustomer = new Customer();
        createNewCustomer.setCustomerName(tfCustomerName.getText());
        createNewCustomer.setPhoneNO(Integer.parseInt(tfCustomerPhoneNO.getText()));


        createNewCustomer.createCustomer(createNewCustomer.getCustomerName(), createNewCustomer.getPhoneNO());


        labelCustomerSuccess.setText("Customer Created!");
        tfCustomerPhoneNO.clear();
        tfCustomerName.clear();
    }

    public void createOrder() {
        int customerID = Integer.parseInt(tfOrderCustomerID.getText());
        int deliveryPoint = Integer.parseInt(tfDeliveryPointID.getText());


        Order order = new Order();
        order.createOrder(customerID, deliveryPoint, Integer.parseInt(tfCreateOrderUN.getText()));

        tfAddClothesOrderNumber.setText(String.valueOf(order.getMaxOrderNumber()));
        order.changeLog(1, order.getMaxOrderNumber(), Integer.parseInt(tfCreateOrderUN.getText()));

        labelOrderCreated.setText("OrderNumber         Created");
        tfOrderCustomerID.clear();
        tfDeliveryPointID.clear();


        WashOrder washOrder = new WashOrder();
        washOrder.createWashOrder(itemsToBasket, tfAddClothesOrderNumber);


        tableViewBasket.getItems().clear();
        tfAddClothesOrderNumber.clear();
        labelOrderCreated.setText("");


    }


    public void confirmOrder() {
        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(tfConfirmON.getText()));
        order.setEmployeeID(Integer.parseInt(tfConfirmOrderUN.getText()));
        int employeeID = order.getEmployeeID();
        int orderNumber = order.getOrderNumber();
        order.confirmOrder(orderNumber, employeeID);
        order.changeLog(3, orderNumber, employeeID);
        labelOrderConfirmed.setText("Order Confirmed");
    }

    public void customerSMS() {

        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(tfOrderNumberSMS.getText()));
        order.setEmployeeID(Integer.parseInt(tfCustomerOrderDoneUN.getText()));
        int employeeID = order.getEmployeeID();
        int orderNumber = order.getOrderNumber();
        order.messageCustomer(orderNumber, employeeID);
        order.changeLog(4, orderNumber, employeeID);

    }

    public void addToBasket() {


        Cloth selection = tableViewProducts.getSelectionModel().getSelectedItem();


        itemsToBasket.addAll(new Cloth(selection.getClothID(), selection.getClothType()));


        colClothIDBasket.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        colClothTypeBasket.setCellValueFactory(new PropertyValueFactory<>("clothType"));


        tableViewBasket.setItems(itemsToBasket);

    }




    public void loginConfirmOrder() {

        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(tfConfirmOrderUN.getText()));
            login.setPassword(tfConfirmOrderPW.getText());
            login.userLoginDeliveryP(paneConfirmOrder, paneLoginConfirmOrder, login.getEmployeeID(), login.getPassword());
        } catch (Exception e) {
            System.out.println("Input EmployeeID");
        }

    }

    public void loginGenerateLabel() {
        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(tfLabelUN.getText()));
            login.setPassword(tfLabelPW.getText());
            login.userLoginCleaningP(paneLabel, paneLoginGenerateLabel, login.getEmployeeID(), login.getPassword());
        } catch (Exception e) {
            System.out.println("Input EmployeeID");
        }


    }

    public void loginCreateCustomer() {

        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(tfCreateCustomerUN.getText()));
            login.setPassword(tfCreateCustomerPW.getText());
            login.userLoginDeliveryP(paneCreateCustomer, paneLoginCreateCustomer, login.getEmployeeID(), login.getPassword());
        } catch (Exception e) {
            System.out.println("Input EmployeeID");
        }

    }

    public void loginCustomerSMS() {
        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(tfCustomerOrderDoneUN.getText()));
            login.setPassword(tfCustomerOrderDonePW.getText());
            login.userLoginDeliveryP(paneSMSCustomer, paneLoginSMS, login.getEmployeeID(), login.getPassword());
        } catch (Exception e) {
            System.out.println("Input EmployeeID");
        }

    }

    public void loginCreateOrder() {
        try {
            Login login = new Login();
            login.setEmployeeID(Integer.parseInt(tfCreateOrderUN.getText()));
            login.setPassword(tfCreateOrderPW.getText());
            login.userLoginDeliveryP(paneCreateOrder, paneLoginCreateOrder, login.getEmployeeID(), login.getPassword());

        } catch (Exception e) {
            System.out.println("Input EmployeeID");
        }

        // Populating Cloth tableview
        tableViewProducts.getItems().clear();

        ObservableList<Cloth> clothingList = new Cloth().populateProductTable();

        colClothID.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        colClothType.setCellValueFactory(new PropertyValueFactory<>("clothType"));

        tableViewProducts.setItems(clothingList);
    }


    // Switching tabs

    public void showCustomerTab() {
        tfCustomerName.clear();
        tfCustomerPhoneNO.clear();
        tfCreateCustomerUN.clear();
        tfCreateCustomerPW.clear();
        labelCustomerSuccess.setText("");

        paneCreateCustomer.setVisible(false);
        paneLoginCreateCustomer.setVisible(true);
        paneCreateOrder.setVisible(false);
        paneLoginCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLoginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneLoginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        paneLoginSMS.setVisible(false);

    }


    public void showOrderTab() {
        tfDeliveryPointID.clear();
        tfOrderCustomerID.clear();
        tfCreateOrderUN.clear();
        tfCreateOrderPW.clear();

        paneCreateCustomer.setVisible(false);
        paneLoginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneLoginCreateOrder.setVisible(true);
        paneConfirmOrder.setVisible(false);
        paneLoginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneLoginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        paneLoginSMS.setVisible(false);


    }


    public void showConfirmOrderTab() {
        tfConfirmON.clear();
        tfConfirmOrderUN.clear();
        tfConfirmOrderPW.clear();

        paneCreateCustomer.setVisible(false);
        paneLoginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneLoginCreateOrder.setVisible(false);
        paneLoginConfirmOrder.setVisible(true);
        paneSMSCustomer.setVisible(false);
        paneLoginGenerateLabel.setVisible(false);
        paneLoginSMS.setVisible(false);
        paneLabel.setVisible(false);
        paneConfirmOrder.setVisible(false);

        labelOrderConfirmed.setText("");
    }

    public void showLabelTab() {
        tfLabelOrderNumber.clear();
        tfLabelUN.clear();
        tfLabelPW.clear();

        paneCreateCustomer.setVisible(false);
        paneLoginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneLoginCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLoginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneLoginGenerateLabel.setVisible(true);
        paneSMSCustomer.setVisible(false);
        paneLoginSMS.setVisible(false);

        labelSuccess.setText("");
    }

    public void showSMSCustomerTab() {
        tfOrderNumberSMS.clear();
        tfCustomerOrderDoneUN.clear();
        tfCustomerOrderDonePW.clear();

        paneCreateCustomer.setVisible(false);
        paneLoginCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneLoginCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLoginConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneLoginGenerateLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        paneLoginSMS.setVisible(true);
    }


}
