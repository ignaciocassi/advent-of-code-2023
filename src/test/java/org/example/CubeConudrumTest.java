package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class CubeConudrumTest {

    private static Map<String, Integer> games;

    private static CubeConudrum cubeConudrum;

    @BeforeAll
    static void beforeAll() {
        cubeConudrum = new CubeConudrum();
        games = new HashMap<>();
        games.put("red", 12);
        games.put("green", 13);
        games.put("blue", 14);
    }

    @Test
    void sumPossibleGames() {
        Assertions.assertEquals(2528, cubeConudrum.sumPossibleGames(games, "cubes.txt"));
    }

    @Test
    void sumPowerFewestNeeded() {
        Assertions.assertEquals(67363, cubeConudrum.sumPowerFewestNeeded("cubes.txt"));
    }
}