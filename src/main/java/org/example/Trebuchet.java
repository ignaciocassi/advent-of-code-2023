package org.example;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Trebuchet {

    public int getCalibrationValuesSum() {
        URL resource = getClass().getClassLoader().getResource("trebuchet.txt");
        String sheet = "";
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
                sheet = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("File not found");
        }

        List<Integer> numbers = new ArrayList<>();
        String[] lines = sheet.split("\n");
        for (String line : lines) {
            List<Character> numbersChars = new ArrayList<>();
            for (Character character : line.toCharArray()) {
                if (character >= 48 && character <= 57) {
                    numbersChars.add(character);
                }
            }
            String first = String.valueOf(numbersChars.get(0));
            String last = String.valueOf(numbersChars.get(numbersChars.size() - 1));
            numbers.add(Integer.valueOf(first + last));
        }
        return numbers.stream().reduce(0, (a, b) -> a + b);
    }

}
