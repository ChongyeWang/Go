package app.com.example.chong.go;

import org.junit.Test;
import app.com.example.chong.go.GameBuild.Game;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the copy game.
 */

public class TestCopy {
    @Test
    public void testCopy() throws Exception {
        Game testGame = new Game();
        testGame.gameBoard.board[0][1] = 'B';
        testGame.gameBoard.board[1][0] = 'B';
        testGame.gameBoard.board[1][2] = 'B';
        testGame.move(1, 2);
        testGame.move(1, 1);
        Game copyTestGame = new Game(testGame);
        assertTrue(copyTestGame.getBlackTurn() == false);
        assertTrue(copyTestGame.gameBoard.board[1][1] == 'E');
    }
}