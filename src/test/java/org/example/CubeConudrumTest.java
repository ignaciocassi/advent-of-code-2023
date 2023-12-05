package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CubeConudrumTest {

    @Test
    void sumPossibleGames() {
        Map<String, Integer> games = new HashMap<>();
        games.put("red", 12);
        games.put("green", 13);
        games.put("blue", 14);
        CubeConudrum cubeConudrum = new CubeConudrum();
        Assertions.assertEquals(2528, cubeConudrum.sumPossibleGames(games));
    }
}