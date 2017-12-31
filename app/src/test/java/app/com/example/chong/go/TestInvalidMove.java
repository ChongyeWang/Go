package app.com.example.chong.go;

import org.junit.Test;
import app.com.example.chong.go.GameBuild.Game;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the invalid move.
 */

public class TestInvalidMove {
    @Test
    public void testInvalidMove() throws Exception{
        Game testGame = new Game();//create new game
        testGame.gameBoard.board[0][1] = 'B';
        testGame.gameBoard.board[1][0] = 'B';
        testGame.gameBoard.board[1][2] = 'B';
        testGame.move(1, 2);
        testGame.move(1, 1);
        assertTrue(testGame.gameBoard.board[1][1] == 'E');

        Game testGame2 = new Game();
        testGame2.gameBoard.board[0][1] = 'B';
        testGame2.gameBoard.board[1][0] = 'B';
        testGame2.gameBoard.board[1][2] = 'B';
        testGame2.gameBoard.board[1][1] = 'W';

        testGame2.move(1, 2);
        assertTrue(testGame2.gameBoard.board[1][1] == 'E');

        Game testGame3 = new Game();
        for(int i = 10; i <= 15; i++){
            for(int j = 10; j <= 15; j++){
                testGame3.gameBoard.board[i][j] = 'B';
            }
        }
        for(int i = 11; i <= 14; i++){
            for(int j = 11; j <= 14; j++){
                testGame3.gameBoard.board[i][j] = 'W';
            }
        }
        for(int i = 12; i <= 13; i++){
            for(int j = 12; j <= 13; j++){
                testGame3.gameBoard.board[i][j] = 'B';
                if(i == 13 && j == 13){
                    testGame3.gameBoard.board[i][j] = 'E';
                }
            }
        }

        testGame3.move(13, 13);
        assertTrue(testGame2.gameBoard.board[13][13] == 'E');
    }
}
