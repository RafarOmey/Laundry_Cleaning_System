package application;


import Domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tech.Select;

import java.util.Optional;
import java.util.function.UnaryOperator;


public class Controller {

    @FXML
    TextField tfUN, tfCustomerName, tfCreateOrderPhoneNO, tfDeliveryPointID, tfCustomerPhoneNO, tfLabelOrderNumber;
    @FXML
    TextField tfConfirmON, tfOrderNumberSMS;
    @FXML
    PasswordField pwPassword;
    @FXML
    Button buttonDailyWashOrder, buttonLogOut, buttonMessageCustomerTab, buttonCreateCustomer, buttonCreateCustomerTab, buttonCreateOrderTab, buttonConfirmOrderTab, buttonGenerateLabelTab, buttonGenLabel;
    @FXML
    AnchorPane paneLoginScreen, paneCreateCustomer, paneCreateOrder, paneConfirmOrder, paneLabel, paneSMSCustomer,panePrintDailyWashOrder;
    @FXML
    Label labelWrongInput, labelMessage, labelLoggedInAs, labelCustomerCreated, labelCreateOrder, labelSuccess, labelOrderConfirmed;
    @FXML
    TableView<Cloth> tableViewProducts, tableViewBasket, tableViewDailyWashOrder;

    @FXML
    TableColumn<Cloth, String> colClothType, colClothTypeBasket, colDailyWashOrderName;
    @FXML
    TableColumn<Cloth, Integer> colClothID, colClothIDBasket, colClothPrice, colClothPriceBasket, colDailyWashOrderAmount;

    private ObservableList<Cloth> itemsToBasket = FXCollections.observableArrayList();


    public void initialize() {

        tfCreateOrderPhoneNO.setTextFormatter(numbersOnly());

        tfUN.setTextFormatter(numbersOnly());
        tfCustomerPhoneNO.setTextFormatter(numbersOnly());
        tfDeliveryPointID.setTextFormatter(numbersOnly());
        tfConfirmON.setTextFormatter(numbersOnly());
        tfOrderNumberSMS.setTextFormatter(numbersOnly());
        tfLabelOrderNumber.setTextFormatter(numbersOnly());
        tfCustomerName.setTextFormatter(textOnly());

        addTextLimiter(tfCustomerPhoneNO, 8);
        addTextLimiter(tfCreateOrderPhoneNO, 8);
        addTextLimiter(tfCustomerName, 49);
        addTextLimiter(tfDeliveryPointID, 3);


    }

    /**
     * This method adds limits on the size of textfields
     *
     * @param tf        textField to put a limit too.
     * @param maxLength put a limit on the TextFields.
     */
    private static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    /**
     * This method will make the textfields using it only be able to use numbers between 0-9
     *
     * @return The textFormatter we defined in the if statement.
     */
    private TextFormatter numbersOnly() {

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();


            if (text.matches("[0-9]*")) {
                return change;
            }


            return null;

        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        return textFormatter;
    }

    private TextFormatter textOnly() {

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();


            if (text.matches("[a-zA-ZæøåÆØÅ ]*")) {
                return change;
            }


            return null;

        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        return textFormatter;
    }


    public void generateLabel() {
        try {
            Order order = new Order();
            order.setEmployeeID(Integer.parseInt(tfUN.getText()));
            order.setOrderNumber(Integer.parseInt(tfLabelOrderNumber.getText()));
            order.setProgressID(2);


            order.generateLabel(labelSuccess);
            order.changeLog(labelSuccess);
        } catch (NumberFormatException e) {
            labelSuccess.setText("Wrong order number!");
        }

    }

    public void createCustomer() {

        if (tfCustomerPhoneNO.getText().length() == 8) {
            try {
                Customer createNewCustomer = new Customer();
                createNewCustomer.setCustomerName(tfCustomerName.getText());
                createNewCustomer.setPhoneNO(Integer.parseInt(tfCustomerPhoneNO.getText()));


                createNewCustomer.createCustomer(labelCustomerCreated);


                tfCustomerPhoneNO.clear();
                tfCustomerName.clear();
            } catch (NumberFormatException e) {
                labelCustomerCreated.setText("Invalid Input!");
            }
        } else {
            labelCustomerCreated.setText("Input valid phone number");
        }
    }

