package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CubeConudrum {

    public int sumPossibleGames(Map<String, Integer> game) {
        String gameFile = readFile("cubes.txt");
        int sum = 0;
        String[] lines = gameFile.split("\n");
        for (String line: lines) {
            String[] lineFields = line.split(": |:|; |, ");
            int gameId = Integer.valueOf(lineFields[0].split(" ")[1]);
            int i = 1;
            boolean possible = true;
            while (i < lineFields.length && possible) {
                String[] seen = lineFields[i].split(" ");
                int quantity = Integer.valueOf(seen[0]);
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
