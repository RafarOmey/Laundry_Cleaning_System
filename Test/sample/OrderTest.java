package sample;

import Domain.Order;
import org.junit.jupiter.api.Test;
import tech.Select;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    public void testMaxOrderNumber() {

        Order test = new Order();
        int maxOrderNumber = Select.getMaxOrder();
        assertEquals(60,maxOrderNumber);

    }

}