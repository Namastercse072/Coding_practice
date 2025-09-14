package Coding_practice.Project.Ski_Slope_Simulator;

public class simulateSkiPath {
    public static String simulateSkiPath(int[] heights, int startPosition) {
        // Write code here
        if (heights == null || heights.length == 0) return "Invalid slope";
        if (startPosition < 0 || startPosition >= heights.length) return "Invalid start position";

        int rows = 0;
        for (int h : heights) rows = Math.max(rows, h);
        int cols = heights.length;

        // grid: rows x cols, top row = 0, bottom row = rows-1
        char[][] slope = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) slope[i][j] = '-';
        }

        int col = startPosition;
        // mark path by step (each move goes one visual row down)
        int step = 0;
        while (true) {
            int row = Math.min(step, rows - 1);
            slope[row][col] = 'O';

            // find nearest column with strictly lower height using helper
            int next = findNextPosition(heights, col, heights[col]);
            if (next == col) break; // no lower neighbour found or tie -> stop
            col = next;
            step++; // move one visual row down for the next placement
        }
        
          // ensure final skier position is marked in case the loop exited before marking (covers stopping before end)
        int lastRowWithO = -1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (slope[r][c] == 'O') { lastRowWithO = Math.max(lastRowWithO, r); break; }
            }
        }
        if (lastRowWithO == -1) lastRowWithO = 0;

        StringBuilder sb = new StringBuilder();
        for (int r = 0; r <= lastRowWithO; r++) {
            for (int c = 0; c < cols; c++) sb.append(slope[r][c]);
            if (r < lastRowWithO) sb.append('\n');
        }
        return sb.toString();
    }
     // helper: returns next position to move to, or currentPosition if cannot move / tie
    private static int findNextPosition(int[] heights, int currentPosition, int currentHeight) {
        int leftPos = -1, rightPos = -1;
        int leftDist = Integer.MAX_VALUE, rightDist = Integer.MAX_VALUE;

        // search left: nearest lower
        for (int i = currentPosition - 1; i >= 0; i--) {
            if (heights[i] < currentHeight) {
                leftPos = i;
                leftDist = currentPosition - i;
                break;
            }
        }

        // search right: nearest lower
        for (int j = currentPosition + 1; j < heights.length; j++) {
            if (heights[j] < currentHeight) {
                rightPos = j;
                rightDist = j - currentPosition;
                break;
            }
        }

        if (leftPos == -1 && rightPos == -1) return currentPosition; // no lower found
        if (leftPos != -1 && rightPos == -1) return leftPos;
        if (leftPos == -1 && rightPos != -1) return rightPos;

        // both found: choose closest; if equal distance, skier stops
        if (leftDist < rightDist) return leftPos;
        if (rightDist < leftDist) return rightPos;
        return currentPosition; // tie -> stop
    }

    public static void main(String[] args) {
        int[] heights = {5, 3, 4, 2, 1, 2, 3};
        int startPosition = 2;
        String result = simulateSkiPath(heights, startPosition);
        System.out.println(result);
    }

}