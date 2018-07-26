package ir.ac.kntu;

import java.security.SecureRandom;
import java.util.function.Function;

/**
 * It can product new Brick s in Random type
 * <br/>
 * @author SFathi
 */
public final class BrickFactory {
    private final static int BRICK_TYPE_COUNTER =
            ImagesLoader.getNumberOfBricks();
    private static boolean isHearterBrickProduced = false;
    private static Function<GameBoard, Brick>[] functions =
            new Function[BRICK_TYPE_COUNTER+1];
    static {
        functions[0] = (gameBoard) -> null;
        functions[1] = new Function<GameBoard, Brick>() {
            @Override
            public Brick apply(GameBoard gameBoard) {
                Brick brick = new LowBrick(gameBoard);
                return brick;
            }
        };
        functions[2] = gameBoard -> {
            Brick brick = new HighBrick(gameBoard);
            return brick;
        };
        functions[3] = gameBoard -> {
            Brick brick = new MagneticBrick(gameBoard);
            return brick;
        };
        functions[4] = gameBoard -> {
            Brick brick = new AcceleratorBrick(gameBoard);
            return brick;
        };
        functions[5] = gameBoard -> {
            Brick brick = new DeAcceleratorBrick(gameBoard);
            return brick;
        };
        functions[6] = gameBoard -> {
            Brick brick = new ChocolaterBrick(gameBoard);
            return brick;
        };
        functions[7] = gameBoard -> {
            Brick brick = new StarerBrick(gameBoard);
            return brick;
        };
        functions[8] = gameBoard -> {
            Brick brick = new HearterBrick(gameBoard);
            return brick;
        };
    }

    /**
     * product new Random Brick
     * @param gameBoard gameBoard which the brick will be in
     * @return new Brick of Random type
     */
    public static Brick random(GameBoard gameBoard){
        int random = new SecureRandom().nextInt(BRICK_TYPE_COUNTER+1);

        if( isHearterBrickProduced ){
            while( random==8 ){
                random = new SecureRandom().nextInt(BRICK_TYPE_COUNTER);
            }
        }else{
            if( random==8 ){
                isHearterBrickProduced = true;
            }
        }

        return functions[random].apply(gameBoard);
    }

    /**
     * Utility classes should not have a public or default constructor.
     */
    private BrickFactory() {
    }
}
