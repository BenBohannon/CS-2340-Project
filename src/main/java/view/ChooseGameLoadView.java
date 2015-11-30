package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import presenters.ChooseGameLoadPresenter;

/**
 * View that operates the UI for the screen that allows the user to
 * choose and load a saved game.
 */
public class ChooseGameLoadView extends View<ChooseGameLoadPresenter> {

    @FXML
    ListView gameSavesListView;

    public void handleBackClick(ActionEvent event) {

    }

    public void handleLoadClick(ActionEvent event) {

    }
}
