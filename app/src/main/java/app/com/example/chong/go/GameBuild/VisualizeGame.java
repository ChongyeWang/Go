package app.com.example.chong.go.GameBuild;

import java.util.Scanner;

/**
 * This class will visualize thegame
 * by printing the board with chars.
 */

public class VisualizeGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = new Game();
        while (true) {
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    if (game.gameBoard.board[i][j] == 'E') {
                        System.out.print('-');
                    }
                    if (game.gameBoard.board[i][j] == 'B') {
                        System.out.print('B');
                    }
                    if (game.gameBoard.board[i][j] == 'W') {
                        System.out.print('W');
                    }
                    System.out.print(' ');
                }
                System.out.println(' ');
            }
            int targetX = sc.nextInt();
            int targetY = sc.nextInt();
            game.move(targetX, targetY);
        }
    }
}//End of class