package com.kerrrusha;

import org.junit.Test;

import static com.kerrrusha.util.TaskUtil.factorial;
import static com.kerrrusha.util.TaskUtil.getRandomInt;
import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    public void randomIntTest() {
        final int attempts = 1000;
        final int fromInclusive = 1;
        final int toExclusive = 10;
        for (int i = 0; i < attempts; i++) {
            int random = getRandomInt(fromInclusive, toExclusive);
            assertTrue(random >= fromInclusive && random < toExclusive);
        }
    }

    @Test
    public void factorialTest() {
        final int n = 3;
        final int expected = 6;
        final int actual = factorial(n);
        assertEquals(expected, actual);
    }

    @Test
    public void factorialTest1() {
        final int n = 5;
        final int expected = 120;
        final int actual = factorial(n);
        assertEquals(expected, actual);
    }

    @Test
    public void factorialTest2() {
        final int n = 1;
        final int expected = 1;
        final int actual = factorial(n);
        assertEquals(expected, actual);
    }

    @Test
    public void factorialTest3() {
        final int n = 0;
        final int expected = 1;
        final int actual = factorial(n);
        assertEquals(expected, actual);
    }

}
