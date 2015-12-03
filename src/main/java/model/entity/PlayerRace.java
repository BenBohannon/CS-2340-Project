package model.entity;

/**
 * Created by brian on 10/15/15.
 */
public enum PlayerRace {
    Bonzoid("Bonzoid", "/races/Bonzoid.png", 470),
    Buzzite("Buzzite", "/races/Buzzite.png", 470),
    Flapper("Flapper", "/races/Flapper.png", 600),
    Human("Human", "/races/Human.png", 330),
    Ugaite("Ugaite", "/races/Ugaite.png", 470);

    private String raceName;
    private String imagePath;
    private int ptu;

    /**
     * initialises player race
     * @param pRaceName name of race
     * @param pImagePath location of image file
     * @param pPtu ptu
     */
    PlayerRace(String pRaceName, String pImagePath, int pPtu) {
        this.raceName = pRaceName;
        this.imagePath = pImagePath;
        this.ptu = pPtu;
    }

    /**
     * return race name
     * @return race name
     */
    public String toString() {
        return raceName;
    }

    /**
     * gets location of image for race
     * @return image locataion
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * gets PTU of race
     * @return ptu
     */
    public int getPtu() {
        return ptu;
    }
}
