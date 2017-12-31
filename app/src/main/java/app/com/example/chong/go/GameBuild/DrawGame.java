package app.com.example.chong.go.GameBuild;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import app.com.example.chong.go.R;

import static app.com.example.chong.go.StartGame.game;

/**
 * This class implements the game view.
 */

public class DrawGame extends View {
    private float xPos = -1;
    private float yPos = -1;

    private boolean countingPressed = false;
    private boolean evaluatingPressed = false;

    /* Get the dimension of the screen */
    private DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    private float width = metrics.widthPixels;
    private float height = metrics.heightPixels;

    private double gridSize = (width - (65.0 / 1080.0) * width) / 18.0;

    /* Get the image from the drawable */
    private Bitmap board = BitmapFactory.decodeResource(getResources(), R.drawable.board);
    private Bitmap black = BitmapFactory.decodeResource(getResources(), R.drawable.black);
    private Bitmap white = BitmapFactory.decodeResource(getResources(), R.drawable.white);
    private Bitmap counting = BitmapFactory.decodeResource(getResources(), R.drawable.counting);
    private Bitmap evaluating = BitmapFactory.decodeResource(getResources(), R.drawable.evaluating);
    private Bitmap blackArea = BitmapFactory.decodeResource(getResources(), R.drawable.blackarea);
    private Bitmap whiteArea = BitmapFactory.decodeResource(getResources(), R.drawable.whitearea);

    /* Scale the image */
    private Bitmap scaledBoard = Bitmap.createScaledBitmap(board, (int)width, (int)width, true);
    private Bitmap scaledBlack = Bitmap.createScaledBitmap(black, (int)(width / 19 - width / 200), (int)(width / 19 - width / 200), true);
    private Bitmap scaledWhite = Bitmap.createScaledBitmap(white, (int)(width / 19 - width / 200), (int)(width / 19 - width / 200), true);
    private Bitmap scaledCounting = Bitmap.createScaledBitmap(counting, (int)(width / 10), (int)(width / 10), true);
    private Bitmap scaledEvaluating = Bitmap.createScaledBitmap(evaluating, (int)(width / 10), (int)(width / 10), true);
    private Bitmap scaledBlackArea = Bitmap.createScaledBitmap(blackArea, (int)(width / 19 - width / 200), (int)(width / 19 - width / 200), true);
    private Bitmap scaledWhiteArea = Bitmap.createScaledBitmap(whiteArea, (int)(width / 19 - width / 200), (int)(width / 19 - width / 200), true);

    /**
     * DrawGame constructor
     * @param context
     */
    public DrawGame(Context context){
        super(context);
    }

    protected void onDraw(Canvas canvas){
        //Draw the board
        canvas.drawBitmap(scaledBoard, 0, 0, null);
        canvas.drawBitmap(scaledCounting, width * 9 / 10 - width / 100, width + width / 100, null);
        canvas.drawBitmap(scaledEvaluating, width * 8 / 10 - width / 100 - width / 100, width + width / 100, null);

        if(countingPressed == true){
            game.counting();
            for(int i = 0; i < 19; i++){
                for(int j = 0; j < 19; j++){
                    if(game.countingBoard.board[i][j] != 'E'){
                        if(game.countingBoard.board[i][j] == 'b'){
                            canvas.drawBitmap(scaledBlackArea, (float)(j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                        }
                        if(game.countingBoard.board[i][j] == 'w'){
                            canvas.drawBitmap(scaledWhiteArea, (float)(j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                        }
                    }
                }
            }

            int blackArea = 0;
            int whiteArea = 0;
            for(int i = 0; i < 19; i++){
                for(int j = 0; j < 19; j++){
                    if(game.countingBoard.board[i][j] == 'W' || game.countingBoard.board[i][j] == 'w'){
                        whiteArea++;
                    }
                    if(game.countingBoard.board[i][j] == 'B' || game.countingBoard.board[i][j] == 'b'){
                        blackArea++;
                    }
                }
            }

            String winner = "";
            if(blackArea - whiteArea > 6.5){
                winner = "Black";
            }
            else{
                winner = "White";
            }

            /*Draw text to canvas*/
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(60);
            canvas.drawText("Please Remove Death Pieces", 0, (float)(width + 100.0 / 1080.0 * width), paint);
            canvas.drawText("Black : " + blackArea, 0, (float)(width + 200.0 / 1080.0 * width), paint);
            canvas.drawText("White : " + whiteArea, 0, (float)(width + 300.0 / 1080.0 * width), paint);
            canvas.drawText("Winner : " + winner, 0, (float)(width + 400.0 / 1080.0 * width), paint);

        }

        if(evaluatingPressed == true){
            game.evaluate();
            for(int i = 0; i < 19; i++){
                for(int j = 0; j < 19; j++){
                    if(game.evaluatingBoard.board[i][j] != 'E'){
                        if(game.evaluatingBoard.board[i][j] == 'b'){
                            canvas.drawBitmap(scaledBlackArea, (float)(j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                        }
                        if(game.evaluatingBoard.board[i][j] == 'w'){
                            canvas.drawBitmap(scaledWhiteArea, (float)(j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                        }
                    }
                }
            }
        }

        //Draw the pieces
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(game.gameBoard.board[i][j] != 'E'){
                    if(game.gameBoard.board[i][j] == 'B'){
                        canvas.drawBitmap(scaledBlack, (float)(j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                    }
                    if(game.gameBoard.board[i][j] == 'W'){
                        canvas.drawBitmap(scaledWhite, (float)(j * gridSize + 8.0 / 1080.0 * width), (float) (i * gridSize + 8.0 / 1080.0 * width), null);
                    }
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent e){
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                xPos = e.getX(); //Get the x position
                yPos = e.getY(); //Get the y position

                //counting button pressed
                if(xPos >= width * 9 / 10 - width / 100 && xPos <= width && yPos >= width + width / 100 && yPos <= width + width / 10 + width / 100){
                    countingPressed = !countingPressed;
                }

                //evaluating button pressed
                if(xPos >= width * 8 / 10 - width / 100  - width / 100 && xPos <= width * 9 / 10 - width / 100 && yPos >= width + width / 100 && yPos <= width + width / 10  + width / 100){
                    evaluatingPressed = !evaluatingPressed;
                }

                game.move((int)((xPos - 7.0 / 1080.0 * width) / gridSize), (int)((yPos - 7.0 / 1080.0 * width) / gridSize));
                invalidate();
                break;
        }
        return true;
    }
}//End of Class