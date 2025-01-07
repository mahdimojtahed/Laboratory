package com.mehdi.Laboratory.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void add() {
        assertEquals(5, calculator.add(2, 3), "Addition should return 5");
    }

    @Test
    void subtract() {
        assertEquals(4, calculator.subtract(7, 3), "Subtraction should return 4");
    }

    @Test
    void divideByZero() {
        Exception exception = assertThrows(RuntimeException.class, () -> calculator.divide(1, 0));
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "2, 3, 5",
            "3, 5, 8",
            "1, 1, 2"
    })
    void testAddParameterized(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }
}