package ir.ac.kntu;

import java.awt.Point;

/**
 * This brick is a simple Brick which just stand one time to hit and die!
 * Its Life is 1
 * Its Score is 1
 * @see Brick
 * <Br/>
 * @author SFathi
 */
public class LowBrick extends Brick {
    private static final long serialVersionUID = -6499682698533687973L;
    private final static int LIFE = 1;
    private final static double SCORE = 1;

    /**
     * constructor
     * @param myGameBoard gameBoard which this brick is in
     */
    public LowBrick(GameBoard myGameBoard) {
        super(LIFE, SCORE, myGameBoard);
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

        ball.changeScore(SCORE);

        Point myIndex = gameBoard.getIndexes(this);
        gameBoard.replaceContent(myIndex, null);
    }
}
