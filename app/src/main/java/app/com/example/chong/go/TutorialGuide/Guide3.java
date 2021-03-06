package app.com.example.chong.go.TutorialGuide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import app.com.example.chong.go.GameBuild.Game;
import app.com.example.chong.go.R;

import static app.com.example.chong.go.TutorialGuide.Guide3.guide3;

/**
 * This class provides the user with the
 * eating pieces tutorial.
 */

public class Guide3 extends AppCompatActivity {

    protected static Game guide3 = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawGuide3(this));

        /*Set up guide3*/
        guide3.gameBoard.board[8][10] = 'B';
        guide3.gameBoard.board[9][10] = 'B';
        guide3.gameBoard.board[10][10] = 'B';
        guide3.gameBoard.board[8][9] = 'W';
        guide3.gameBoard.board[9][9] = 'W';
        guide3.gameBoard.board[10][9] = 'W';
        guide3.gameBoard.board[9][7] = 'W';
        guide3.gameBoard.board[7][9] = 'B';
        guide3.gameBoard.board[11][9] = 'B';
        guide3.gameBoard.board[7][8] = 'B';
        guide3.gameBoard.board[11][8] = 'B';
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        guide3 = new Game();
        return;
    }
}


/**
 * This class implements the guide3 view.
 */

class DrawGuide3 extends View {
    private float xPos = -1;
    private float yPos = -1;

    private int numOfSteps = 0;
    private boolean tutorialEnds = false;
    private boolean correctAnswer = true;

    /* Get the dimension of the screen */
    private DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    private int width = metrics.widthPixels;
    private int height = metrics.heightPixels;

    private double gridSize = (width - (65.0 / 1080.0) * width) / 18.0;

    /* Get the image from the drawable */
    private Bitmap board = BitmapFactory.decodeResource(getResources(), R.drawable.board);
    private Bitmap black = BitmapFactory.decodeResource(getResources(), R.drawable.black);
    private Bitmap white = BitmapFactory.decodeResource(getResources(), R.drawable.white);

    /* Scale the image */
    private Bitmap scaledBoard = Bitmap.createScaledBitmap(board, width, width, true);
    private Bitmap scaledBlack = Bitmap.createScaledBitmap(black, (int) (width / 19 - width / 200), (int) (width / 19 - width / 200), true);
    private Bitmap scaledWhite = Bitmap.createScaledBitmap(white, (int) (width / 19 - width / 200), (int) (width / 19 - width / 200), true);

    /**
     * DrawGame constructor
     *
     * @param context
     */
    public DrawGuide3(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        //Draw the board
        canvas.drawBitmap(scaledBoard, 0, 0, null);

        //Draw the pieces
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (guide3.gameBoard.board[i][j] != 'E') {
                    if (guide3.gameBoard.board[i][j] == 'B') {
                        canvas.drawBitmap(scaledBlack, (float) (j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                    }
                    if (guide3.gameBoard.board[i][j] == 'W') {
                        canvas.drawBitmap(scaledWhite, (float) (j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                    }
                }
            }
        }

        /*Draw text to canvas*/
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        canvas.drawText("Goal : Remove The Middle White Pieces", 0, (float) (width + 100.0 / 1080.0 * width), paint);
        canvas.drawText("Answer : ", 0, (float) (width + 200.0 / 1080.0 * width), paint);

        /*Check Answer*/
        if(tutorialEnds == true) {
            if (correctAnswer == true) {
                paint.setColor(Color.GREEN);
                canvas.drawText("Correct", (float)(250.0 / 1080.0 * width), (float)(width + 200.0 / 1080.0 * width), paint);
            } else {
                paint.setColor(Color.RED);
                canvas.drawText("Wrong", (float)(250.0 / 1080.0 * width), (float)(width + 200.0 / 1080.0 * width), paint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (tutorialEnds) {
            return false;
        }
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                xPos = e.getX(); //Get the x position
                yPos = e.getY(); //Get the y position
                boolean origTurn = guide3.getBlackTurn();
                guide3.move((int)((xPos - 7.0 / 1080.0 * width) / gridSize), (int)((yPos - 7.0 / 1080.0 * width) / gridSize));
                if(guide3.getBlackTurn() != origTurn){
                    numOfSteps++;
                    int liberty = guide3.gameBoard.getLiberty(9, 9);
                    //check if white pieces are already removed
                    if(liberty == 0){
                        tutorialEnds = true;
                    }
                    else{
                        //get the computer position
                        int[] computerPosition = guide3.extendLiberty(9, 9);
                        int comX = computerPosition[0];
                        int comY = computerPosition[1];
                        //computer moves
                        guide3.move(comX, comY);
                        //check new total liberties
                        int newLiberty = guide3.gameBoard.getLiberty(9, 9);
                        if(newLiberty > 2){
                            tutorialEnds = true;
                            correctAnswer = false;
                        }
                        if(numOfSteps > 4){
                            tutorialEnds = true;
                            correctAnswer = false;
                        }
                    }
                }
                invalidate();
                break;
        }
        return true;
    }
}//End of class
