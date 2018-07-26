package ir.ac.kntu;

import java.awt.Point;
import java.io.Serializable;

/**
 * @author SFathi
 */
public class GameBoard implements Serializable {
    class Builder{
        GameBoard build(){
            return new GameBoard(0);
        }
        Builder byWidth(int width){
            GameBoard.this.boardWidth = width;
            return this;
        }
        Builder byLength(int length){
            GameBoard.this.boardLength = length;
            return this;
        }
        Builder byBall(Ball ball){
            GameBoard.this.ball = ball;
            return this;
        }
        Builder setNewCells(){
            checkWidthAndLength();
            GameBoard.this.boardCells = new Cell[boardWidth][boardLength];
            return this;
        }
        void setCell(int rowIndex, int colIndex, Cell cell){
            boardCells[rowIndex][colIndex] = cell;
        }
    }

    private int boardLength;
    private int boardWidth;
    private Cell[][] boardCells;
    private Ball ball;
    private boolean isWon = false;
    private boolean isLost = false;
    private int level;
    private final static int DECREASE_TIME_UNIT = 100;
    private final static int PRIMARY_SIZE_BRICK_ROW = 2;

    /**
     * constructor
     * @param state max time the game permits to continue
     * @param boardLength length of the game's board
     * @param boardWidth width of the game's board
     * @throws NullPointerException when pass null instead of array shapes,
     * or pass not enough shapes
     */
    public GameBoard(int state, int boardWidth, int boardLength){
        this(state);

        this.boardLength = boardLength;
        this.boardWidth = boardWidth;
        checkWidthAndLength();
        boardCells = new Cell[boardWidth][boardLength];

        prepareBoardCells();
    }

    GameBoard(int state){
        if( state<=0 ){
            state = 3;
        }
        ball = new Ball(state, 0, this);
        level = 1;
    }

    private void checkWidthAndLength(){
        if( boardLength<=0 || boardWidth<=0 ){
            throw new NegativeArraySizeException("some problem...\n" +
                    "length : " + boardLength + " width : " + boardWidth);
        }
    }

    /**
     * put the ball in start home
     */
    public void putBallInHome(){
        Point ballIndexes = getIndexes(ball);
        try{
            replaceContent(ballIndexes, null);
        }catch(ArrayIndexOutOfBoundsException e){
            //ball not exist yet(int the start state)
        }

        boardCells[boardWidth-1][(int)Math.floor(boardLength/2.0)].
                setContent(ball);
    }

    private void prepareBoardCells(){
        if( PRIMARY_SIZE_BRICK_ROW + level >= boardWidth - 3 ){
            throw new RuntimeException("Bricks are very close to the ball!");
        }
        if( boardLength<=JavaFXExecutable.getLengthOfRacket() ){
            throw new RuntimeException("Racket can not move!");
        }

        isWon = false;

        for(int i=0 ; i<boardWidth ; i++){
            for(int j=0 ; j<boardLength ; j++){
                boardCells[i][j] = new Cell();
            }
        }

        putBallInHome();
        ball.setImage(ImagesLoader.getImageOf(ball.getClass().getSimpleName()));

        final int upDistanceIndex = 2;
        for(int i=0+upDistanceIndex ;
            i<PRIMARY_SIZE_BRICK_ROW + level + upDistanceIndex ; i++){
            for(int j=0 ; j<boardLength ; j++){
                boardCells[i][j].setContent(BrickFactory.random(this));
            }
        }
    }

    /**
     *
     * @return game's ball object
     */
    public Ball getBall() {
        return ball;
    }

    /**
     *
     * @param goalRowIndex the row index of cell has old value
     * @param goalColIndex the column index of cell has old value
     * @param gameComponent the new value requesting to replace with
     *                      the old one
     * @throws ArrayIndexOutOfBoundsException when request to access
     * an out of bound index
     */
    private void replaceContent(int goalRowIndex,int goalColIndex,
                                GameComponent gameComponent){
        if( boardCells[goalRowIndex][goalColIndex]==null ){
            boardCells[goalRowIndex][goalColIndex] = new Cell();
        }
        boardCells[goalRowIndex][goalColIndex].setContent(gameComponent);
    }

    /**
     * replace content of a cell
     * @param indexes indexes of the goal cell
     * @param gameComponent new game component which should replace with
     *                      old value
     */
    public void replaceContent(Point indexes, GameComponent gameComponent){
        replaceContent(indexes.y, indexes.x, gameComponent);
    }

