/**
 * Created by brian on 9/14/15.
 * Superclass for LocatableA, B, C.
 * Allows each instance of our test locatables to be uniquely identified.
 */
public class ABC {
    private int id;

    public ABC() {
        id = MapTests.getId();
    }

    public int getId() {
        return id;
    }
}