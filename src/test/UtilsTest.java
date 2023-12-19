package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import front.Utils;

public class UtilsTest {
    
    @Test 
    public void coordinatesTest() {
        boolean check = Utils.coordinatesTest();
        assertEquals(true, check);
    }

    @Test 
    public void angleTest() {
        boolean check = Utils.angleTest();
        assertEquals(true, check);
    }
}
