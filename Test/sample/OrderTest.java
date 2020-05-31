package sample;

import Application_Domain.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    public void testMaxOrderNumber() {

        Order test = new Order();
        int maxOrderNumber = test.getMaxOrderNumber();
        assertEquals(60,maxOrderNumber);

    }

}