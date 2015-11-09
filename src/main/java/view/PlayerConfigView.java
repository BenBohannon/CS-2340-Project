package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import presenters.PlayerConfigPresenter;


/**
 * Created by brian on 10/1/15.
 */
public class PlayerConfigView extends View<PlayerConfigPresenter> {

    //FXML textFills//
    // not using getters and setters to avoid clutter //
    @FXML
    private Color transparent;
    @FXML
    private Color red;

    //validation labels//
    @FXML
    private Label mainValidationLabel;
    @FXML
    private Label raceValidationLabel;
    @FXML
    private Label nameValidationLabel;
    @FXML
    private Label colorValidationLabel;

    //controls//
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ToggleGroup raceToggleGroup;
    @FXML
    private TextField nameTextField;


    /**
     * event handler wired into the fxml control thanks to the fxml file
     */
    @FXML
    public final void handleFinishButtonAction(ActionEvent event) {
        if (validateForm()) {
            //can make calls on presenter//
            //notice that the presenter is already of the correct type, because it was injected into the View class.//

            getPresenter().finish(colorPicker.getValue(), nameTextField.getText(), ((RadioButton) raceToggleGroup.getSelectedToggle()).getText());
        }
    }

    private boolean validateForm() {
        boolean allValid = true;

        if (raceToggleGroup.getSelectedToggle() == null) {
            raceValidationLabel.setTextFill(red);
            allValid = false;
        } else {
            raceValidationLabel.setTextFill(transparent);
        }

        if (nameTextField.getText() == null || nameTextField.getText().trim().equals("")) {
            nameValidationLabel.setTextFill(red);
            allValid = false;
        } else {
            nameValidationLabel.setTextFill(transparent);
        }

        mainValidationLabel.setTextFill(allValid ? transparent : red);
        return allValid;
    }

    /**
     * called from the presenter
     */
    public final void showColorAlreadyChosen() {
        colorValidationLabel.setTextFill(red);
        mainValidationLabel.setTextFill(red);
    }

    public final void showNameAlreadyChosen() {
        nameValidationLabel.setTextFill(red);
        nameValidationLabel.setText("name already used!");
        mainValidationLabel.setTextFill(red);
    }

    public final void showRaceAlreadyChosen() {
        raceValidationLabel.setTextFill(red);
        mainValidationLabel.setTextFill(red);
    }

}
