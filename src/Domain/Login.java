package Domain;

import Foundation.Database;
import tech.Select;

public class Login {


    private int employeeID;


    public void setEmployeeID(int username) {
        this.employeeID = username;
    }


    /**
     * This method will get into the Database and get the Employee Name that is logged in.
     *
     * @return it will return the name of the employee, that we will use to showcase in our loginScreen.
     */
    public String getEmployeeName() {
        Select.selectEmployeeName(getEmployeeID());


        return Database.getData();
    }

    /**
     * This method will get the Job ID of the specific user.
     *
     * @return Will return the Job Id, wich we will use later in the Controller so we can controller that this Job ID has Permission to do.
     */
    public int getJobID() {
        Select.selectJobIDUserLogin(getEmployeeID());

        return Integer.parseInt(Database.getData());
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
        Select.selectPasswordUserLogin(getEmployeeID());

        return Database.getData();
    }


}
