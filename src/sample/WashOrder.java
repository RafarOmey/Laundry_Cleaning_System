package sample;

import Foundation.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class WashOrder {





    public void createWashOrder(ObservableList<Cloth> itemsToBasket, TextField addClothesOrderNumber) {

        for (Cloth clothID: itemsToBasket)
        {
            Database.executeStatement("insert into tblWashOrder (fldOrderNumber, fldClothID) values("+addClothesOrderNumber.getText()+ ","+ clothID.getClothID()+")");



        }



    }

}





