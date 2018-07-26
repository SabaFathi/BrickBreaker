package ir.ac.kntu;

import java.awt.Point;
import java.security.SecureRandom;

/**
 * This brick embrace the Ball while its(the brick) life end!
 * Its Life is a Random integer number between [2, 5)
 * Its Score is 1
 * @see Brick
 *<Br/>
 * @author SFathi
 */
public class MagneticBrick extends Brick {
    private static final long serialVersionUID = 1807869603925830960L;
    private final static double SCORE = 1;

    /**
     * constructor
     * @param myGameBoard gameBoard which this brick is in
     */
    public MagneticBrick(GameBoard myGameBoard) {
        super(new SecureRandom().nextInt(3) + 2, SCORE, myGameBoard);
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

        if( this.isAlive() ){
            ball.setCanMove(false);
        }else{
            ball.changeScore(SCORE);
            ball.setCanMove(true);

            Point myIndex = gameBoard.getIndexes(this);
            gameBoard.replaceContent(myIndex, null);
        }

        this.changeLife(-1);
    }
}
