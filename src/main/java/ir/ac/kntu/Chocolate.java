package ir.ac.kntu;

import java.awt.Point;

/**
 * a chocolate falling from {@link ChocolaterBrick} when hit,
 * it get some score if racket can give chocolate
 *
 * @author SFathi
 */
public class Chocolate extends FallingComponents {
    private final static double SCORE = 3;
    private final static int LIFE = 1;

    /**
     * constructor
     *
     * @param myGameBoard gameBoard which this chocolate is in
     * @param startIndexes the ChocolaterBrick indexes
     */
    public Chocolate(GameBoard myGameBoard, Point startIndexes) {
        super(LIFE, SCORE, myGameBoard, startIndexes);
        setImage(ImagesLoader.getImageOf(this.getClass().getSimpleName()));
    }

    /**
     *
     * @param ball the ball which changes should apply on it
     * @see FallingComponents#applyChanges(Ball)
     */
    @Override
    public void applyChanges(Ball ball) {
        ball.changeScore(SCORE);
    }
}
