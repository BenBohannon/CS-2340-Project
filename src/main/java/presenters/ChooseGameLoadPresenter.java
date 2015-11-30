package presenters;

import com.google.inject.Inject;
import data.abstractsources.Repository;
import model.entity.GameSaveMeta;
import model.service.DefaultTurnService;
import model.service.GameSaveMetaHolderService;
import view.ChooseGameLoadView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Presenter for the screen that allows the user to choose the saved game that
 * he or she would like to play.
 */
public class ChooseGameLoadPresenter extends Presenter<ChooseGameLoadView> {

    @Inject
    Repository<GameSaveMeta> gameSaveMetaRepository;
    @Inject
    GameSaveMetaHolderService gameSaveMetaHolder;
    @Inject
    DefaultTurnService turnService;

    private List<GameSaveMeta> gameSaveList;

    public ChooseGameLoadPresenter() {
        gameSaveList = new ArrayList<>();
    }

    @Override
    public void initialize() {
        gameSaveList.addAll(gameSaveMetaRepository.getAll());
        setViewGameSavesToDisplay();
    }

    public void onBackToHome() {
        getContext().showScreen("home_screen.fxml");
    }

    public void onLoadSavedGame(int savedGameIndex) {
        GameSaveMeta chosenGameSaveMeta = gameSaveList.get(savedGameIndex);

        gameSaveMetaHolder.setGameSaveMeta(chosenGameSaveMeta);

        initializeGameStateAndStart();
    }

    private void setViewGameSavesToDisplay() {
        List<String> displayStringsForGameSaves = gameSaveList.stream()
                .map((gameSaveMeta) -> gameSaveMeta.getName() + " " + gameSaveMeta.getLastPlayed().toString())
                .collect(Collectors.toList());

        getView().setGameSaveStrsToDisplay(displayStringsForGameSaves);
    }

    private void initializeGameStateAndStart() {
        turnService.initializeFromDatasource();
        if (turnService.isAllTurnsOver()) {
            getContext().showScreen("map_grid_tile_select.fxml");
        } else {
            getContext().showScreen("map_grid.fxml");
        }
    }
}
