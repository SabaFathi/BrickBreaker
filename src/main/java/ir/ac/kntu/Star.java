package ir.ac.kntu;

import java.awt.Point;

/**
 * a star falling from {@link StarerBrick} when hit,
 * it get an extra life if racket can give heart
 *
 * @author SFathi
 */
public class Star extends FallingComponents {
    private final static double SCORE = 0;
    private final static int LIFE = 1;

    /**
     *
     * @param myGameBoard gameBoard which this star is in
     * @param startIndexes the StarerBrick's indexes
     */
    public Star(GameBoard myGameBoard, Point startIndexes) {
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
        ball.setShiningMode(true);
    }
}
