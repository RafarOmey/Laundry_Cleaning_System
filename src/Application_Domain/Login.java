package Application_Domain;

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

    /**
     * This method will get into the Database and get the Employee Name that is logged in.
     *
     * @return it will return the name of the employee, that we will use to showcase in our loginScreen.
     */
    public String getEmployeeName() {
        Database.selectSQL("select fldName from tblEmployee where fldEmployeeID=" + getEmployeeID());
        employeeName = Database.getData();
        return employeeName;
    }

    /**
     * This method will get the Job ID of the specific user.
     *
     * @return Will return the Job Id, wich we will use later in the Controller so we can controller that this Job ID has Permission to do.
     */
    public int getJobID() {
        Database.selectSQL("select fldJobID from tblUserLogin where fldEmployeeID=" + getEmployeeID());
        jobID = Integer.parseInt(Database.getData());
        return jobID;
    }

    public int getEmployeeID() {

        return employeeID;
    }

    /**
     * This Method will get the Password of the Employee that is trying to login.
     *
     * @return Will return the Password, and we can check if the Employee logged in with the correct Password.
     */
    public String getPassword() {
        Database.selectSQL("select fldPassword from tblUserLogin where fldEmployeeID=" + getEmployeeID());
        password = Database.getData();
        return password;
    }


}
