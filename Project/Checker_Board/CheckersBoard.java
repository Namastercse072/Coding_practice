package Coding_practice.Project.Checker_Board;

import java.util.Scanner;

public class CheckersBoard {

    public static String createCheckersBoard(String blackPosition, String whitePosition) {
        char[][] board = new char[8][8];

        // Fill the board with dots
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '.';
            }
        }

        // Place Black ('B') and White ('W') pieces
        placePiece(board, blackPosition, 'B');
        placePiece(board, whitePosition, 'W');

        // Build output from rank 8 (top) to rank 1 (bottom)
        StringBuilder sb = new StringBuilder();
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                sb.append(board[row][col]);
            }
            if (row != 0) sb.append("\n"); // no extra newline at the end
        }

        return sb.toString();
    }

    private static void placePiece(char[][] board, String pos, char piece) {
        if (pos == null || pos.length() != 2) return;

        pos = pos.toUpperCase();
        int col = pos.charAt(0) - 'A'; // A→0, B→1, ...
        int row = Character.getNumericValue(pos.charAt(1)) - 1; // 1→0, 8→7

        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            board[row][col] = piece;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String black = sc.nextLine(); // e.g., a1
        String white = sc.nextLine(); // e.g., h8

        System.out.println(createCheckersBoard(black, white));

        sc.close();
    }
}