    /**
     *
     * @param cell the cell want its index in the game's board
     * @return indexes of the input cell,
     * if could not find it return unavailable indexes
     */
    public Point getIndexes(Cell cell){
        int rowIndex = 0;
        int colIndex = 0;
        for( ; rowIndex<boardWidth ; rowIndex++){
            for( ; colIndex<boardLength ; colIndex++){
                if( boardCells[rowIndex][colIndex]==null ){
                    if( cell==null ){
                        return new Point(rowIndex, colIndex);
                    }else{
                        continue;
                    }
                }
                if( boardCells[rowIndex][colIndex].equals(cell) ){
                    return new Point(rowIndex, colIndex);
                }
            }
        }

        return new Point(rowIndex, colIndex);
    }

    /**
     *
     * @param gameComponent the gameComponent want its index
     *                      in the game's board
     * @return indexes of the input gameComponent,
     * if could not find it return unavailable indexes
     */
    public Point getIndexes(GameComponent gameComponent){
        int rowIndex;
        int colIndex = 0;
        for(rowIndex=0 ; rowIndex<boardWidth ; rowIndex++){
            for(colIndex=0 ; colIndex<boardLength ; colIndex++){
                if( boardCells[rowIndex][colIndex]==null ||
                        boardCells[rowIndex][colIndex].getContent()==null ){
                    if( gameComponent==null ){
                        return new Point(colIndex, rowIndex);
                    }else{
                        continue;
                    }
                }
                if( boardCells[rowIndex][colIndex].getContent().
                        equals(gameComponent) ){
                    return new Point(colIndex, rowIndex);
                }
            }
        }

        return new Point(colIndex, rowIndex);
    }

    /**
     *
     * @param index index of cell you want
     * @return the cell in the input index
     * @throws ArrayIndexOutOfBoundsException when the index is out of bound
     */
    /**
     *
     * @param rowIndex row index of cell you want
     * @param colIndex column index of cell you want
     * @return the cell in the input indexes
     * @throws ArrayIndexOutOfBoundsException when the indexes is out of bound
     */
    private Cell getCell(int rowIndex, int colIndex){
        return boardCells[rowIndex][colIndex];
    }

    /**
     *
     * @param indexes indexes of cell you want
     * @return the cell in the input indexes
     * @see GameBoard#getCell(int, int)
     */
    public Cell getCell(Point indexes){
        return getCell(indexes.y, indexes.x);
    }

    /**
     *
     * @return {@code true} if gameBoard was empty from bricks
     * and otherwise {@code false}
     */
    public boolean isWon() {
        return isWon;
    }

    /**
     *
     * @return {@code true} if ball's life <= 0
     * and otherwise {@code false}
     */
    public boolean isLost() {
        return isLost;
    }

    private Cell[][] cloneBoard(){
        Cell[][] cloned = new Cell[boardWidth][boardLength];
        for(int i=0 ; i<boardWidth ; i++){
            for(int j=0 ; j<boardLength ; j++){
                if( boardCells[i][j]==null ){
                    continue;
                }
                cloned[i][j] = (Cell)boardCells[i][j].clone();
            }
        }

        return cloned;
    }

    /**
     *
     * @return cloned board after a step
     * @throws RuntimeException if bull is not alive
     * @see GameComponent#isAlive()
     */
    public Cell[][] nextStep(){
        if( ! ball.isAlive() ){
            throw new RuntimeException("Ball Is Dead!");
        }

        ball.move();
        checkWinState();
        checkLoseState();

        //create a copy; don't share the board.
        return cloneBoard();
    }

    private void checkWinState(){
        for(int i=0 ; i<boardWidth ; i++){
            for(int j=0 ; j<boardLength ; j++){
                try{
                    if( boardCells[i][j].getContent() instanceof Brick ){
                        isWon = false;
                        return;
                    }
                }catch(NullPointerException e){
                    continue;
                }

            }
        }

        isWon = true;
        level++;
        ball.decreaseDelayTime(DECREASE_TIME_UNIT);
        prepareBoardCells();
    }

    private void checkLoseState(){
        isLost = !ball.isAlive();
    }

    /**
     *
     * @return board's length (number of columns)
     */
    public int getBoardLength() {
        return boardLength;
    }

    /**
     *
     * @return board's width (number of rows)
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     *
     * @return level of the game
     */
    public int getLevel() {
        return level;
    }

}
