package ir.ac.kntu;

import java.awt.Point;

/**
 *
 * @author SFathi
 */
public abstract class FallingComponents extends Brick
        implements Observer, Movable{
    private static final long serialVersionUID = -5961090700967047315L;
    private int rowIndex;
    private int colIndex;

    /**
     * constructor
     *
     * @param life life of the component
     * @param score score of the component
     * @param myGameBoard gameBoard which this component is in
     * @param startIndexes the Producer Brick indexes
     */
    public FallingComponents(int life, double score, GameBoard myGameBoard,
                             Point startIndexes) {
        super(life, score, myGameBoard);
        this.rowIndex = startIndexes.y;
        this.colIndex = startIndexes.x;
        getMyGameBoard().getBall().addObserver(this);
    }

    /**
     * @see Brick#hit()
     */
    @Override
    public void hit() {
        changeLife(-1);
        getMyGameBoard().replaceContent(getIndexes(), null);
    }

    /**
     * @see Observer#update()
     */
    @Override
    public void update() {
        move();
    }

    /**
     * @see Movable#move()
     */
    @Override
    public void move() {
        GameBoard gameBoard = getMyGameBoard();
        Ball ball = gameBoard.getBall();
        Point ballIndexes = gameBoard.getIndexes(ball);

        if( !isAlive() ){
            ball.deleteObserver(this);
            return;
        }

        gameBoard.replaceContent(getIndexes(), null);
        rowIndex ++;

        if( ballIndexes.x==colIndex && ballIndexes.y==rowIndex ){
            return;
        }
        if( rowIndex<gameBoard.getBoardWidth() ){
            gameBoard.replaceContent(getIndexes(), this);
        }else{
            final int minRacket = (int)Obstacle.getRacket().getX()/JavaFXExecutable.getLengthOfCells()-1;
            final int maxRacket = minRacket + 3;
            if( minRacket<=colIndex && colIndex<maxRacket ){
                applyChanges(ball);
            }
            changeLife(-1);
        }
    }

    /**
     *
     * @return a point containing indexes of the component
     */
    private Point getIndexes(){
        return new Point(colIndex, rowIndex);
    }

    /**
     *
     * @param ball the ball which changes should apply on it
     */
    public abstract void applyChanges(Ball ball);
}
