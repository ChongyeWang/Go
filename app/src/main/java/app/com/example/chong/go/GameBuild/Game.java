package app.com.example.chong.go.GameBuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * This class implements the move of pieces.
 */

public class Game {
    private boolean blackTurn;
    private String winner;

    private Stack<Game> blackStack;
    private Stack<Game> whiteStack;

    public Board gameBoard;
    public Board countingBoard;
    public Board evaluatingBoard;

    private int[][] evaluating;

    /**
     * Game constructor
     */
    public Game(){
        this.blackTurn = true;
        this.winner = "";
        this.blackStack = new Stack<>();
        this.whiteStack = new Stack<>();
        this.gameBoard = new Board(19, 19);
        this.countingBoard = new Board(19, 19);
        this.evaluatingBoard = new Board(19, 19);
        this.evaluating = new int[19][19];
    }


    /**
     * Copy constructor for game
     * @param other
     */
    public Game(Game other){
        copy(other);
    }


    /**
     * Copy from another game object
     * @param other
     */
    public void copy(Game other){
        this.blackTurn = other.blackTurn;
        this.winner = other.winner;
        this.gameBoard = new Board(other.gameBoard);
        this.countingBoard = new Board(other.countingBoard);
        this.evaluatingBoard = new Board(other.evaluatingBoard);
        this.evaluating = new int[19][19];
    }


    /**
     * Check whether the current turn
     * is black turn
     * @return
     */
    public boolean getBlackTurn(){
        return this.blackTurn;
    }


    /**
     * Set the black turn
     * @param turn
     */
    public void setBlackTurn(boolean turn){
        this.blackTurn = turn;
    }


    /**
     * Get the black stack of Game
     * @return
     */
    public Stack<Game> getBlackStack(){
        return this.blackStack;
    }


    /**
     * Get the white stack of Game
     * @return
     */
    public Stack<Game> getWhiteStack(){
        return this.whiteStack;
    }


    /**
     * Move piece method
     * @param x
     * @param y
     */
    public void move(int x, int y){
        //Return if target place if not empty
        if(checkTargetPlace(x, y) == false){
            return;
        }

        //Black Turn
        if(blackTurn == true){
            //Create copy of original Game
            Game origGame = new Game(this);
            boolean origTurn = blackTurn;

            //Get the original number of white piece
            int origWhiteNum = this.gameBoard.getWhiteNum();

            //Put the black piece on the board
            this.gameBoard.board[y][x] = 'B';
            moveHelper(gameBoard.board, 'W');

            //Get the new number of white piece
            int newWhiteNum = this.gameBoard.getWhiteNum();

            //Check ko
            if(blackStack.isEmpty() == false){
                if(blackStack.peek().gameBoard.sameBoard(this.gameBoard)){
                    this.copy(origGame);//Invalid move, change back to original
                    return;
                }
            }

            //If there are white pieces that are removed, the black move is valid
            if(origWhiteNum != newWhiteNum){
                blackTurn = !blackTurn;
                return;
            }

            //Get the number of black piece
            int origBlackNum = this.gameBoard.getBlackNum();
            moveHelper(gameBoard.board, 'B');

            //Get the number of black piece
            int newBlackNum = this.gameBoard.getBlackNum();

            //If the number of black decreases, then there are some black pieces cannot exist on the board
            if(newBlackNum < origBlackNum){
                this.copy(origGame);//Invalid move, change back to original
                return;
            }
            else{
                blackTurn = !blackTurn;
            }

            //Valid move Push onto stack
            if(blackTurn != origTurn){
                blackStack.push(new Game(this));
            }
        }
        //White Turn
        else {
            //Create copy of original Game
            Game origGame = new Game(this);
            boolean origTurn = blackTurn;

            //Get the original number of black piece
            int origBlackNum = this.gameBoard.getBlackNum();

            //Put the white piece on the board
            this.gameBoard.board[y][x] = 'W';
            moveHelper(gameBoard.board, 'B');

            //Get the new number of black piece
            int newBlackNum = this.gameBoard.getBlackNum();

            //Check ko
            if(whiteStack.isEmpty() == false){
                if(whiteStack.peek().gameBoard.sameBoard(this.gameBoard)){
                    this.copy(origGame);//Invalid move, change back to original
                    return;
                }
            }

            //If there are black pieces that are removed, the white move is valid
            if(origBlackNum != newBlackNum){
                blackTurn = !blackTurn;
                return;
            }

            //Get the number of white piece
            int origWhiteNum = this.gameBoard.getWhiteNum();
            moveHelper(gameBoard.board, 'W');

            //Get the number of white piece
            int newWhiteNum = this.gameBoard.getWhiteNum();

            //If the number of white decreases, then there are white pieces cannot exist on the board
            if(newWhiteNum < origWhiteNum){
                this.copy(origGame);//Invalid move, change back to original
                return;
            }
            else{
                blackTurn = !blackTurn;
            }

            //Valid move Push onto stack
            if(blackTurn != origTurn){
                whiteStack.push(new Game(this));
            }
        }

    }//End of move method


