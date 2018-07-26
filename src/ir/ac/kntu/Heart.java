package ir.ac.kntu;

import java.awt.Point;

/**
 * a heart falling from {@link HearterBrick} when hit,
 * it get an extra life if racket can give heart
 *
 * @author SFathi
 */
public class Heart extends FallingComponents {
    private final static double SCORE = 0;
    private final static int LIFE = 1;

    /**
     * constructor
     *
     * @param myGameBoard gameBoard which this heart is in
     * @param startIndexes the HearterBrick indexes
     */
    public Heart(GameBoard myGameBoard, Point startIndexes) {
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
        ball.changeLife(+1);
    }
}
