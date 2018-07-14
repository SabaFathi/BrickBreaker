package ir.ac.kntu;

import java.awt.Point;

/**
 * Chocolater Brick can produce a {@link Chocolate}
 *
 * @author SFathi
 */
public class ChocolaterBrick extends Brick {
    private static final long serialVersionUID = 6968549528765332132L;
    private final static int LIFE = 1;
    private final static double SCORE = 0;

    /**
     * constructor
     *
     * @param myGameBoard gameBoard which this brick is in
     */
    public ChocolaterBrick(GameBoard myGameBoard) {
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
        changeLife(-1);
        gameBoard.replaceContent(myIndexes, new Chocolate(gameBoard,
                myIndexes));
    }
}
