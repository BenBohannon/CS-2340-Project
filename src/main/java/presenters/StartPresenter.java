package presenters;

import com.google.inject.Inject;
import data.abstractsources.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import model.entity.*;
import model.service.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.*;
import java.util.*;


/**
 * Created by brian on 9/10/15.
 */
public class StartPresenter extends Presenter {

    private GameSaveMetaHolderService gameSaveMetaHolder;
    private Repository<GameSaveMeta> gameSaveMetaRepo;

    private Label nameRequiredValidationLabel;
    private TextField nameTextField;
    private Stage modalStage;

    @Inject
    public StartPresenter(GameSaveMetaHolderService pGameSaveMetaHolder, Repository<GameSaveMeta> pGameSaveMetaRepo) {
        gameSaveMetaHolder = pGameSaveMetaHolder;
        gameSaveMetaRepo = pGameSaveMetaRepo;
    }

    @FXML
    /**
     * reads past game state from db and starts
     */
    protected final void handleNewGameEvent(ActionEvent event) {
        showSaveNameModal(event);
    }

    private void clearTable(String tableName, Session session) {
        String queryString = new StringBuilder("FROM ")
                .append(tableName)
                .toString();
        Query query = session.createQuery(queryString);
        if (query.list() != null) {
            for (Object locatableRecord : query.list()) {
                session.delete(locatableRecord);
            }
        }
    }

    @FXML
    protected final void handleLoadEvent(ActionEvent event) {
        getContext().showScreen("choose_game_load.fxml");
    }

    private void showSaveNameModal(ActionEvent event) {

        Parent rootFxmlView = createFXMLViewsFromFile("name_save_modal.fxml");

        setupModalStageForView(rootFxmlView, event);

        setupViewLogic(modalStage.getScene());

        modalStage.show();
    }

    private Parent createFXMLViewsFromFile(String filePath) {
        try {
            return FXMLLoader.load(getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("not a valid JavaFx file path, or could not read");
    }

    private Stage setupModalStageForView(Parent rootView, ActionEvent initiatingEvent) {
        modalStage = new Stage();
        modalStage.setScene(new Scene(rootView));
        modalStage.setTitle("Name Your Save");
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(((Node) initiatingEvent.getSource()).getScene().getWindow());

        return modalStage;
    }

    private void setupViewLogic(Scene scene) {
        nameRequiredValidationLabel = (Label) scene.lookup("#nameRequiredValidationLabel");
        nameTextField = (TextField) scene.lookup("#nameTextField");
        scene.lookup("#okayButton").setOnMouseClicked(event -> onOkayClicked());
    }

    private void onOkayClicked() {
        if (isNameInputValid()) {

            GameSaveMeta gameSaveMeta = initializeNewGameSaveMeta(nameTextField.getText());
            gameSaveMetaHolder.setGameSaveMeta(gameSaveMeta);

            getContext().showScreen("config_screen.fxml");

            modalStage.close(); // this is confusing, somehow equivalent to hide()
        } else {
            nameRequiredValidationLabel.setVisible(true);
        }
    }

    private boolean isNameInputValid() {
        return (nameTextField.getText() != null) && (!nameTextField.getText().trim().isEmpty());
    }

    private GameSaveMeta initializeNewGameSaveMeta(String gameSaveName) {
        GameSaveMeta meta = new GameSaveMeta();
        meta.setGameStarted(new Date());
        meta.setLastPlayed(new Date());
        meta.setName(gameSaveName);
        meta.setId(-1); // this is a flag for new instance. Sad move, but this project is done.

        return gameSaveMetaRepo.save(meta); // now has correct id
    }
}
