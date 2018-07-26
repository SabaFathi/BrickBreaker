package ir.ac.kntu;

import java.io.Serializable;

/**
 * This class forming the "board" game.
 * It has a content with GameComponent type
 * @see GameComponent
 * <br/>
 * @author SFathi
 */
public class Cell implements Serializable{
    private static final long serialVersionUID = 6627564109777764494L;
    private GameComponent content;

    /**
     * constructor
     * @param content : GameComponent, content of the cell
     */
    public Cell(GameComponent content) {
        this.content = content;
    }

    /**
     * constructor
     * Produce a Cell with null content
     * @see Cell#Cell(GameComponent)
     */
    public Cell() {
        this(null);
    }

    /**
     *
     * @return content of the cell : GameComponent
     */
    public GameComponent getContent() {
        return content;
    }

    /**
     *
     * @param content : GameComponent, content of the cell
     */
    public void setContent(GameComponent content) {
        this.content = content;
    }

    /**
     *
     * @return a copy of this cell
     * @see Object#clone()
     */
    @Override
    public Object clone() {
        return new Cell(this.content);
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

        Cell cell = (Cell) object;

        if( this.content==null ){
            if( cell.getContent()==null ){
                return true;
            }else{
                return false;
            }
        }

        return getContent().equals(cell.getContent());
    }

    /**
     *
     * @return hashCode : int
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getContent()==null ? 0 : getContent().hashCode();
    }
}