    /**
     * Helper method for move method
     * This method combines floodFill method, changeColorBack
     *  method and removePiece method
     * @param board
     * @param colorTobeSet
     */
    private void moveHelper(char[][] board, char colorTobeSet){
        this.floodFill(board, colorTobeSet);
        this.changeColorBack(board, colorTobeSet);
        this.removePiece(board);
    }


    /**
     * Check whether the target place is empty
     * @param x
     * @param y
     * @return
     */
    public boolean checkTargetPlace(int x, int y){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return false;
        }//out of bound
        if(gameBoard.board[y][x] != 'E'){
            return false;
        }//already occupied
        return true;
    }


    /**
     * FloodFill algorithm for removing target piece
     * @param board
     * @param colorTobeSet
     */
    public void floodFill(char[][] board, char colorTobeSet){
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(board[i][j] == colorTobeSet){
                    floodFillHelper(board, j, i, colorTobeSet);
                }
            }
        }
    }//End of floodFill Method


    /**
     * Helper method for floodFill method
     * @param board
     * @param x
     * @param y
     * @param colorTobeSet
     */
    private void floodFillHelper(char[][] board, int x, int y, char colorTobeSet){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return;
        }//out of range
        if(board[y][x] != colorTobeSet){
            return;
        }//not target piece
        if(x - 1 >= 0 && board[y][x - 1] == 'E'
                || x + 1 <= 18 && board[y][x + 1] == 'E'
                || y - 1 >= 0 && board[y - 1][x] == 'E'
                || y + 1 <= 18 && board[y + 1][x] == 'E'){
            board[y][x] = 'R';// Later we will change this part of piece back to its color
            return;
        }

        board[y][x] = 'P';//Pending(To be decided)

        floodFillHelper(board, x - 1, y, colorTobeSet);//Left
        floodFillHelper(board, x + 1, y, colorTobeSet);//Right
        floodFillHelper(board, x, y - 1, colorTobeSet);//Down
        floodFillHelper(board, x, y + 1, colorTobeSet);//Up
    }


    /**
     * This method will change the group of pieces with 'R'(this group of
     * pieces can still exists on the board) back to its original color
     * @param board
     * @param origColor
     */
    public void changeColorBack(char[][] board, char origColor){
        for(int i = 0; i <= 18; i++){
            for(int j = 0; j <= 18; j++){
                if(board[i][j] == 'R'){
                    changeColorBackHelper(board, j, i, origColor);
                }
            }
        }
    }


    /**
     * Helper method for changeColorBack method
     * @param x
     * @param y
     * @param origColor
     */
    private void changeColorBackHelper(char[][] board, int x, int y, char origColor){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return;
        }//out of range
        if(board[y][x] != 'P' && board[y][x] != 'R'){
            return;
        }//not target piece

        board[y][x] = origColor;//change back to original color

        changeColorBackHelper(board, x - 1, y, origColor);//Left
        changeColorBackHelper(board, x + 1, y, origColor);//Right
        changeColorBackHelper(board, x, y - 1, origColor);//Down
        changeColorBackHelper(board, x, y + 1, origColor);//Up
    }


    /**
     * This method will remove pieces that have
     * already been eaten
     * @param board
     */
    public void removePiece(char[][] board){
        for(int i = 0; i <= 18; i++){
            for(int j = 0; j <= 18; j++){
                if(board[i][j] == 'P'){
                    board[i][j] = 'E';
                }
            }
        }
    }


    /**
     * Get the position that will extend the
     * total number of liberties of the current group
     * of the given position
     * @param x
     * @param y
     * @return
     */
    public int[] extendLiberty(int x, int y){
        //Get the current total liberties
        int[] currMaxLiberty = new int[1];
        currMaxLiberty[0] = gameBoard.getLiberty(x, y);

        //Keep track of the x and y position
        int[] bestPosition = new int[2];
        bestPosition[0] = -1;
        bestPosition[1] = -1;

        if(gameBoard.board[y][x] != 'B' && gameBoard.board[y][x] != 'W'){
            return bestPosition;
        }

        //Get original color
        char origColor = gameBoard.board[y][x];

        //19 * 19 char array, mark space checked already
        char[][] checked = new char[19][19];

        extendLibertyHelper(x, y, origColor, currMaxLiberty, bestPosition, checked);

        return bestPosition;
    }


    /**
     * Helper function for extendLiberty method
     * @param x
     * @param y
     * @param origColor
     * @param currMaxLiberty
     * @param bestPostion
     * @param checked
     */
    private void extendLibertyHelper(int x, int y, char origColor, int[] currMaxLiberty, int[] bestPostion, char[][] checked){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return;
        }//out of range
        if(gameBoard.board[y][x] != origColor){
            return;
        }//not target color
        if(checked[y][x] == 1){
            return;
        }//already checked

        if(x - 1 >= 0 && gameBoard.board[y][x - 1] == 'E'){
            gameBoard.board[y][x - 1] = origColor;
            int liberty = gameBoard.getLiberty(x, y);
            if(liberty >= currMaxLiberty[0]){
                bestPostion[0] = x - 1;
                bestPostion[1] = y;
                currMaxLiberty[0] = liberty;
            }
            gameBoard.board[y][x - 1] = 'E';
        }//check left space
        if(x + 1 <= 18 && gameBoard.board[y][x + 1] == 'E'){
            gameBoard.board[y][x + 1] = origColor;
            int liberty = gameBoard.getLiberty(x, y);
            if(liberty >= currMaxLiberty[0]){
                bestPostion[0] = x + 1;
                bestPostion[1] = y;
                currMaxLiberty[0] = liberty;
            }
            gameBoard.board[y][x + 1] = 'E';
        }//check right space
        if(y - 1 >= 0 && gameBoard.board[y - 1][x] == 'E'){
            gameBoard.board[y - 1][x] = origColor;
            int liberty = gameBoard.getLiberty(x, y);
            if(liberty >= currMaxLiberty[0]){
                bestPostion[0] = x;
                bestPostion[1] = y - 1;
                currMaxLiberty[0] = liberty;
            }
            gameBoard.board[y - 1][x] = 'E';
        }//check down space
        if(y + 1 <= 18 && gameBoard.board[y + 1][x] == 'E'){
            gameBoard.board[y + 1][x] = origColor;
            int liberty = gameBoard.getLiberty(x, y);
            if(liberty >= currMaxLiberty[0]){
                bestPostion[0] = x;
                bestPostion[1] = y + 1;
                currMaxLiberty[0] = liberty;
            }
            gameBoard.board[y + 1][x] = 'E';
        }//check up space

        checked[y][x] = 1;

        extendLibertyHelper(x - 1, y, origColor, currMaxLiberty, bestPostion, checked);//Left
        extendLibertyHelper(x + 1, y, origColor, currMaxLiberty, bestPostion, checked);//Right
        extendLibertyHelper(x, y - 1, origColor, currMaxLiberty, bestPostion, checked);//Down
        extendLibertyHelper(x, y + 1, origColor, currMaxLiberty, bestPostion, checked);//Up
    }


    /**
     * This method will be used the check whether a space
     * is a mutual life space
     * Note : This method should be used when counting area
     * @param x
     * @param y
     * @return
     */
    public boolean mutualLife(int x, int y){
        if(gameBoard.board[y][x] != 'E'){
            return false;
        }
        boolean accessBlack[] = new boolean[1];
        boolean accessWhite[] = new boolean[1];
        accessBlack[0] = false;
        accessWhite[0] = false;
        mutualLifeHelper(x, y, accessBlack, accessWhite);

        //Change back to empty
        for(int i = 0; i <= 18; i++){
            for(int j = 0; j <= 18; j++){
                if(gameBoard.board[i][j] == 'e'){
                    gameBoard.board[i][j] = 'E';
                }
            }
        }
        return accessBlack[0] == true && accessWhite[0] == true;
    }


    /**
     * Helper method for mutualLife function
     * @param x
     * @param y
     * @param accessBlack
     * @param accessWhite
     */
    private void mutualLifeHelper(int x, int y, boolean[] accessBlack, boolean[] accessWhite){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return ;
        }//out of bound
        if(gameBoard.board[y][x] != 'E'){
            return;
        }

        //Check black access
        if(x - 1 >= 0 && gameBoard.board[y][x - 1] == 'B'){
            accessBlack[0] = true;
        }//check left space
        if(x + 1 <= 18 && gameBoard.board[y][x + 1] == 'B'){
            accessBlack[0] = true;
        }//check right space
        if(y - 1 >= 0 && gameBoard.board[y - 1][x] == 'B'){
            accessBlack[0] = true;
        }//check down space
        if(y + 1 <= 18 && gameBoard.board[y + 1][x] == 'B'){
            accessBlack[0] = true;
        }//check up space

        //Check white access
        if(x - 1 >= 0 && gameBoard.board[y][x - 1] == 'W'){
            accessWhite[0] = true;
        }//check left space
        if(x + 1 <= 18 && gameBoard.board[y][x + 1] == 'W'){
            accessWhite[0] = true;
        }//check right space
        if(y - 1 >= 0 && gameBoard.board[y - 1][x] == 'W'){
            accessWhite[0] = true;
        }//check down space
        if(y + 1 <= 18 && gameBoard.board[y + 1][x] == 'W'){
            accessWhite[0] = true;
        }//check up space

        gameBoard.board[y][x] = 'e';//mark as checked

        mutualLifeHelper(x - 1, y, accessBlack, accessWhite);//Left
        mutualLifeHelper(x + 1, y, accessBlack, accessWhite);//Right
        mutualLifeHelper(x, y - 1, accessBlack, accessWhite);//Down
        mutualLifeHelper(x, y + 1, accessBlack, accessWhite);//Up
    }


    /**
     * Check whether the group area belongs to black
     * @param x
     * @param y
     * @return
     */
    private boolean blackArea(int x, int y){
        if(gameBoard.board[y][x] != 'E'){
            return false;
        }
        boolean accessBlack[] = new boolean[1];
        accessBlack[0] = false;
        blackAreaHelper(x, y, accessBlack);

        //Change back to empty
        for(int i = 0; i <= 18; i++){
            for(int j = 0; j <= 18; j++){
                if(gameBoard.board[i][j] == 'e'){
                    gameBoard.board[i][j] = 'E';
                }
            }
        }
        return accessBlack[0] == true;
    }


    /**
     * Helper method for blackArea
     * @param x
     * @param y
     * @param accessBlack
     */
    private void blackAreaHelper(int x, int y, boolean[] accessBlack){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return ;
        }//out of bound
        if(gameBoard.board[y][x] != 'E'){
            return;
        }

        if(x - 1 >= 0 && gameBoard.board[y][x - 1] == 'B'){
            accessBlack[0] = true;
            return;
        }//check left space
        if(x + 1 <= 18 && gameBoard.board[y][x + 1] == 'B'){
            accessBlack[0] = true;
            return;
        }//check right space
        if(y - 1 >= 0 && gameBoard.board[y - 1][x] == 'B'){
            accessBlack[0] = true;
            return;
        }//check down space
        if(y + 1 <= 18 && gameBoard.board[y + 1][x] == 'B'){
            accessBlack[0] = true;
            return;
        }//check up space

        gameBoard.board[y][x] = 'e';//mark as checked

        blackAreaHelper(x - 1, y, accessBlack);//Left
        blackAreaHelper(x + 1, y, accessBlack);//Right
        blackAreaHelper(x, y - 1, accessBlack);//Down
        blackAreaHelper(x, y + 1, accessBlack);//Up
    }


    /**
     * Check whether the group area belongs to white
     * @param x
     * @param y
     * @return
     */
    private boolean whiteArea(int x, int y){
        if(gameBoard.board[y][x] != 'E'){
            return false;
        }
        boolean accessWhite[] = new boolean[1];
        accessWhite[0] = false;
        whiteAreaHelper(x, y, accessWhite);

        //Change back to empty
        for(int i = 0; i <= 18; i++){
            for(int j = 0; j <= 18; j++){
                if(gameBoard.board[i][j] == 'e'){
                    gameBoard.board[i][j] = 'E';
                }
            }
        }
        return accessWhite[0] == true;
    }


    /**
     * Helper method for whiteArea
     * @param x
     * @param y
     * @param accessWhite
     */
    private void whiteAreaHelper(int x, int y, boolean[] accessWhite){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return ;
        }//out of bound
        if(gameBoard.board[y][x] != 'E'){
            return;
        }

        if(x - 1 >= 0 && gameBoard.board[y][x - 1] == 'W'){
            accessWhite[0] = true;
            return;
        }//check left space
        if(x + 1 <= 18 && gameBoard.board[y][x + 1] == 'W'){
            accessWhite[0] = true;
            return;
        }//check right space
        if(y - 1 >= 0 && gameBoard.board[y - 1][x] == 'W'){
            accessWhite[0] = true;
            return;
        }//check down space
        if(y + 1 <= 18 && gameBoard.board[y + 1][x] == 'W'){
            accessWhite[0] = true;
            return;
        }//check up space

        gameBoard.board[y][x] = 'e';//mark as checked

        whiteAreaHelper(x - 1, y, accessWhite);//Left
        whiteAreaHelper(x + 1, y, accessWhite);//Right
        whiteAreaHelper(x, y - 1, accessWhite);//Down
        whiteAreaHelper(x, y + 1, accessWhite);//Up
    }


    /**
     * Change the empty space to mutual life area
     * @param x
     * @param y
     */
    private void changeToMutualLifeArea(int x, int y){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return;
        }//out of range

        if(countingBoard.board[y][x] != 'E'){
            return;
        }

        countingBoard.board[y][x] = 'm';

        changeToMutualLifeArea(x - 1, y);//Left
        changeToMutualLifeArea(x + 1, y);//Right
        changeToMutualLifeArea(x, y - 1);//Down
        changeToMutualLifeArea(x, y + 1);//Up
    }


    /**
     * Change the empty space to black area
     * @param x
     * @param y
     */
    private void changeToBlackArea(int x, int y){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return;
        }//out of range

        if(countingBoard.board[y][x] != 'E'){
            return;
        }

        countingBoard.board[y][x] = 'b';

        changeToBlackArea(x - 1, y);//Left
        changeToBlackArea(x + 1, y);//Right
        changeToBlackArea(x, y - 1);//Down
        changeToBlackArea(x, y + 1);//Up
    }


    /**
     * Change the empty space to white area
     * @param x
     * @param y
     */
    private void changeToWhiteArea(int x, int y){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return;
        }//out of range

        if(countingBoard.board[y][x] != 'E'){
            return;
        }

        countingBoard.board[y][x] = 'w';

        changeToWhiteArea(x - 1, y);//Left
        changeToWhiteArea(x + 1, y);//Right
        changeToWhiteArea(x, y - 1);//Down
        changeToWhiteArea(x, y + 1);//Up
    }


    /**
     * Change the empty space to mutual area or
     * black area of white area
     */
    public void counting(){
        this.countingBoard = new Board(this.gameBoard);
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(countingBoard.board[i][j] == 'E'){
                    if(mutualLife(j, i) == true){
                        changeToMutualLifeArea(j, i);
                    }
                    if(blackArea(j, i) == true){
                        changeToBlackArea(j, i);
                    }
                    if(whiteArea(j, i) == true){
                        changeToWhiteArea(j, i);
                    }
                }
            }
        }
    }


    /**
     * This method get the shortest path between an
     * empty space and an occupied space.
     * @param emptyX
     * @param emptyY
     * @param currX
     * @param currY
     * @return
     */
    public int getShortestPath(int emptyX, int emptyY, int currX, int currY){
        if(emptyX < 0 || emptyY < 0 || currX > 18 || currY > 18){
            return -1;
        }
        if(gameBoard.board[emptyY][emptyX] != 'E'){
            return -1;
        }
        if(gameBoard.board[currY][currX] != 'B' && gameBoard.board[currY][currX] != 'W'){
            return -1;
        }

        char oppoColor;
        if(gameBoard.board[currY][currX] == 'B'){
            oppoColor = 'W';
        }
        else{
            oppoColor = 'B';
        }

        //Mark visited space
        boolean visited[][] = new boolean[19][19];

        int distance[][] = new int[19][19];
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        Queue<int[]> q = new LinkedList<>();

        int[] coordinate = new int[2];
        coordinate[0] = currX;
        coordinate[1] = currY;
        q.add(coordinate);

        visited[currY][currX] = true;
        distance[currY][currX] = 0;

        while(q.isEmpty() == false){
            int[] head = q.peek();
            int x = head[0];
            int y = head[1];
            q.poll();
            if(x - 1 >= 0 && gameBoard.board[y][x - 1] != oppoColor && visited[y][x - 1] == false){
                if(distance[y][x - 1] > distance[y][x] + 1){
                    distance[y][x - 1] = distance[y][x] + 1;
                }
                int[] array = new int[2];
                array[0] = x - 1;
                array[1] = y;
                q.add(array);
                visited[y][x - 1] = true;
            }
            if(x + 1 <= 18 && gameBoard.board[y][x + 1] != oppoColor && visited[y][x + 1] == false){
                if(distance[y][x + 1] > distance[y][x] + 1){
                    distance[y][x + 1] = distance[y][x] + 1;
                }
                int[] array = new int[2];
                array[0] = x + 1;
                array[1] = y;
                q.add(array);
                visited[y][x + 1] = true;
            }
            if(y - 1 >= 0 && gameBoard.board[y - 1][x] != oppoColor && visited[y - 1][x] == false){
                if(distance[y - 1][x] > distance[y][x] + 1){
                    distance[y - 1][x] = distance[y][x] + 1;
                }
                int[] array = new int[2];
                array[0] = x;
                array[1] = y - 1;
                q.add(array);
                visited[y - 1][x] = true;
            }
            if(y + 1 <= 18 && gameBoard.board[y + 1][x] != oppoColor && visited[y + 1][x] == false){
                if(distance[y + 1][x] > distance[y][x] + 1){
                    distance[y + 1][x] = distance[y][x] + 1;
                }
                int[] array = new int[2];
                array[0] = x;
                array[1] = y + 1;
                q.add(array);
                visited[y + 1][x] = true;
            }
        }

        return distance[emptyY][emptyX];
    }

    /**
     * Evaluate the board
     */
    public void evaluate(){

        //Map the distance with the weight
        HashMap distance_weight = new HashMap();
        distance_weight.put(1, 32);
        distance_weight.put(2, 16);
        distance_weight.put(3, 8);
        distance_weight.put(4, 4);
        distance_weight.put(5, 2);
        distance_weight.put(6, 1);

        //Calculating the influence
        evaluating = new int[19][19];
        this.evaluatingBoard = new Board(gameBoard);

        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'E'){
                    for(int k = Math.max(0, i - 3); k <= Math.min(18, i + 3); k++){
                        for(int l = Math.max(0, j - 3); l <= Math.min(18, j + 3); l++){
                            if(gameBoard.board[k][l] != 'E'){
                                int distance = getShortestPath(j, i, l, k);
                                if(distance < 4 && distance >= 0){
                                    int weight = (int)distance_weight.get(distance);
                                    if(gameBoard.board[k][l] == 'B'){
                                        evaluating[i][j] = evaluating[i][j] + weight;
                                    }
                                    else{
                                        evaluating[i][j] = evaluating[i][j] - weight;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //Counting the board first
        this.counting();

        //Evaluating board
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(countingBoard.board[i][j] == 'B'){
                    evaluatingBoard.board[i][j] = 'B';
                }//Black piece
                else if(countingBoard.board[i][j] == 'W'){
                    evaluatingBoard.board[i][j] = 'W';
                }//White piece
                else if(countingBoard.board[i][j] == 'b'){
                    evaluatingBoard.board[i][j] = 'b';
                }//Black area
                else if(countingBoard.board[i][j] == 'w'){
                    evaluatingBoard.board[i][j] = 'w';
                }//White area
                else{
                    //Black area
                    if(evaluating[i][j] > 0){
                        evaluatingBoard.board[i][j] = 'b';
                    }
                    //White area
                    if(evaluating[i][j] < 0){
                        evaluatingBoard.board[i][j] = 'w';
                    }
                }
            }
        }
    }


    /**
     * This method implements the AI
     */
    public void AI(){
        //Always white turn

        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < 361; i++){
            map.put(i, 0);
        }

        //Evaluate radiation
        Game originalGame = new Game(this);
        originalGame.evaluate();
        int score = 0;
        for(int y = 0; y < 19; y++) {
            for (int x = 0; x < 19; x++) {
                if(originalGame.evaluatingBoard.board[y][x] == 'w' || originalGame.evaluatingBoard.board[y][x] == 'W'){
                    if((y <= 3 && x <= 3) || (y <= 3 && x >= 15) || (x <= 3 && y >= 15) || (x >= 15 && y >= 15)){
                        score = score + 3;
                    }
                    else if((x > 3 && y > 3) || (x < 15 && y < 15)){
                        score = score + 1;
                    }
                    else{
                        score = score + 2;
                    }
                }
            }
        }


        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                Game orig = new Game(this);
                if(orig.gameBoard.board[i][j] == 'E') {
                    orig.gameBoard.board[i][j] = 'W';
                    if(orig.gameBoard.getLiberty(j, i) == 0){
                        continue;
                    }
                    orig.evaluate();
                    int newscore = 0;
                    for(int y = 0; y < 19; y++) {
                        for (int x = 0; x < 19; x++) {
                            if(orig.evaluatingBoard.board[y][x] == 'w'|| orig.evaluatingBoard.board[y][x] == 'W'){
                                if((y <= 3 && x <= 3) || (y <= 3 && x >= 15) || (x <= 3 && y >= 15) || (x >= 15 && y >= 15)){
                                    newscore = newscore + 3;
                                }
                                else if((x > 3 && y > 3) || (x < 15 && y < 15)){
                                    newscore = newscore + 1;
                                }
                                else{
                                    newscore = newscore + 2;
                                }
                            }
                        }
                    }
                    //Area is increased
                    if(newscore - score > 0){
                        int index = i * 19 + j;
                        map.put(index, map.get(index) + (newscore - score));
                    }
                }
            }
        }

        //Remove Black
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'E') {
                    Game origGame = new Game(this);
                    int origBlackNum = origGame.gameBoard.getBlackNum();
                    origGame.gameBoard.board[i][j] = 'W';
                    moveHelper(origGame.gameBoard.board, 'B');
                    int newBlackNum = origGame.gameBoard.getBlackNum();
                    if (newBlackNum < origBlackNum) {//some black pieces are removed
                        int index = i * 19 + j;
                        map.put(index, map.get(index) + 15);
                    }
                }
            }
        }

        //extend liberty
        boolean[][] checked = new boolean[19][19];
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'W') {
                    int whiteLiberty = gameBoard.getLiberty(j, i);
                    //white group that in danger
                    if(whiteLiberty <= 3){
                        int[] computerPosition = this.extendLiberty(j, i);
                        int comX = computerPosition[0];
                        int comY = computerPosition[1];
                        if(comX != -1 && comY != -1 && checked[comY][comX] == false){
                            checked[comY][comX] = true;
                            int index = comY * 19 + comX;
                            map.put(index, map.get(index) + 10);
                        }
                    }
                }
            }
        }

        //Disconnect black
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'E'){
                    if(disconnectBlack(i, j) == true){
                        int index = i * 19 + j;
                        map.put(index, map.get(index) + 10);
                    }

                }
            }
        }

        //Connect white
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'E'){
                    if(connectWhite(i, j)){
                        int index = i * 19 + j;
                        map.put(index, map.get(index) + 15);
                    }
                }
            }
        }

        ArrayList<Integer> mapValue = new ArrayList<>();
        for(int i = 0; i < 361; i++){
            mapValue.add(map.get(i));
        }

        //Sort the arraylist
        Collections.sort(mapValue);
        Collections.reverse(mapValue);

        boolean originalTurn = this.getBlackTurn();

        for(int i = 0; i < 361; i++){
            int value = mapValue.get(i);
            for(int j = 0; j < 361; j++){
                if(map.get(j) == value){
                    int y = j / 19;
                    int x = j % 19;
                    this.move(x, y);
                    if(this.getBlackTurn() != originalTurn){
                        return;
                    }
                    else{
                        break;
                    }
                }
            }
        }

    }//End of AI method


    /**
     * This method returns whether the give coordinate
     * connects the white group
     * @param i
     * @param j
     * @return
     */
    private boolean connectWhite(int i, int j){
        int origGroupNum = this.whiteGroupNum();
        gameBoard.board[i][j] = 'W';
        int newGroupNum = this.whiteGroupNum();
        gameBoard.board[i][j] = 'E';
        if(newGroupNum < origGroupNum){
            return true;
        }
        return false;
    }


    /**
     * This method returns whether the given coordinate
     * will disconnect the black group
     * @param i
     * @param j
     * @return
     */
    private boolean disconnectBlack(int i, int j){
        return connectBlack(i, j);
    }

    /**
     * This method returns whether the give coordinate
     * connects the black group
     * @param i
     * @param j
     * @return
     */
    private boolean connectBlack(int i, int j){
        int origGroupNum = this.blackGroupNum();
        gameBoard.board[i][j] = 'B';
        int newGroupNum = this.blackGroupNum();
        gameBoard.board[i][j] = 'E';
        if(newGroupNum < origGroupNum){
            return true;
        }
        return false;
    }


    /**
     * This method counts the number of white group
     * @return
     */
    public int whiteGroupNum(){
        int num = 0;
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'W'){
                    num++;
                    whiteGroupNumHelper(j, i);
                }
            }
        }

        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'P'){
                    gameBoard.board[i][j] = 'W';
                }
            }
        }

        return num;
    }


    /**
     * Helper method for whiteGroupNum method
     * @param x
     * @param y
     */
    private void whiteGroupNumHelper(int x, int y){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return;
        }//out of range

        if(gameBoard.board[y][x] != 'W'){
            return;
        }

        gameBoard.board[y][x] = 'P';

        whiteGroupNumHelper(x - 1, y);
        whiteGroupNumHelper(x + 1, y);
        whiteGroupNumHelper(x, y - 1);
        whiteGroupNumHelper(x, y + 1);
    }


    /**
     * This method counts the number of black group
     * @return
     */
    public int blackGroupNum(){
        int num = 0;
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'B'){
                    num++;
                    blackGroupNumHelper(j, i);
                }
            }
        }

        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(gameBoard.board[i][j] == 'P'){
                    gameBoard.board[i][j] = 'B';
                }
            }
        }

        return num;
    }


    /**
     * Helper method for blackGroupNum
     * @param x
     * @param y
     */
    private void blackGroupNumHelper(int x, int y){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return;
        }//out of range

        if(gameBoard.board[y][x] != 'B'){
            return;
        }

        gameBoard.board[y][x] = 'P';

        blackGroupNumHelper(x - 1, y);
        blackGroupNumHelper(x + 1, y);
        blackGroupNumHelper(x, y - 1);
        blackGroupNumHelper(x, y + 1);
    }

}//End of Game class