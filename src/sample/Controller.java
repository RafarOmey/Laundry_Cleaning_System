package sample;

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


    private static Connection con;
    private static String port;
    private static String databaseName;
    private static String userName;
    private static String password;


    static {
        Properties props = new Properties();
        String fileName = "db.properties";
        InputStream input;
        try {
            input = new FileInputStream(fileName);
            props.load(input);
            port = props.getProperty("port", "1433");
            databaseName = props.getProperty("databaseName");
            userName = props.getProperty("userName", "sa");
            password = props.getProperty("password");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Database Ready");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }


    @FXML
    TextField textCustomerName, orderCustomerID, deliveryPointID, textCustomerPhoneNO, textCustomerMail,addClothesOrderNumber,labelOrderNumber;
    @FXML
    Button buttonCreateCustomer,createCustomerTab, createOrderTab, confirmOrderTab, labelTab, genLabel;
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




/*    public void generateLabel(){
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

*/
/*
    public void createOrder() {
        int customerID = Integer.parseInt(orderCustomerID.getText());
        int deliveryPoint = Integer.parseInt(deliveryPointID.getText());


        Order order = new Order();
        order.createOrder(customerID, deliveryPoint);

    }
*/



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
        tableViewProducts.getItems().clear();

        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:" + port + ";databaseName=" + databaseName, userName, password);  // to hide the password in file.
            Statement stmt = con.createStatement();


            ResultSet rs = stmt.executeQuery("SELECT * from tblClothes");
            while (rs.next()) {

                clothingList.add(new Cloth(rs.getInt(1), rs.getString(2)));

            }
            con.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


        ColClothID.setCellValueFactory(new PropertyValueFactory<>("ClothID"));
        ColClothType.setCellValueFactory(new PropertyValueFactory<>("ClothType"));

        tableViewProducts.setItems(clothingList);



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
