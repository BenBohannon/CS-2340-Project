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
    // not using getters and setters to avoid clutter //
    @FXML
    Color transparent;
    @FXML
    Color red;

    @FXML
    Label difficultyValidationLabel;
    @FXML
    Label mapValidationLabel;
    @FXML
    Label numPlayersValidationLabel;
    @FXML
    Label mainValidationLabel;

    @FXML
    ToggleGroup difficultyToggleGroup;
    @FXML
    ToggleGroup mapToggleGroup;
    @FXML
    ToggleGroup numPlayersToggleGroup;

    @FXML
    public final void handleFinishButtonAction(ActionEvent event) {
        if (validateForm()) {
            getPresenter().finishConfigure(((RadioButton) difficultyToggleGroup.getSelectedToggle()).getText(),
                    ((RadioButton) mapToggleGroup.getSelectedToggle()).getText(),
                    Integer.parseInt(((RadioButton) numPlayersToggleGroup.getSelectedToggle()).getText()));
        }
    }


    private boolean validateForm() {
        boolean allValid = true;

        if (difficultyToggleGroup.getSelectedToggle() == null) {
            difficultyValidationLabel.setTextFill(red);
            allValid = false;
        } else {
            difficultyValidationLabel.setTextFill(transparent);
        }

        if (mapToggleGroup.getSelectedToggle() == null) {
            mapValidationLabel.setTextFill(red);
            allValid = false;
        } else {
            mapValidationLabel.setTextFill(transparent);
        }

        if (numPlayersToggleGroup.getSelectedToggle() == null) {
            numPlayersValidationLabel.setTextFill(red);
            allValid = false;
        } else {
            numPlayersValidationLabel.setTextFill(transparent);
        }

        mainValidationLabel.setTextFill(allValid ? transparent : red);
        return allValid;
    }
}