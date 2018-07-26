package ir.ac.kntu;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 * singleton pattern
 *
 * @author SFathi
 */
public final class Racket {
    private final static Racket INSTANCE_RACKET = new Racket();
    private double x;
    private double y;
    private double minXLimit = 0;
    private double maxXLimit = Byte.MAX_VALUE;
    private final static ImageView IMAGE_VIEW = new ImageView(ImagesLoader
            .getImageOf(Racket.class.getSimpleName()));

    private Racket() {}//singleton pattern

    /**
     *
     * @return instance of Racket
     */
    public static Racket getInstance() {
        return INSTANCE_RACKET;
    }

    /**
     *
     * @param keyCode
     * @param unit units to change racket's coordinate.x
     */
    public void setCoordinate(KeyCode keyCode, int unit){
        switch (keyCode){
            case LEFT:
                if( x>minXLimit ){
                    x -= unit;
                }
                break;
            case RIGHT:
                if( x<maxXLimit ){
                    x += unit;
                }
                break;
            default:
                //do nothing
        }
    }

    /**
     *
     * @param minXLimit
     * @param maxXLimit
     */
    public void setXLimits(double minXLimit, double maxXLimit){
        this.minXLimit = minXLimit;
        this.maxXLimit = maxXLimit;
        if( x<minXLimit ){
            x = minXLimit;
        }
        if( x>maxXLimit ){
            x = maxXLimit;
        }
    }

    /**
     *
     * @param y set racket's coordinate.y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     *
     * @return racket's coordinate.y
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @return racket's coordinate.x
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return racket's imageView
     */
    public static ImageView getImageView() {
        return IMAGE_VIEW;
    }
}