    public void createOrder() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Confirm contents of order
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm contents of the order");
        alert.setContentText("Are the clothes in the order confirmed?");

        try {
            if (tfCreateOrderPhoneNO.getText().equals("") || tfDeliveryPointID.getText().equals("")) {
                labelCreateOrder.setText("Fill Phone Number and DeliveryPoint");

            } else if (itemsToBasket.size() == 0) {
                labelCreateOrder.setText("Basket is Empty");

            } else if (tfCreateOrderPhoneNO.getText().length() != 8) {
                labelCreateOrder.setText("Input valid phone number");
            } else {
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Order order = new Order();
                    order.setPhoneNO(Integer.parseInt(tfCreateOrderPhoneNO.getText()));
                    order.setDeliveryPoint(Integer.parseInt(tfDeliveryPointID.getText()));
                    order.setProgressID(1);


                    order.setEmployeeID(Integer.parseInt(tfUN.getText()));

                    order.createOrder(labelCreateOrder);
                    order.setOrderNumber(Select.getMaxOrder());
                    order.changeLog(labelCreateOrder);

                    tfCreateOrderPhoneNO.clear();
                    tfDeliveryPointID.clear();


                    WashOrder washOrder = new WashOrder();
                    washOrder.createWashOrder(itemsToBasket);

                    washOrder.insertTotalPrice(itemsToBasket);

                    tableViewBasket.getItems().clear();
                }
            }


        } catch (NumberFormatException e) {
            labelCreateOrder.setText("Wrong input!");
        }
    }


    public void confirmOrder() {
        try {
            Order order = new Order();
            order.setOrderNumber(Integer.parseInt(tfConfirmON.getText()));
            order.setEmployeeID(Integer.parseInt(tfUN.getText()));
            order.setProgressID(3);

            order.confirmOrder();
            labelOrderConfirmed.setText("Order Confirmed");
            order.changeLog(labelOrderConfirmed);
        } catch (Exception e) {
            labelOrderConfirmed.setText("Wrong order number!");
        }
    }

    public void customerSMS() {
        try {
            Order order = new Order();
            order.setOrderNumber(Integer.parseInt(tfOrderNumberSMS.getText()));
            order.setEmployeeID(Integer.parseInt(tfUN.getText()));
            order.setProgressID(4);


            order.messageCustomer(labelMessage);
            order.changeLog(labelMessage);
            labelMessage.setVisible(true);


        } catch (NumberFormatException e) {
            labelMessage.setText("Wrong order number!");
        }
    }

    /**
     * This method will get the selected items from tableViewProduct and add that to ObservableList itemsToBasket
     * which we will use to populate tableViewBasket with the selected items.
     */
    public void addToBasket() {


        Cloth selection = tableViewProducts.getSelectionModel().getSelectedItem();


        itemsToBasket.addAll(new Cloth(selection.getClothID(), selection.getClothType(), selection.getClothPrice()));


        colClothIDBasket.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        colClothTypeBasket.setCellValueFactory(new PropertyValueFactory<>("clothType"));
        colClothPriceBasket.setCellValueFactory(new PropertyValueFactory<>("ClothPrice"));


        tableViewBasket.setItems(itemsToBasket);


    }

    /**
     * This method will remove items from TableviewBasket.
     */
    public void removeFromBasket() {

        Cloth removeSelection = tableViewBasket.getSelectionModel().getSelectedItem();
        tableViewBasket.getItems().remove(removeSelection);
    }

    public void showDailyWashOrder() {
        colDailyWashOrderName.setCellValueFactory(new PropertyValueFactory<>("ClothType"));
        colDailyWashOrderAmount.setCellValueFactory(new PropertyValueFactory<>("ClothAmount"));
        ObservableList<Cloth> dailyWashOrder = FXCollections.observableArrayList();
        Cloth cloth = new Cloth();
        cloth.populateDailyWashOrderTable(dailyWashOrder);



        tableViewDailyWashOrder.setItems(dailyWashOrder);


    }

    /**
     * This method will work like a login screen using the anchor pane paneLoginScreen
     */
    public void loginScreen() {

        try {

            Login login = new Login();


            login.setEmployeeID(Integer.parseInt(tfUN.getText()));

            String password = pwPassword.getText();

            String employeeName = login.getEmployeeName();

            int jobID = login.getJobID();

            if (jobID == 2 && password.equals(login.getPassword())) {
                paneLoginScreen.setVisible(false);
                buttonGenerateLabelTab.setVisible(true);
                buttonLogOut.setVisible(true);
                labelLoggedInAs.setVisible(true);
                buttonDailyWashOrder.setVisible(true);
                labelLoggedInAs.setText("Logged in as " + employeeName);

            } else if (jobID == 1 && password.equals(login.getPassword())) {
                labelWrongInput.setVisible(false);
                paneLoginScreen.setVisible(false);
                buttonCreateCustomerTab.setVisible(true);
                buttonCreateOrderTab.setVisible(true);
                buttonMessageCustomerTab.setVisible(true);
                buttonConfirmOrderTab.setVisible(true);
                buttonLogOut.setVisible(true);
                labelLoggedInAs.setVisible(true);
                labelLoggedInAs.setText("Logged in as " + employeeName);


            }
        } catch (NumberFormatException e) {
            labelWrongInput.setVisible(true);
            labelWrongInput.setText("Wrong input!");
        }


    }

    /**
     * This method will log the current user out and change back to the login screen
     */
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
        panePrintDailyWashOrder.setVisible(false);
        buttonDailyWashOrder.setVisible(false);
        labelMessage.setText("");
        labelMessage.setVisible(false);
        labelLoggedInAs.setText("");
        labelLoggedInAs.setVisible(false);


    }


    // Switching tabs

    /**
     * This method will change to the tab Create Customer
     */
    public void showCustomerTab() {
        tfCustomerName.clear();
        tfCustomerPhoneNO.clear();

        labelCustomerCreated.setText("");
        paneCreateCustomer.setVisible(true);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        panePrintDailyWashOrder.setVisible(false);

    }

    /**
     * Shows create Order tab
     * ObservableList clothingList, will be populated into tableViewProduct with Cloth Info.
     */
    public void showOrderTab() {
        tfDeliveryPointID.clear();
        tfCreateOrderPhoneNO.clear();
        labelCreateOrder.setText("");
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(true);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        panePrintDailyWashOrder.setVisible(false);

        // Populating Cloth tableview
        tableViewProducts.getItems().clear();


        ObservableList<Cloth> clothingList = new Cloth().populateProductTable();


        colClothID.setCellValueFactory(new PropertyValueFactory<>("clothID"));
        colClothType.setCellValueFactory(new PropertyValueFactory<>("clothType"));
        colClothPrice.setCellValueFactory(new PropertyValueFactory<>("clothPrice"));

        tableViewProducts.setItems(clothingList);
        tableViewBasket.getItems().clear();

    }

    /**
     * Shows Confirm Order tab
     */
    public void showConfirmOrderTab() {
        tfConfirmON.clear();

        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneSMSCustomer.setVisible(false);
        paneLabel.setVisible(false);
        panePrintDailyWashOrder.setVisible(false);
        paneConfirmOrder.setVisible(true);
        labelOrderConfirmed.setText("");
    }

    /**
     * Shows generate label tab
     */
    public void showLabelTab() {
        tfLabelOrderNumber.clear();

        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(true);
        paneSMSCustomer.setVisible(false);
        panePrintDailyWashOrder.setVisible(false);

        labelSuccess.setText("");
    }

    /**
     * Shows SMS customer tab
     */
    public void showSMSCustomerTab() {
        tfOrderNumberSMS.clear();
        labelMessage.setText("");
        labelMessage.setVisible(false);

        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneSMSCustomer.setVisible(true);
        panePrintDailyWashOrder.setVisible(false);
    }

    public void showPrintDailyWashOrderTab() {
      tableViewDailyWashOrder.getItems().clear();

        paneConfirmOrder.setVisible(false);
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneLabel.setVisible(false);
        paneSMSCustomer.setVisible(false);
        paneLoginScreen.setVisible(false);
        panePrintDailyWashOrder.setVisible(true);

    }


}
