package app.com.example.chong.go;

import org.junit.Test;
import app.com.example.chong.go.GameBuild.Game;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the extendLiberty method.
 */

public class TestExtendLiberty {
    @Test
    public void testExtendLiberty() throws Exception{
        Game testGame = new Game();

        testGame.gameBoard.board[0][1] = 'B';
        testGame.gameBoard.board[1][0] = 'B';
        testGame.gameBoard.board[1][2] = 'B';
        testGame.gameBoard.board[2][1] = 'B';
        testGame.gameBoard.board[1][1] = 'W';

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

        testGame.gameBoard.board[6][6] = 'W';
        testGame.gameBoard.board[6][7] = 'W';

        testGame.gameBoard.board[17][18] = 'B';
        testGame.gameBoard.board[18][17] = 'B';
        testGame.gameBoard.board[18][18] = 'W';

        testGame.gameBoard.board[18][10] = 'B';
        testGame.gameBoard.board[18][12] = 'B';
        testGame.gameBoard.board[17][11] = 'B';
        testGame.gameBoard.board[18][11] = 'W';


        for(int i = 10; i <= 15; i++){
            for(int j = 10; j <= 15; j++){
                testGame.gameBoard.board[i][j] = 'B';
            }
        }
        for(int i = 11; i <= 14; i++){
            for(int j = 11; j <= 14; j++){
                testGame.gameBoard.board[i][j] = 'W';
            }
        }
        for(int i = 12; i <= 13; i++){
            for(int j = 12; j <= 13; j++){
                testGame.gameBoard.board[i][j] = 'B';
                if(i == 13 && j == 13){
                    testGame.gameBoard.board[i][j] = 'E';
                }
            }
        }

        assertTrue(testGame.extendLiberty(6, 6)[0] == 8);
        assertTrue(testGame.extendLiberty(6, 6)[1] == 6);

        assertTrue(testGame.extendLiberty(18, 1)[0] == 18);
        assertTrue(testGame.extendLiberty(18, 1)[1] == 3);

        assertTrue(testGame.extendLiberty(10, 10)[0] == 10);
        assertTrue(testGame.extendLiberty(10, 10)[1] == 16);

        assertTrue(testGame.extendLiberty(11, 11)[0] == -1);
        assertTrue(testGame.extendLiberty(11, 11)[1] == -1);

        assertTrue(testGame.extendLiberty(17, 2)[0] == 16);
        assertTrue(testGame.extendLiberty(17, 2)[1] == 0);
    }
}
