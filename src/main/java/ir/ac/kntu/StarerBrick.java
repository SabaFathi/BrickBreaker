package ir.ac.kntu;

import java.awt.Point;

/**
 * a Starer Brick can produce a {@link Star}
 *
 * @author SFathi
 */
public class StarerBrick extends Brick {
    private static final long serialVersionUID = -8167470548147506775L;
    private final static int LIFE = 1;
    private final static double SCORE = 0;

    /**
     * constructor
     *
     * @param myGameBoard gameBoard which this brick is in
     */
    public StarerBrick(GameBoard myGameBoard) {
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
        new Star(gameBoard, myIndexes);
        changeLife(-1);
        gameBoard.replaceContent(myIndexes, null);
    }
}
