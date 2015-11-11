package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import presenters.GameConfigPresenter;

/**
 * Created by brian on 10/18/15.
 */
public class GameConfigView extends View<GameConfigPresenter> {

    //FXML textFills//
    @FXML
    Color transparent;
    @FXML
    Color red;

    @FXML
    Label difficulty_validation_label;
    @FXML
    Label map_validation_label;
    @FXML
    Label numPlayers_validation_label;
    @FXML
    Label main_validation_label;

    @FXML
    ToggleGroup difficultyToggleGroup;
    @FXML
    ToggleGroup mapToggleGroup;
    @FXML
    ToggleGroup numPlayersToggleGroup;

    /**
     * Called when the finish button is clicked.
     * @param event event of clicking the finish button.
     */
    @FXML
    protected void handleFinishButtonAction(final ActionEvent event) {
        if (validateForm()) {
            presenter.finishConfigure(((RadioButton)
                    difficultyToggleGroup.getSelectedToggle()).getText(),
                ((RadioButton) mapToggleGroup.getSelectedToggle()).getText(),
                Integer.parseInt(((RadioButton)
                        numPlayersToggleGroup.getSelectedToggle()).getText()));
        }
    }

    /**
     * Validates that the input data is acceptable.
     * @return true, if the input is acceptable.
     */
    private boolean validateForm() {
        boolean allValid = true;

        if (difficultyToggleGroup.getSelectedToggle() == null) {
            difficulty_validation_label.setTextFill(red);
            allValid = false;
        } else {
            difficulty_validation_label.setTextFill(transparent);
        }

        if (mapToggleGroup.getSelectedToggle() == null) {
            map_validation_label.setTextFill(red);
            allValid = false;
        } else {
            map_validation_label.setTextFill(transparent);
        }

        if (numPlayersToggleGroup.getSelectedToggle() == null) {
            numPlayers_validation_label.setTextFill(red);
            allValid = false;
        } else {
            numPlayers_validation_label.setTextFill(transparent);
        }

        main_validation_label.setTextFill(allValid ? transparent : red);
        return allValid;
    }
}