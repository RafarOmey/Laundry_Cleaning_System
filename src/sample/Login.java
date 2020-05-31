package sample;

import Foundation.Database;
import javafx.scene.layout.AnchorPane;

import javax.naming.ldap.Control;
import javax.xml.crypto.Data;

public class Login {


    private int employeeID;
    private String password;
    private int jobID;
    private String employeeName;

    public void setEmployeeID(int username) {
        this.employeeID = username;
    }

    public String getEmployeeName() {
        Database.selectSQL("select fldName from tblEmployee where fldEmployeeID=" + getEmployeeID());
        employeeName = Database.getData();
        return employeeName;
    }

    public int getJobID() {
        Database.selectSQL("select fldJobID from tblUserLogin where fldEmployeeID=" + getEmployeeID());
        jobID = Integer.parseInt(Database.getData());
        return jobID;
    }

    private int getEmployeeID() {

        return employeeID;
    }

    public String getPassword() {
        Database.selectSQL("select fldPassword from tblUserLogin where fldEmployeeID=" + getEmployeeID());
        password = Database.getData();
        return password;
    }


}
