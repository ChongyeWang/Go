package app.com.example.chong.go.GameBuild;

/**
 *  This class implements the board.
 */

public class Board {
    private int row;
    private int column;
    public char board[][];

    /**
     * Constructor for board
     * @param row
     * @param column
     */
    public Board(int row, int column){
        this.row = row;
        this.column = column;
        this.board = new char[row][column];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                this.board[i][j] = 'E';
            }
        }
    }


    /**
     * Copy constructor for board
     * @param other
     */
    public Board(Board other){
        this.row = other.row;
        this.column = other.column;
        this.board = new char[row][column];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                this.board[i][j] = other.board[i][j];
            }
        }
    }


    /**
     * Get the space on the board
     * @param x
     * @param y
     * @return
     */
    public char getSpace(int x, int y){
        return this.board[y][x];
    }


    /**
     * Set the space on the board
     * @param x
     * @param y
     * @param input
     */
    public void setSpace(int x, int y, char input){
        this.board[y][x] = input;
    }


    /**
     * Set the row of board
     * @param row
     */
    public void setRow(int row){
        this.row = row;
    }


    /**
     * Get the row of board
     * @return
     */
    public int getRow(){
        return this.row;
    }


    /**
     * Set the column of board
     * @param column
     */
    public void setColumn(int column){
        this.column = column;
    }


    /**
     * Get the column of board
     * @return
     */
    public int getColumn(){
        return this.column;
    }


    /**
     * Get the number of black
     * pieces on the board
     * @return
     */
    public int getBlackNum(){
        int sum = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                if(board[i][j] == 'B'){
                    sum++;
                }
            }
        }
        return sum;
    }


    /**
     * Get the number of white
     * pieces on the board
     * @return
     */
    public int getWhiteNum(){
        int sum = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                if(board[i][j] == 'W'){
                    sum++;
                }
            }
        }
        return sum;
    }


    /**
     * Get the number of empty
     * spaces on the board
     * @return
     */
    public int getEmptyNum(){
        int sum = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                if(board[i][j] == 'E'){
                    sum++;
                }
            }
        }
        return sum;
    }


    /**
     * Check whether two board are the same
     * @param other
     * @return
     */
    public boolean sameBoard(Board other){
        if(this.row != other.row || this.column != other.column){
            return false;
        }
        for(int i = 0; i < this.row; i++){
            for(int j = 0; j < this.column; j++){
                if(this.board[i][j] != other.board[i][j]){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Get the total liberties of the group of the
     * given piece coordinate
     * @param x
     * @param y
     * @return
     */
    public int getLiberty(int x, int y){
        if(board[y][x] != 'B' && board[y][x] != 'W'){
            return 0;
        }
        char origColor = board[y][x];//original color
        int retval = getLibertyHelper(x, y, origColor);

        //Change back to original
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(board[i][j] == 'P'){
                    board[i][j] = origColor;
                }
                if(board[i][j] == 'e'){
                    board[i][j] = 'E';
                }
            }
        }
        return retval;
    }


    /**
     * Helper method for getLiberty method
     * @param x
     * @param y
     * @param origColor
     * @return
     */
    private int getLibertyHelper(int x, int y, char origColor){
        if(x < 0 || y < 0 || x > 18 || y > 18){
            return 0;
        }//out of bound

        if(board[y][x] != origColor){
            return 0;
        }//not target color

        int sum = 0;//track the sum of liberties

        if(x - 1 >= 0 && board[y][x - 1] == 'E'){
            sum++;
            board[y][x - 1] = 'e';
        }//check left space
        if(x + 1 <= 18 && board[y][x + 1] == 'E'){
            sum++;
            board[y][x + 1] = 'e';
        }//check right space
        if(y - 1 >= 0 && board[y - 1][x] == 'E'){
            sum++;
            board[y - 1][x] = 'e';
        }//check down space
        if(y + 1 <= 18 && board[y + 1][x] == 'E'){
            sum++;
            board[y + 1][x] = 'e';
        }//check up space

        board[y][x] = 'P';//mark as checked

        sum += getLibertyHelper(x - 1, y, origColor);//Left
        sum += getLibertyHelper(x + 1, y, origColor);//Right
        sum += getLibertyHelper(x, y - 1, origColor);//Down
        sum += getLibertyHelper(x, y + 1, origColor);//Up

        return sum;
    }

}//End of Class
