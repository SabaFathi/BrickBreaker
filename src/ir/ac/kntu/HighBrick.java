package ir.ac.kntu;

import java.awt.Point;
import java.security.SecureRandom;

/**
 * This brick is a simple Brick same as LowBrick
 * but its life and score is more than LowBrick's life and score
 * Its Life is a Random number between [2, 4)
 * Its Score is 2
 * @see Brick
 * <Br/>
 * @author SFathi
 */
public class HighBrick extends Brick {
    private static final long serialVersionUID = -3925264686742183481L;
    private final static double SCORE = 2;

    /**
     * constructor
     * @param myGameBoard gameBoard which this brick is in
     */
    public HighBrick(GameBoard myGameBoard) {
        //life : 2<n<4
        super(new SecureRandom().nextInt(2) + 2, SCORE, myGameBoard);
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

        if (this.isAlive()) {
            this.changeLife(-1);
        }else{
            ball.changeScore(SCORE);
            Point myIndex = gameBoard.getIndexes(this);
            gameBoard.replaceContent(myIndex, null);
        }
    }
}
