package ir.ac.kntu;

import java.awt.Point;

/**
 * a Hearter Brick can produce a {@link Heart}
 *
 * @author SFathi
 */
public class HearterBrick extends Brick {
    private static final long serialVersionUID = -555761467953998638L;
    private final static int LIFE = 1;
    private final static double SCORE = 0;

    /**
     * constructor
     *
     * @param myGameBoard gameBoard which this brick is in
     */
    public HearterBrick(GameBoard myGameBoard) {
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
        Point myIndexes = gameBoard.getIndexes(this);
        new Heart(gameBoard, myIndexes);
        changeLife(-1);
        gameBoard.replaceContent(myIndexes, null);
    }
}
