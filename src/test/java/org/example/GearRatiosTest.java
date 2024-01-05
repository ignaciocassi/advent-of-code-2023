package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GearRatiosTest {

    private static GearRatios gearRatios;

    @BeforeAll
    static void beforeAll() {
        gearRatios = new GearRatios();
    }

    @Test
    void shouldSum560670() {
        Assertions.assertEquals(560670, gearRatios.sumGears("gear_ratios.txt"));
    }

}
