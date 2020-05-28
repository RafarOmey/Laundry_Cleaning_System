package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class Controller {

    @FXML
    TextField tfUN, tfCustomerName, tfCreateOrderPhoneNO, tfDeliveryPointID, tfCustomerPhoneNO, tfAddClothesOrderNumber, tfLabelOrderNumber;
    @FXML
    TextField tfConfirmON, tfOrderNumberSMS;
    @FXML
    PasswordField pwPassword;
    @FXML
    Button buttonLogOut, buttonMessageCustomerTab, buttonCreateCustomer, buttonCreateCustomerTab, buttonCreateOrderTab, buttonConfirmOrderTab, buttonGenerateLabelTab, buttonGenLabel;
    @FXML
    AnchorPane paneLoginScreen, paneCreateCustomer, paneCreateOrder, paneConfirmOrder, paneLabel, paneSMSCustomer;
    @FXML
    Label labelMessage,labelLoggedInAs,labelCustomerSuccess, labelOrderCreated, labelSuccess, labelOrderConfirmed;
    @FXML
    TableView<Cloth> tableViewProducts, tableViewBasket;

    @FXML
    TableColumn<Cloth, String> colClothType, colClothTypeBasket;
    @FXML
    TableColumn<Cloth, Integer> colClothID, colClothIDBasket ,colClothPrice,colClothPriceBasket;

    ObservableList<Cloth> itemsToBasket = FXCollections.observableArrayList();

    public void generateLabel() {
        Order order = new Order();

        order.setOrderNumber(Integer.parseInt(tfLabelOrderNumber.getText()));
        int orderNumber = order.getOrderNumber();

        order.generateLabel(orderNumber, Integer.parseInt(tfUN.getText()));
        order.changeLog(2, orderNumber, Integer.parseInt(tfUN.getText()));

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
        int phoneNO = Integer.parseInt(tfCreateOrderPhoneNO.getText());
        int deliveryPoint = Integer.parseInt(tfDeliveryPointID.getText());


        Order order = new Order();
        order.createOrder(phoneNO, deliveryPoint, Integer.parseInt(tfUN.getText()));

        tfAddClothesOrderNumber.setText(String.valueOf(order.getMaxOrderNumber()));
        order.changeLog(1, order.getMaxOrderNumber(), Integer.parseInt(tfUN.getText()));

        labelOrderCreated.setText("OrderNumber Created" + order.getMaxOrderNumber());
        tfCreateOrderPhoneNO.clear();
        tfDeliveryPointID.clear();


        WashOrder washOrder = new WashOrder();
        washOrder.createWashOrder(itemsToBasket, tfAddClothesOrderNumber);

      washOrder.insertTotalPrice(itemsToBasket, tfAddClothesOrderNumber);

        tableViewBasket.getItems().clear();
        tfAddClothesOrderNumber.clear();



    }


    public void confirmOrder() {
        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(tfConfirmON.getText()));
        order.setEmployeeID(Integer.parseInt(tfUN.getText()));
        int employeeID = order.getEmployeeID();
        int orderNumber = order.getOrderNumber();
        order.confirmOrder(orderNumber, employeeID);
        order.changeLog(3, orderNumber, employeeID);
        labelOrderConfirmed.setText("Order Confirmed");
    }

    public void customerSMS() {

        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(tfOrderNumberSMS.getText()));
        order.setEmployeeID(Integer.parseInt(tfUN.getText()));
        int employeeID = order.getEmployeeID();
        int orderNumber = order.getOrderNumber();
        order.messageCustomer(orderNumber, employeeID);
        order.changeLog(4, orderNumber, employeeID);
        labelMessage.setVisible(true);
        labelMessage.setText("Message sent!");

    }

    public void addToBasket() {


        Cloth selection = tableViewProducts.getSelectionModel().getSelectedItem();


        itemsToBasket.addAll(new Cloth(selection.getClothID(), selection.getClothType(), selection.getClothPrice()));





        colClothIDBasket.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        colClothTypeBasket.setCellValueFactory(new PropertyValueFactory<>("clothType"));
        colClothPriceBasket.setCellValueFactory(new PropertyValueFactory<>("ClothPrice"));


        tableViewBasket.setItems(itemsToBasket);

    }


    public void loginScreen() {

        try {

            Login login = new Login();


            login.setEmployeeID(Integer.parseInt(tfUN.getText()));

            String password = pwPassword.getText();

            String employeeName=login.getEmployeeName();

            int jobID = login.getJobID();

            if (jobID == 2 && password.equals(login.getPassword())) {
                paneLoginScreen.setVisible(false);
                buttonGenerateLabelTab.setVisible(true);
                buttonLogOut.setVisible(true);
                labelLoggedInAs.setVisible(true);
                labelLoggedInAs.setText("Logged in as "+employeeName);

            } else if (jobID == 1&& password.equals(login.getPassword())) {
                paneLoginScreen.setVisible(false);
                buttonCreateCustomerTab.setVisible(true);
                buttonCreateOrderTab.setVisible(true);
                buttonMessageCustomerTab.setVisible(true);
                buttonConfirmOrderTab.setVisible(true);
                buttonLogOut.setVisible(true);
                labelLoggedInAs.setVisible(true);
                labelLoggedInAs.setText("Logged in as "+employeeName);


            }
        } catch (Exception e) {
            System.out.println("Input EmployeeID");
        }


    }

    public void logOut() {
        tfUN.clear();
        pwPassword.clear();

        paneLoginScreen.setVisible(true);
        buttonCreateCustomerTab.setVisible(false);
        buttonCreateOrderTab.setVisible(false);
        buttonMessageCustomerTab.setVisible(false);
        buttonConfirmOrderTab.setVisible(false);
        buttonGenerateLabelTab.setVisible(false);
        paneSMSCustomer.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneLabel.setVisible(false);
        buttonLogOut.setVisible(false);

        labelMessage.setText("");
        labelMessage.setVisible(false);
        labelLoggedInAs.setText("");
        labelLoggedInAs.setVisible(false);
    }


    // Switching tabs

    public void showCustomerTab() {
        tfCustomerName.clear();
        tfCustomerPhoneNO.clear();

        labelCustomerSuccess.setText("");
        paneCreateCustomer.setVisible(true);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);


    }


    public void showOrderTab() {
        tfDeliveryPointID.clear();
        tfCreateOrderPhoneNO.clear();
        labelOrderCreated.setText("");
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(true);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);

        // Populating Cloth tableview
        tableViewProducts.getItems().clear();

        ObservableList<Cloth> clothingList = new Cloth().populateProductTable();

        colClothID.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        colClothType.setCellValueFactory(new PropertyValueFactory<>("clothType"));
        colClothPrice.setCellValueFactory(new PropertyValueFactory<>("clothPrice"));

        tableViewProducts.setItems(clothingList);

    }


    public void showConfirmOrderTab() {
        tfConfirmON.clear();

        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneSMSCustomer.setVisible(false);
        paneLabel.setVisible(false);
        paneConfirmOrder.setVisible(true);
        labelOrderConfirmed.setText("");
    }

    public void showLabelTab() {
        tfLabelOrderNumber.clear();

        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(true);
        paneSMSCustomer.setVisible(false);

        labelSuccess.setText("");
    }

    public void showSMSCustomerTab() {
        tfOrderNumberSMS.clear();
        labelMessage.setText("");
        labelMessage.setVisible(false);

        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneSMSCustomer.setVisible(true);
    }


}
