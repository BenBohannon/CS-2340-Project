package model.entity;

/**
 * Created by brian on 9/17/15.
 */
public enum MuleType {
    Energy("/mule/energy.png"),
    Food("/mule/burger.png"),
    Smithore("/mule/ingot.png"),
    Crysite("/mule/crystal.png");

    private String imagePath;

    MuleType(String pImagePath) {
        imagePath = pImagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
