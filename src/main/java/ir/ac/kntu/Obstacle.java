package ir.ac.kntu;

import java.awt.Point;

/**
 * the obstacles which can exist in {@link GameBoard}, describe here
 *
 * @author SFathi
 */
public enum Obstacle {
    UP(-1, 0), DOWN(+1, 0), RIGHT(0, +1), LEFT(0, -1),
    UP_RIGHT(-1, +1), UP_LEFT(-1, -1), DOWN_RIGHT(+1, +1), DOWN_LEFT(+1, -1);

    private int rowIndex;
    private int colIndex;
    private static Racket racket = Racket.getInstance();
    Obstacle(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     *
     * @param gameBoard
     * @param indexes indexes of the object requesting to check side obstacles
     * @return {@code true} if obstacle exist otherwise {@code false}
     */
    public boolean existObstacle(final GameBoard gameBoard,
                                 final Point indexes){
        if(indexes.x<0 || indexes.y<0 ||
                indexes.x>=gameBoard.getBoardLength() ||
                indexes.y>=gameBoard.getBoardWidth()){
            throw new ArrayIndexOutOfBoundsException(
                    "input indexes is invalid!");
        }
        try{
            Cell cell = gameBoard.getCell(getNewPoint(this, indexes));
            if( cell==null || cell.getContent()==null ){
                return false;
            }else{
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException e){
            return true;
        }
    }

    /**
     *
     * @param gameBoard
     * @param ballIndexes indexes of the ball wanting hit the obstacle
     * @return a positive number meaning the new direction angle if the ball
     * hit the racket, and return a negative number meaning ball does not hit
     * the racket.
     */
    public double hitObstacle(final GameBoard gameBoard,
                              final Point ballIndexes){
        try{
            Brick brick = (Brick)/*gameBoard.
                    getCell(ballIndexes.y+rowIndex, ballIndexes.x+colIndex).
                    getContent();*/gameBoard.getCell(getNewPoint
                    (this, ballIndexes)).getContent();
            brick.hit();
            return -1;
        }catch(ArrayIndexOutOfBoundsException e){
            if( ballIndexes.y+1 >= gameBoard.getBoardWidth() ){
                //hits the ground
                final Ball ball = gameBoard.getBall();
                final int lengthOfCells = JavaFXExecutable.getLengthOfCells();
                final double xStartRacket = racket.getX() - lengthOfCells;
                final double pi = Math.PI;

                if( ballIndexes.x < xStartRacket/lengthOfCells ||
                        ballIndexes.x > xStartRacket*3/lengthOfCells ){
                    ball.changeLife(-1);
                    gameBoard.putBallInHome();
                }else if( ballIndexes.x <
                        (xStartRacket + lengthOfCells)/lengthOfCells ){
                    if( ball.getDirectionAngle()==pi/2 ){
                        return pi+pi/4;
                    }
                }else if( ballIndexes.x <
                        (xStartRacket + 2 * lengthOfCells)/lengthOfCells ){
                    if( ball.getDirectionAngle()==pi/2 ){
                        return pi*3/2;
                    }
                }else if( ballIndexes.x <
                        (xStartRacket + 3 * lengthOfCells)/lengthOfCells ){
                    if( ball.getDirectionAngle()==pi/2 ){
                        return 2 * pi - pi / 4;
                    }
                }
            }
            //hits the wall!
            return -1;
        }catch(ClassCastException e){
            //hits null or not brick component!
            try{
                FallingComponents fallingComponents =
                        (FallingComponents)gameBoard.getCell
                                (getNewPoint(this, ballIndexes)).getContent();
                fallingComponents.hit();
            }catch(NullPointerException exc){
                //hits null
            }
            return -1;
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     *
     * @param obstacle the type of obstacle
     * @param before indexes of last state
     * @return indexes of new state produced when before indexes
     * goes to the obstacle state
     */
    public static Point getNewPoint(Obstacle obstacle, Point before){
        return new Point(before.x+obstacle.colIndex,
                before.y+obstacle.rowIndex);
    }

    /**
     *
     * @return the racket
     * @see Racket
     */
    public static Racket getRacket() {
        return racket;
    }
}
