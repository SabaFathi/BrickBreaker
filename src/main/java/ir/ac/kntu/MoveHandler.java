package ir.ac.kntu;

import java.awt.Point;
import java.io.Serializable;

/**
 * this class can be a field of {@link Ball} objects.
 * it helps the ball in moving procedures
 *
 * @author SFathi
 */
public class MoveHandler implements Serializable {
    private static final long serialVersionUID = 5261640606639705772L;
    private final static double PI = Math.PI;
    private final GameBoard gameBoard;
    private final Ball ball;

    /**
     * constructor
     *
     * @param gameBoard the game board which the ball is in that
     * @param ball the ball which need the move handler
     */
    public MoveHandler(GameBoard gameBoard, Ball ball) {
        this.gameBoard = gameBoard;
        this.ball = ball;
    }

    /**
     * handle moving of the ball
     */
    public void handleMoving(){
        double directionAngle = ball.getDirectionAngle();
        Point ballIndexes = gameBoard.getIndexes(ball);
        int acceleration = ball.getAcceleration();

        while( directionAngle >= 2*PI ){
            directionAngle -= 2*PI;
        }

        if( directionAngle == PI/4 ){
            handleAngle45(ballIndexes, acceleration);
        }else if( directionAngle == PI/2 ){
            handleAngle90(ballIndexes, acceleration);
        }else if( directionAngle == PI*3/4 ){
            handleAngle135(ballIndexes, acceleration);
        }else if( directionAngle == PI*5/4 ){
            handleAngle225(ballIndexes, acceleration);
        }else if( directionAngle == PI*3/2 ){
            handleAngle270(ballIndexes, acceleration);
        }else if( directionAngle == PI*7/4 ){
            handleAngle315(ballIndexes, acceleration);
        }else{
            System.out.println("unexpected state!");
            System.out.println("direction angle = " + directionAngle);
            System.exit(1);
        }
    }

    private void handleAngle45(Point ballIndexes, double acceleration){
        double groundReaction;
        while(acceleration>0){
            ballIndexes = changeBallCell(Obstacle.DOWN_RIGHT, ballIndexes);

            if( Obstacle.DOWN.existObstacle(gameBoard, ballIndexes) ){
                groundReaction = Obstacle.DOWN.hitObstacle
                        (gameBoard, ballIndexes);
                if( groundReaction<0 ){
                    changeDirectionAngle(2*PI - PI/4);
                }else{
                    changeDirectionAngle(groundReaction);
                }
                if( Obstacle.RIGHT.existObstacle(gameBoard, ballIndexes) ){
                    Obstacle.RIGHT.hitObstacle(gameBoard, ballIndexes);
                    changeDirectionAngle(PI + PI/4);
                }
                break;
            }else if( Obstacle.RIGHT.existObstacle(gameBoard, ballIndexes) ){
                Obstacle.RIGHT.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(PI*3/4);
                break;
            }else if( Obstacle.DOWN_RIGHT.existObstacle
                    (gameBoard, ballIndexes) ){
                groundReaction = Obstacle.DOWN_RIGHT.hitObstacle
                        (gameBoard, ballIndexes);
                if( groundReaction<0 ){
                    changeDirectionAngle(PI + PI/4);
                }else{
                    changeDirectionAngle(groundReaction);
                }
                break;
            }

            acceleration --;
        }
    }

    private void handleAngle90(Point ballIndexes, double acceleration){
        double groundReaction;
        while(acceleration>0){
            ballIndexes = changeBallCell(Obstacle.DOWN, ballIndexes);

            if( Obstacle.DOWN.existObstacle(gameBoard, ballIndexes) ){
                groundReaction = Obstacle.DOWN.hitObstacle
                        (gameBoard, ballIndexes);
                if( groundReaction<0 ){
                    changeDirectionAngle(PI*3/2);
                }else{
                    changeDirectionAngle(groundReaction);
                }
                break;
            }

            acceleration --;
        }
    }

