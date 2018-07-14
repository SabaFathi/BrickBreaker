package ir.ac.kntu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 * An abstract class which used to define a component
 * want being exist in game's board<br/>
 * it has no abstract method but it is abstract
 * and can have no instance directly
 * <br/>
 * @author SFathi
 */
public abstract class GameComponent implements Serializable {
    private static final long serialVersionUID = -1098812548943852518L;
    private transient Image image;
    private int life;
    private double score = 0;
    private final GameBoard myGameBoard;

    /**
     * constructor
     * @param life life of the game component : int
     * @param score base score of the game component : double
     * @param myGameBoard gameBoard which this brick is in
     */
    public GameComponent(int life, double score, GameBoard myGameBoard) {
        this.life = life;
        this.score = score;
        this.myGameBoard = myGameBoard;
    }

    /**
     *
     * @return {@code true} if its life>0 and {@code false} if its dead
     */
    public boolean isAlive(){
        return life>0;
    }

    /**
     *
     * @param unit how many units request to change?
     *             use +unit to increase and -unit to decrease life value
     */
    public void changeLife(int unit) {
        life += unit;
    }

    /**
     *
     * @return life value
     */
    public int getLife() {
        return life;
    }

    /**
     *
     * @param unit how many units request to change?
     *             use +unit to increase and -unit to decrease score value
     */
    public void changeScore(double unit) {
        score += unit;
    }

    /**
     *
     * @return score value
     */
    public double getScore() {
        return score;
    }

    /**
     *
     * @param image its image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     *
     * @return its image
     */
    public Image getImage() {
        return image;
    }

    /**
     *
     * @return graphical shape : ImageView
     * the ImageView every time is a new Object to make no conflict
     * when it adding to the pane
     * it return null if no Image set before
     * @see ImageView
     */
    public ImageView getImageView() {
        ImageView copy = new ImageView(image);
        return copy;
    }

    /**
     *
     * @return its gameBoard
     */
    public GameBoard getMyGameBoard() {
        return myGameBoard;
    }

    /**
     *
     * @param object the object requesting to check if is equal
     * @return {@code true} if this and input object is equal
     * and {@code false} if not equal.
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }

        GameComponent that = (GameComponent) object;

        if (getLife() != that.getLife()){
            return false;
        }
        if (that.getScore() != getScore()){
            return false;
        }
        if (getImage() != null ? !getImage().equals(that.getImage()) :
                that.getImage() != null){
            return false;
        }
        return getMyGameBoard() != null ? getMyGameBoard().equals(that.getMyGameBoard()) : that.getMyGameBoard() == null;
    }

    /**
     *
     * @return hashCode
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getImage() != null ? getImage().hashCode() : 0;
        result = 31 * result + getLife();
        temp = Double.doubleToLongBits(getScore());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getMyGameBoard() != null ? getMyGameBoard().hashCode() : 0);
        return result;
    }
}
