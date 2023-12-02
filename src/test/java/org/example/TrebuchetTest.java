package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrebuchetTest {

    @Test
    void getCalibrationValuesSum() {
        Trebuchet trebuchet = new Trebuchet();
        int actual = trebuchet.getCalibrationValuesSum();
        int expected = 54667;

        Assertions.assertEquals(expected, actual);
    }
}