    private void handleAngle135(Point ballIndexes, double acceleration){
        double groundReaction;
        while(acceleration>0){
            ballIndexes = changeBallCell(Obstacle.DOWN_LEFT, ballIndexes);

            if( Obstacle.DOWN.existObstacle(gameBoard, ballIndexes) ){
                groundReaction = Obstacle.DOWN.hitObstacle
                        (gameBoard, ballIndexes);
                if( groundReaction<0 ){
                    changeDirectionAngle(PI + PI/4);
                }else{
                    changeDirectionAngle(groundReaction);
                }
                if( Obstacle.LEFT.existObstacle(gameBoard, ballIndexes) ){
                    Obstacle.LEFT.hitObstacle(gameBoard, ballIndexes);
                    changeDirectionAngle(2*PI - PI/4);
                }
                break;
            }else if( Obstacle.LEFT.existObstacle(gameBoard, ballIndexes) ){
                Obstacle.LEFT.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(PI/4);
                break;
            }else if(Obstacle.DOWN_LEFT.existObstacle(gameBoard, ballIndexes)){
                groundReaction = Obstacle.DOWN_LEFT.hitObstacle
                        (gameBoard, ballIndexes);
                if( groundReaction<0 ){
                    changeDirectionAngle(2*PI - PI/4);
                }else{
                    changeDirectionAngle(groundReaction);
                }
                break;
            }

            acceleration --;
        }
    }

    private void handleAngle225(Point ballIndexes, double acceleration){
        while(acceleration>0){
            ballIndexes = changeBallCell(Obstacle.UP_LEFT, ballIndexes);

            if( Obstacle.UP.existObstacle(gameBoard, ballIndexes) ){
                Obstacle.UP.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(PI*3/4);
                if( Obstacle.LEFT.existObstacle(gameBoard, ballIndexes) ){
                    Obstacle.LEFT.hitObstacle(gameBoard, ballIndexes);
                    changeDirectionAngle(PI/4);
                }
                break;
            }else if( Obstacle.LEFT.existObstacle(gameBoard, ballIndexes) ){
                Obstacle.LEFT.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(2*PI - PI/4);
                break;
            }else if( Obstacle.UP_LEFT.existObstacle(gameBoard, ballIndexes) ){
                Obstacle.UP_LEFT.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(PI/4);
                break;
            }

            acceleration--;
        }
    }

    private void handleAngle270(Point ballIndexes, double acceleration){
        while(acceleration>0){
            ballIndexes = changeBallCell(Obstacle.UP, ballIndexes);

            if( Obstacle.UP.existObstacle(gameBoard, ballIndexes) ){
                Obstacle.UP.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(PI/2);
                break;
            }

            acceleration --;
        }
    }

    private void handleAngle315(Point ballIndexes, double acceleration){
        while(acceleration>0){
            ballIndexes = changeBallCell(Obstacle.UP_RIGHT, ballIndexes);

            if( Obstacle.UP.existObstacle(gameBoard, ballIndexes) ){
                Obstacle.UP.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(PI/4);
                if( Obstacle.RIGHT.existObstacle(gameBoard, ballIndexes) ){
                    Obstacle.RIGHT.hitObstacle(gameBoard, ballIndexes);
                    changeDirectionAngle(PI*3/4);
                }
                break;
            }else if( Obstacle.RIGHT.existObstacle(gameBoard, ballIndexes) ){
                Obstacle.RIGHT.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(PI + PI/4);
                break;
            }else if( Obstacle.UP_RIGHT.existObstacle(gameBoard, ballIndexes)){
                Obstacle.UP_RIGHT.hitObstacle(gameBoard, ballIndexes);
                changeDirectionAngle(PI*3/4);
                break;
            }

            acceleration --;
        }
    }

    private Point changeBallCell(Obstacle obstacle, Point ballIndexes){
        if( !ball.canMove() ){
            return ballIndexes;
        }
        gameBoard.replaceContent(ballIndexes, null);
        ballIndexes = Obstacle.getNewPoint(obstacle, ballIndexes);
        gameBoard.replaceContent(ballIndexes, ball);
        return ballIndexes;
    }

    private void changeDirectionAngle(double directionAngle){
        if( !ball.canMove() ){
            return;
        }
        ball.setDirectionAngle(directionAngle);
    }

}
