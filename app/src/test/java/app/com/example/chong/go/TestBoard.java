package app.com.example.chong.go;

import org.junit.Test;
import app.com.example.chong.go.GameBuild.Board;
import static org.junit.Assert.assertTrue;
/**
 * This class tests the board class
 */

public class TestBoard {
    @Test
    public void testBoard() throws Exception{
        Board testBoard = new Board(19, 19);
        testBoard.setSpace(0, 0, 'P');
        assertTrue(testBoard.getColumn() == 19);
        assertTrue(testBoard.getRow() == 19);
        assertTrue(testBoard.board[0][0] == 'P');
        assertTrue(testBoard.board[5][5] == 'E');
        assertTrue(testBoard.board[7][5] == 'E');
        assertTrue(testBoard.board[18][18] == 'E');
    }
}
