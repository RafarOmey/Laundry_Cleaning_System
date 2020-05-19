package sample;

import Foundation.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Observable;
import java.util.Properties;


public class Controller {






    @FXML
    TextField textCustomerName, orderCustomerID, deliveryPointID, textCustomerPhoneNO, textCustomerMail, addClothesOrderNumber, labelOrderNumber;
    @FXML
    Button buttonCreateCustomer, createCustomerTab, createOrderTab, confirmOrderTab, labelTab, genLabel;
    @FXML
    AnchorPane paneCreateCustomer, paneCreateOrder, paneConfirmOrder, paneLabel;

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
        int orderNumber = Integer.parseInt(labelOrderNumber.getText());
        order.generateLabel(orderNumber);
    }

    public void createCustomer() {

        Customer createNewCustomer = new Customer();
        String customerName = (textCustomerName.getText());
        String mail = (textCustomerMail.getText());
        int phoneNO = Integer.parseInt(textCustomerPhoneNO.getText());


        createNewCustomer.createCustomer(customerName, mail, phoneNO);

    }


    public void createOrder() {
        int customerID = Integer.parseInt(orderCustomerID.getText());
        int deliveryPoint = Integer.parseInt(deliveryPointID.getText());


        Order order = new Order();
        order.createOrder(customerID, deliveryPoint);

    }


    // Switching tabs

    public void showCustomerTab() {
        paneCreateCustomer.setVisible(true);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
    }

    public void showOrderTab() {
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(true);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(false);
        tableViewProducts.getItems().clear();


        Database.selectSQL("SELECT * from tblClothes");

        int clothID;
        String clothName;
        String entry = "";

        do {


            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothID = Integer.parseInt(entry);
            } else {
                break;
            }

            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                clothName = entry;
            } else {
                break;
            }


            clothingList.add(new Cloth(clothID, clothName));
        } while (true);


        ColClothID.setCellValueFactory(new PropertyValueFactory<>("ClothID"));
        ColClothType.setCellValueFactory(new PropertyValueFactory<>("ClothType"));

        tableViewProducts.setItems(clothingList);


    }

    public void showConfirmOrderTab() {
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(true);
        paneLabel.setVisible(false);
    }

    public void showLabelTab() {
        paneCreateCustomer.setVisible(false);
        paneCreateOrder.setVisible(false);
        paneConfirmOrder.setVisible(false);
        paneLabel.setVisible(true);
    }


}
