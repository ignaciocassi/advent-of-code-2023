package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CubeConudrum {

    public int sumPossibleGames(Map<String, Integer> game, String filename) {
        String gameFile = readFile(filename);
        int sum = 0;
        String[] lines = gameFile.split("\n");
        for (String line: lines) {
            String[] lineFields = line.split(": |:|; |, ");
            int gameId = Integer.parseInt(lineFields[0].split(" ")[1]);
            int i = 1;
            boolean possible = true;
            while (i < lineFields.length && possible) {
                String[] seen = lineFields[i].split(" ");
                int quantity = Integer.parseInt(seen[0]);
                String color = seen[1];
                if (quantity > game.get(color)) {
                    possible = false;
                }
                i++;
            }
            if (possible) {
                sum += gameId;
            }
        }
        return sum;
    }

    public int sumPowerFewestNeeded(String filename) {
        // find fewest needed of each colour for each game
        // multiply fewestRed * fewestGreen * fewestBlue for each game
        // return sum every game power
        String gameFile = readFile(filename);
        int sum = 0;
        Map<String, Integer> fewestNeeded = new HashMap<>();
        String[] lines = gameFile.split("\n");
        for (String line: lines) {
            fewestNeeded.put("red", 0);
            fewestNeeded.put("green", 0);
            fewestNeeded.put("blue", 0);
            String[] gameFields = line.split(": |:|; |, ");
            for (int i = 1; i < gameFields.length; i++) {
                String[] data = gameFields[i].split(" ");
                int quantity = Integer.parseInt(data[0]);
                String colour = data[1];
                if (quantity > fewestNeeded.get(colour)) {
                    fewestNeeded.put(colour, quantity);
                }
            }
            sum += fewestNeeded.get("red") * fewestNeeded.get("green") * fewestNeeded.get("blue");
        }
        return sum;
    }

    private String readFile(String fileName) {
        String file = "";
        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource != null) {
            try {
                InputStream inputStream = resource.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                reader.close();
                inputStream.close();
                file = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Resource not found");
        }
        return file;
    }

}
