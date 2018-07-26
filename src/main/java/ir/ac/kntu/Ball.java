package ir.ac.kntu;

import java.util.ArrayList;

/**
 * This is a game component which move in the game's board
 * This is Movable and Observable
 * @see GameComponent
 * @see Movable
 * @see Observable
 * <Br/>
 * @author SFathi
 */
public class Ball extends GameComponent implements Movable, Observable{
    private static final long serialVersionUID = 5306827182609597399L;
    private final static int BASE_ACCELERATION = 1;
    private final static int UP_ACCELERATION = BASE_ACCELERATION * 2;
    private int acceleration = BASE_ACCELERATION;
    private double directionAngle;
    private boolean canMove = true;
    private int delayTime = 1000;
    private MoveHandler moveHandler;
    private ArrayList<Observer> observers = new ArrayList<>();
    private boolean shiningMode = false;

    /**
     * constructor
     * construct a Ball with base direction angel = Math.PI*1.5
     * @param life int, time to be exist in boardGame and be effective.
     * @param score base score
     * @param myGameBoard gameBoard which this brick is in
     * @see Ball#Ball(int, double, GameBoard, double)
     */
    public Ball(int life, double score, GameBoard myGameBoard) {
        this(life, score, myGameBoard, Math.PI*1.5);
    }

    /**
     *
     * @param life int, time to be exist in boardGame and be effective.
     * @param score base score
     * @param myGameBoard gameBoard which this brick is in
     * @param directionAngle base direction angle
     */
    public Ball(int life, double score, GameBoard myGameBoard,
                double directionAngle){
        super(life, score, myGameBoard);
        this.directionAngle = directionAngle;
        moveHandler = new MoveHandler(myGameBoard, this);
        setImage(ImagesLoader.getImageOf(this.getClass().getSimpleName()));
    }

    /**
     * ball can move in game's board
     * its move step relies on its acceleration and its distance with
     * first obstacle(walls or bricks)
     * <br/>
     * if it goes near a brick, hit the brick too.
     * @throws ArrayIndexOutOfBoundsException when it goes to brick's side
     * and found no brick
     * @see MoveHandler#handleMoving()
     */
    @Override
    public void move(){
        try {
            Thread.sleep(delayTime);
        }catch(InterruptedException ex){
            //eat the exception! :D
        }

        moveHandler.handleMoving();

        updateAllObservers();
    }

    /**
     *
     * @param unit units to decrease delay time
     *             if delay time <= 0 then it change to one milliseconds
     */
    public void decreaseDelayTime(int unit){
        delayTime -= unit;
        if( delayTime<=0 ){
            delayTime = 100;
        }
    }

    /**
     *
     * @param observer
     * @see Observable#addObserver(Observer)
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     *
     * @param observer
     * @see Observable#deleteObserver(Observer)
     */
    @Override
    public void deleteObserver(Observer observer) {
        if( observers.contains(observer) ){
            observers.remove(observer);
        }
    }

    /**
     * @see Observable#updateAllObservers()
     */
    @Override
    public void updateAllObservers() {
        //NOTE: don't use Iterator here;
        //"update()" may remove an object from arrayList!
        /*if( observers.isEmpty() ){
            return;
        }
        for(Observer observer : observers){
            observer.update();
        }
        */

        for(int i=0 ; i<observers.size() ; i++){
            observers.get(i).update();
        }
    }

    /**
     *
     * @param directionAngle new value of direction angle
     */
    public void setDirectionAngle(double directionAngle) {
        this.directionAngle = directionAngle;
    }

    /**
     *
     * @return direction Angle : double
     */
    public double getDirectionAngle() {
        return directionAngle;
    }

    /**
     *
     * @param canMove boolean, set {@code true} if ball can move
     *                and {@code false} if ball has not permission to move
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * if acceleration is under the value of UP_ACCELERATION
     * this method increase ball's acceleration one unit
     * and if not, do nothing
     */
    public void increaseAcceleration(){
        if( acceleration<UP_ACCELERATION ){
            acceleration ++;
        }
    }

    /**
     * if acceleration is upper than the value of BASE_ACCELERATION
     * this method decrease ball's acceleration one unit
     * and if not, do nothing
     */
    public void decreaseAcceleration(){
        if( acceleration>BASE_ACCELERATION ){
            acceleration --;
        }
    }

    /**
     *
     * @return acceleration : int
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     *
     * @return {@code true} if ball can move otherwise {@code false}
     */
    public boolean canMove() {
        return canMove;
    }

    /**
     *
     * @return {@code true} if shining mode is on otherwise {@code false}
     */
    public boolean isShiningMode() {
        return shiningMode;
    }

    /**
     *
     * @param shiningMode boolean, turn on or off the shining mode
     */
    public void setShiningMode(boolean shiningMode) {
        this.shiningMode = shiningMode;
    }

    /**
     *
     * @param unit how many units request to change?
     * @see GameComponent#changeLife(int)
     */
    @Override
    public void changeLife(int unit){
        if( shiningMode ){
            shiningMode = false;
            return;
        }else{
            super.changeLife(unit);
        }
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

        Ball ball = (Ball) object;

        if (getAcceleration() != ball.getAcceleration()){
            return false;
        }
        if (Double.compare(ball.getDirectionAngle(), getDirectionAngle()) !=0){
            return false;
        }
        if (canMove != ball.canMove){
            return false;
        }

        return observers != null ? observers.equals(ball.observers) :
                ball.observers == null;

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
        result = getAcceleration();
        temp = Double.doubleToLongBits(getDirectionAngle());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (canMove ? 1 : 0);
        result = 31 * result + (observers != null ? observers.hashCode() : 0);
        return result;
    }
}
