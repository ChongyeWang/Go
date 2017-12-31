package app.com.example.chong.go;

import org.junit.Test;

import app.com.example.chong.go.GameBuild.Game;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the ko rule.
 */

public class TestKo {
    @Test
    public void testKo() throws Exception{
        Game testGame = new Game();//create new game
        testGame.move(1, 0);
        testGame.move(0, 0);
        testGame.move(18, 18);
        testGame.move(1, 1);
        testGame.move(17, 17);
        testGame.move(0, 2);
        testGame.move(0, 1);
        assertTrue(testGame.gameBoard.board[0][0] == 'E');
        assertTrue(testGame.getBlackTurn() == false);
        testGame.move(0, 0);
        assertTrue(testGame.gameBoard.board[0][0] == 'E');
        assertTrue(testGame.getBlackTurn() == false);
        testGame.move(10, 10);
        testGame.move(11, 11);
        testGame.move(0, 0);
        assertTrue(testGame.gameBoard.board[0][0] == 'W');
        assertTrue(testGame.getBlackTurn() == true);
    }
}
