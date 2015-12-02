package model.service;

import model.entity.GameSaveMeta;

/**
 * Holds the meta data for the currently active game save, if there is
 * one active. It is used by the classes that load and store objects from
 * the database, so that they only load the objects associated with the active save.
 * When they store objects, they associate also associate them with the currently
 * active save.
 *
 * Must be bound in DI container to an instance.
 */
public class GameSaveMetaHolderService {
    private GameSaveMeta gameSaveMeta;

    public GameSaveMeta getGameSaveMeta() {
        if (gameSaveMeta == null) {
            throw new IllegalStateException("no currently active game save");
        }
        return gameSaveMeta;
    }

    public void setGameSaveMeta(GameSaveMeta gameSaveMeta) {
        this.gameSaveMeta = gameSaveMeta;
    }

    public boolean hasActiveGame() {
        return gameSaveMeta == null;
    }
}
