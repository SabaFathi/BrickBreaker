package ir.ac.kntu;

import java.awt.Point;

/**
 * This brick decrease the acceleration of the Ball with decreaseAcceleration()
 * @see Ball#decreaseAcceleration()
 * Its Life is 1
 * Its Score is 0.5
 * @see Brick
 * <Br/>
 * @author SFathi
 */
public class DeAcceleratorBrick extends Brick {
    private static final long serialVersionUID = -1593218865892818836L;
    private final static int LIFE = 1;
    private final static double SCORE = 0.5;

    /**
     * constructor
     * @param gameBoard gameBoard which this brick is in
     */
    public DeAcceleratorBrick(GameBoard gameBoard) {
        super(LIFE, SCORE, gameBoard);
        setImage(ImagesLoader.getImageOf(this.getClass().getSimpleName()));
    }

    /**
     * @see Brick#hit()
     */
    @Override
    public void hit() {
        setHited(true);
        this.changeLife(-1);

        GameBoard gameBoard = getMyGameBoard();

        Ball ball = gameBoard.getBall();
        ball.decreaseAcceleration();
        ball.changeScore(SCORE);

        Point myIndex = gameBoard.getIndexes(this);
        gameBoard.replaceContent(myIndex, null);
    }
}
