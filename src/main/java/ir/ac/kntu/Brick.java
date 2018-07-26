package ir.ac.kntu;

/**
 * This is a game component which wait for the ball to hit it and it reacts!
 * It is an abstract class which has "hit()" abstract method.
 * @see Brick#hit()
 * @see GameComponent
 * <Br/>
 * @author SFathi
 */
public abstract class Brick extends GameComponent {
    private static final long serialVersionUID = -4840886727229245961L;

    private boolean isHited = false;

    /**
     * constructor
     * @param life : int, time to be exist in boardGame and be effective.
     * @param score : double, the score getting to ball when destroyed.
     * @param myGameBoard gameBoard which this brick is in
     */
    public Brick(int life, double score, GameBoard myGameBoard) {
        super(life, score, myGameBoard);
    }

    /**
     * This method says what happen if the ball hit the brick
     */
    public abstract void hit();

    /**
     *
     * @return {@code true} if brick was hited otherwise {@code false}
     */
    public boolean isHited() {
        return isHited;
    }

    /**
     *
     * @param hited
     */
    public void setHited(boolean hited) {
        isHited = hited;
    }

    /**
     *
     * @param object the object requesting to check if is equal
     * @return boolean
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
        if (!super.equals(object)) {
            return false;
        }

        Brick brick = (Brick) object;

        return isHited() == brick.isHited() && super.equals(brick);
    }

    /**
     *
     * @return int
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isHited() ? 1 : 0);
        return result;
    }
}
