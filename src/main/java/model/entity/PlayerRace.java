package model.entity;

/**
 * Created by brian on 10/15/15.
 */
public enum PlayerRace {
    Bonzoid("Bonzoid", "/races/Bonzoid.png"),
    Buzzite("Buzzite", "/races/Buzzite.png"),
    Flapper("Flapper", "/races/Flapper.png"),
    Human("Human", "/races/Human.png"),
    Ugaite("Ugaite", "/races/Human.png");

    private String raceName;
    private String imagePath;

    PlayerRace(String raceName, String imagePath) {
        this.raceName = raceName;
        this.imagePath = imagePath;
    }

    public String toString() {
        return raceName;
    }

    public String getImagePath() {
        return imagePath;
    }
}
