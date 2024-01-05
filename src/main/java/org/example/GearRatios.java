package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GearRatios {

    private FileReader fileReader;

    public GearRatios() {
        this.fileReader = new FileReader();
    }

    public int sumGears(String fileName) {
        // Read the input file
        String file = fileReader.readFile(fileName);
        // Split into lines
        String[] lines = file.split("\\s+");
        // Sum of the parts
        int sum = 0;
        // For each line
        for (int i = 0; i < lines.length; i++) {

            // Get positions of those numbers (startIndex, endIndex) in the line as positions [index][number, startIndex, endIndex]
            List<Integer> numbers = getNumbers(lines[i]);
            int[][] positions = getNumbersAndPositions(lines[i], numbers);
            // For each number
            for (int n = 0; n < positions.length; n++) {
                if (validateIsPart(i, positions[n], lines)) {
                    sum += positions[n][0];
                }
            }
        }
        return sum;
    }

    /**
     * Given a line, uses regex to return a list of Integers contained in the line
     * @param line
     * @return
     */
    private List<Integer> getNumbers(String line) {
        List<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            numbers.add(Integer.valueOf(matcher.group()));
        }
        return numbers;
    }

    /**
     * Given a list of numbers from a line, and a line
     * Returns a matrix representing for each line the number, startIndex, endIndex
     * of the given number in the line
     * @param numbers
     * @param line
     * @return positions
     */
    private int[][] getNumbersAndPositions(String line, List<Integer> numbers) {
        // The positions matrix has numbers.size() as rows, and 3 columns [number, startIndex, endIndex]
        int[][] positions = new int[numbers.size()][3];

        // For character in the line, if its not a number, exclue
        // if it is a number, take it as part of a number section, set the startIndex
        // continue to iterate the line until reach the end or the end of the number and save the endIndex
        // then save [number, startIndex, endIndex]
        char[] chars = line.toCharArray();
        int startIndex = -1;
        int n = 0;
        String buffer = "";
        // For every character in the string
        for (int i = 0; i < line.length(); i++) {
            // If its a number, save the startIndex and append the character to the buffer
            if (Character.isDigit(chars[i])) {
                if (startIndex == -1) {
                    startIndex = i;
                }
                buffer += chars[i];
            } else {
                if (startIndex != -1) {
                    positions[n][0] = Integer.valueOf(buffer);
                    positions[n][1] = startIndex;
                    positions[n][2] = i;
                    n += 1;
                    buffer = "";
                    startIndex = -1;
                }
            }
        }
        if (buffer != "") {
            positions[n][0] = Integer.valueOf(buffer);
            positions[n][1] = startIndex;
            positions[n][2] = line.length()-1;
        }

        // Return the positions matrix
        return positions;
    }

    private boolean validateIsPart(int i, int[] positions, String[] lines) {
        if (hasSymbolsOnTop(i, positions, lines)) {
            return true;
        }
        if (hasSymbolsOnBottom(i, positions, lines)) {
            return true;
        }
        if (hasSymbolsOnLeft(i, positions, lines)) {
            return true;
        }
        if (hasSymbolsOnRight(i, positions, lines)) {
            return true;
        }
        return false;
    }

    /**
     * Given a number, index of actual line, the lines, positions of the number in te line
     * validates if the topLine has a symbol in it (from topLeftDiag to rightLeftDiag)
     * @param i index of the current line in lines
     * @param number candidate number of being a part
     * @param positions array containing [number, startIndex, endIndex] in the line
     * @param lines array of lines
     * @return true if the number has symbols on the topLine
     */
    private boolean hasSymbolsOnTop(int i, int[] positions, String[] lines) {
        boolean hasSymbols = false;
        // if it has a line on top
        if (i - 1 >= 0) {
            // If the startIndex - 1 is oub from the line, use 0 as topLeftDiag. else, else use the topLeftDiag
            int topLeftDiag = positions[1] - 1 < 0 ? 0 : positions[1] - 1;
            // If the endIndex + 1 is oub from the line, use the last index from the line, else use the topRightDiag
            int topRightDiag = positions[2] + 1 >= lines[i - 1].length() ? lines[i - 1].length() : positions[2] + 1;
            String top = lines[i - 1].substring(topLeftDiag, topRightDiag);
            hasSymbols = hasSymbols(top);
        }
        return hasSymbols;
    }

    private boolean hasSymbolsOnBottom(int i, int[] positions, String[] lines) {
        boolean hasSymbols = false;
        // if it has a line on bottom
        if (i + 1 < lines.length) {
            // If the startIndex - 1 is oub from the line, use 0 as topLeftDiag. else, else use the topLeftDiag
            int bottomLeftDiag = positions[1] - 1 < 0 ? 0 : positions[1] - 1;
            // If the endIndex + 1 is oub from the line, use the last index from the line, else use the topRightDiag
            int bottomRightDiag = positions[2] + 1 >= lines[i + 1].length() ? lines[i + 1].length() : positions[2] + 1;
            String bottom = lines[i + 1].substring(bottomLeftDiag, bottomRightDiag);
            hasSymbols = hasSymbols(bottom);
        }
        return hasSymbols;
    }

    private boolean hasSymbolsOnLeft(int i, int[] positions, String[] lines) {
        // If the endIndex - 1 is oub from the line, the number has no char in left, assign -1 and return false
        int leftIndex = positions[1] - 1 < 0 ? -1 : positions[1] - 1;
        if (leftIndex < 0) {
            return false;
        }
        String leftStr = lines[i].substring(leftIndex, leftIndex + 1);
        return hasSymbols(leftStr);
    }

    private boolean hasSymbolsOnRight(int i, int[] positions, String[] lines) {
        // If the startIndex - 1 is oub from the line, the number has no char in leftIndex, assign -1 and return false
        int rightIndex = positions[2] + 1 >= lines[i].length() ? -1 : positions[2] + 1;
        if (rightIndex == -1) {
            return false;
        }
        String rightStr = lines[i].substring(rightIndex - 1, rightIndex);
        return hasSymbols(rightStr);
    }

    /**
     * Given a line, uses regex validates if it contains: any char, followed by  one or more chars of *+#$, and any char
     * @param line line to be evaluated
     * @return hasSymbols
     */
    private boolean hasSymbols(String line) {
        boolean hasSymbols = false;
        Pattern pattern = Pattern.compile(".*[@#$%^&*=/+-].*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()){
            hasSymbols = true;
        }
        return hasSymbols;
    }

}
