package sample;

import Foundation.Database;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Login {


    private int employeeID;
    private String password;


    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int username) {
        this.employeeID = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Login method for DeliveryPerson
    public void userLoginDeliveryP(AnchorPane loggedInAP, AnchorPane loginPane, int username, String password) {


        Database.selectSQL("select * from tblUserLogin where fldEmployeeID= " + getEmployeeID());

        String entry;
        int jobID = 0;

        do {


            entry = Database.getData();

            if (!entry.equals("-ND-")) {
                setEmployeeID(Integer.parseInt(entry));
            } else {
                break;
            }
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                setPassword(entry);
            } else {
                break;
            }
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                jobID = Integer.parseInt(entry);
            } else {
                break;
            }


        } while (true);


        if (username == getEmployeeID() && password.equals(getPassword()) && jobID == 1) {

            loginPane.setVisible(false);
            loggedInAP.setVisible(true);

        }

    }


    //Login method for CleaningPerson
    public void userLoginCleaningP(AnchorPane loggedInAP, AnchorPane loginPane, int username, String password) {

        Database.selectSQL("select * from tblUserLogin where fldEmployeeID= " + getEmployeeID());

        String entry;
        int jobID = 0;

        do {


            entry = Database.getData();

            if (!entry.equals("-ND-")) {
                setEmployeeID(Integer.parseInt(entry));
            } else {
                break;
            }
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                setPassword(entry);
            } else {
                break;
            }
            entry = Database.getData();
            if (!entry.equals("-ND-")) {
                jobID = Integer.parseInt(entry);
            } else {
                break;
            }


        } while (true);


        if (username == getEmployeeID() && password.equals(getPassword()) && jobID == 2) {

            loginPane.setVisible(false);
            loggedInAP.setVisible(true);

        }

    }
}
