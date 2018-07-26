package ir.ac.kntu;

import java.awt.Point;

/**
 * This brick increase the acceleration of the Ball with increaseAcceleration()
 * @see Ball#increaseAcceleration()
 * Its Life is 1
 * Its Score is 0.5
 * @see Brick
 * <Br/>
 * @author SFathi
 */
public class AcceleratorBrick extends Brick {
    private static final long serialVersionUID = -4835432371574386094L;
    private final static int LIFE = 1;
    private final static double SCORE = 0.5;

    /**
     * constructor
     * @param myGameBoard gameBoard which this brick is in
     */
    public AcceleratorBrick(GameBoard myGameBoard) {
        super(LIFE, SCORE,myGameBoard);
        setImage(ImagesLoader.getImageOf(this.getClass().getSimpleName()));
    }

    /**
     * @see Brick#hit()
     */
    @Override
    public void hit() {
        setHited(true);

        GameBoard gameBoard = getMyGameBoard();
        Ball ball = gameBoard.getBall();

        this.changeLife(-1);
        ball.increaseAcceleration();
        ball.changeScore(SCORE);

        Point myIndex = gameBoard.getIndexes(this);
        gameBoard.replaceContent(myIndex, null);
    }
}
