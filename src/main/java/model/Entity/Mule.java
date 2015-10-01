package model.Entity;

/**
 * Created by brian on 9/17/15.
 */
public class Mule {
    private MuleType type;

    public Mule(MuleType type) {
        this.type = type;
    }

    public MuleType getType() {
        return type;
    }
}
