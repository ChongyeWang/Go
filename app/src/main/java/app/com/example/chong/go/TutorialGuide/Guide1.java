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

import static app.com.example.chong.go.TutorialGuide.Guide1.guide1;

/**
 * This class provides the user with the
 * eating pieces tutorial.
 */
public class Guide1 extends AppCompatActivity {

    protected static Game guide1 = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawGuide1(this));

        /*Set up guide1*/
        guide1.gameBoard.board[9][8] = 'B';
        guide1.gameBoard.board[9][10] = 'B';
        guide1.gameBoard.board[10][9] = 'B';
        guide1.gameBoard.board[9][9] = 'W';
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        guide1 = new Game();
        return;
    }
}


/**
 * This class implements the guide1 view.
 */

class DrawGuide1 extends View {
    private float xPos = -1;
    private float yPos = -1;

    private int numOfSteps = 0;
    private boolean tutorialEnds = false;

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
    private Bitmap scaledBlack = Bitmap.createScaledBitmap(black, (int)(width / 19 - width / 200), (int)(width / 19 - width / 200), true);
    private Bitmap scaledWhite = Bitmap.createScaledBitmap(white, (int)(width / 19 - width / 200), (int)(width / 19 - width / 200), true);

    /**
     * DrawGame constructor
     * @param context
     */
    public DrawGuide1(Context context){
        super(context);
    }

    protected void onDraw(Canvas canvas){
        //Draw the board
        canvas.drawBitmap(scaledBoard, 0, 0, null);

        //Draw the pieces
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(guide1.gameBoard.board[i][j] != 'E'){
                    if(guide1.gameBoard.board[i][j] == 'B'){
                        canvas.drawBitmap(scaledBlack, (float)(j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                    }
                    if(guide1.gameBoard.board[i][j] == 'W'){
                        canvas.drawBitmap(scaledWhite, (float)(j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                    }
                }
            }
        }

        /*Draw text to canvas*/
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        canvas.drawText("Goal : Remove The White", 0, (float)(width + 100.0 / 1080.0 * width), paint);
        canvas.drawText("Answer : ", 0, (float)(width + 200.0 / 1080.0 * width), paint);

        /*Check Answer*/
        if(numOfSteps == 1) {
            if ((int) ((xPos - 7.0 / 1080.0 * width) / gridSize) == 9 && (int) ((yPos - 7.0 / 1080.0 * width) / gridSize) == 8) {
                paint.setColor(Color.GREEN);
                canvas.drawText("Correct", (float) (250.0 / 1080.0 * width), (float) (width + 200.0 / 1080.0 * width), paint);
            } else {
                paint.setColor(Color.RED);
                canvas.drawText("Wrong", (float) (250.0 / 1080.0 * width), (float) (width + 200.0 / 1080.0 * width), paint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent e){
        if(tutorialEnds){
            return false;
        }
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                xPos = e.getX(); //Get the x position
                yPos = e.getY(); //Get the y position
                boolean origTurn = guide1.getBlackTurn();
                guide1.move((int)((xPos - 7.0 / 1080.0 * width) / gridSize), (int)((yPos - 7.0 / 1080.0 * width) / gridSize));
                if(guide1.getBlackTurn() != origTurn){
                    numOfSteps++;
                    tutorialEnds = true;
                }
                invalidate();
                break;
        }
        return true;
    }
}//End of Class