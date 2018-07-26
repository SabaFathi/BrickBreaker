package ir.ac.kntu;

/**
 * <b>Observable Pattern</b>
 * @author SFathi
 */
public interface Observable {

    /**
     * it forces the observable objects to add their observers in a list
     * @param observer an observer which observes the observable
     */
    void addObserver(Observer observer);

    /**
     * delete the observer from observers list
     * @param observer the observer which
     *                 do not observes the observable after this second
     */
    void deleteObserver(Observer observer);

    /**
     * notify and update all observers exist in observers list
     */
    void updateAllObservers();
}
