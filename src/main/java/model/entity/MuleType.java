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

    /**
     * sets the mule image path
     * @param imagePath path of image for mule
     */
    MuleType(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * gets location of mule image on file
     * @return string of location
     */
    public String getImagePath() {
        return imagePath;
    }
}
