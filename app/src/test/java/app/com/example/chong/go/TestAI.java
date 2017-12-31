package app.com.example.chong.go;

import org.junit.Test;

import app.com.example.chong.go.GameBuild.Game;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the AI
 */

public class TestAI {
    @Test
    public void testAI() throws Exception{
        Game testGame = new Game();//create new game

        testGame.gameBoard.board[0][1] = 'B';
        testGame.gameBoard.board[1][0] = 'B';
        testGame.gameBoard.board[1][2] = 'B';
        testGame.gameBoard.board[2][1] = 'B';

        testGame.gameBoard.board[17][0] = 'B';
        testGame.gameBoard.board[18][1] = 'B';
        testGame.gameBoard.board[16][1] = 'B';
        testGame.gameBoard.board[17][1] = 'W';

        testGame.gameBoard.board[3][17] = 'B';
        testGame.gameBoard.board[1][18] = 'B';
        testGame.gameBoard.board[2][18] = 'B';
        testGame.gameBoard.board[1][16] = 'B';
        testGame.gameBoard.board[2][16] = 'B';
        testGame.gameBoard.board[1][17] = 'W';
        testGame.gameBoard.board[2][17] = 'W';
        testGame.gameBoard.board[0][17] = 'W';

        assertTrue(testGame.getBlackTurn() == true);
        testGame.move(16, 18);
        testGame.AI();
        assertTrue(testGame.getBlackTurn() == true);
        testGame.move(18, 18);
        testGame.AI();
        assertTrue(testGame.getBlackTurn() == true);
        testGame.move(3, 8);
        testGame.AI();
        assertTrue(testGame.getBlackTurn() == true);
        testGame.move(4, 12);
        testGame.AI();
        assertTrue(testGame.getBlackTurn() == true);
        testGame.move(14, 17);
        testGame.AI();
        assertTrue(testGame.getBlackTurn() == true);
        testGame.move(10, 16);
        testGame.AI();
        assertTrue(testGame.getBlackTurn() == true);

    }
}
