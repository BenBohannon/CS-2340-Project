package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
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
     * @param event event handler
     */
    @FXML
    public final void handleFinishButtonAction(ActionEvent event) {
        if (validateForm()) {
            //can make calls on presenter//
            //notice that the presenter is already of the correct type,
            // because it was injected into the View class.//

            getPresenter().finish(colorPicker.getValue(),
                    nameTextField.getText(),
                    ((RadioButton) raceToggleGroup.getSelectedToggle()).getText());
        }
    }

    /**
     * checks for form correctness
     * @return if form is properly filled out
     */
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
     * shows color already chosen error
     */
    public final void showColorAlreadyChosen() {
        colorValidationLabel.setTextFill(red);
        mainValidationLabel.setTextFill(red);
    }


    /**
     * shows name already chosen error
     */
    public final void showNameAlreadyChosen() {
        nameValidationLabel.setTextFill(red);
        nameValidationLabel.setText("name already used!");
        mainValidationLabel.setTextFill(red);
    }

    /**
     * shows race already chosen error
     */
    public final void showRaceAlreadyChosen() {
        raceValidationLabel.setTextFill(red);
        mainValidationLabel.setTextFill(red);
    }

}
