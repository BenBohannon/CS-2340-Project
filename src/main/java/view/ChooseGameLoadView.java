package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import presenters.ChooseGameLoadPresenter;

import java.util.List;

/**
 * View that operates the UI for the screen that allows the user to
 * choose and load a saved game.
 */
public class ChooseGameLoadView extends View<ChooseGameLoadPresenter> {

    @FXML
    ListView gameSavesListView;
    @FXML
    Label listSelectionValidationLabel;

    ObservableList<String> savesToDisplay;

    @FXML
    public void initialize() {
        gameSavesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void setGameSaveStrsToDisplay(List<String> pSavesToDisplay) {
        savesToDisplay = FXCollections.observableArrayList(pSavesToDisplay);
        gameSavesListView.setItems(savesToDisplay);
    }

    public void handleBackClick() {
        getPresenter().onBackToHome();
    }

    public void handleLoadClick() {
        if (isUserSelectionValid()) {
            getPresenter().onLoadSavedGame(getListViewSelectedIndex());
        }
        updateValidationLabelVisibility();
    }

    private boolean isUserSelectionValid() {
        return !gameSavesListView.getSelectionModel().isEmpty();
    }

    private int getListViewSelectedIndex() {
        return gameSavesListView.getSelectionModel().getSelectedIndex();
    }

    private void updateValidationLabelVisibility() {
        if (isUserSelectionValid()) {
            listSelectionValidationLabel.setVisible(false);
        } else {
            listSelectionValidationLabel.setVisible(true);
        }
    }
}
