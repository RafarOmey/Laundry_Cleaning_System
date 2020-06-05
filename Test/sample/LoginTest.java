package sample;

import Domain.Login;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    public void testJobID(){
        Login login = new Login();
        login.setEmployeeID(1);
        int jobID=login.getJobID();
        assertEquals(1,jobID);
    }
}