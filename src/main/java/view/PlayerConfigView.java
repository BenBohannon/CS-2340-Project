package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import presenters.PlayerConfigPresenter;


/**
 * Created by brian on 10/1/15.
 */
public class PlayerConfigView extends View<PlayerConfigPresenter> {

    //FXML textFills//
    @FXML
    Color transparent;
    @FXML
    Color red;

    //validation labels//
    @FXML
    Label main_validation_label;
    @FXML
    Label race_validation_label;
    @FXML
    Label name_validation_label;
    @FXML
    Label color_validation_label;

    //controls//
    @FXML
    ColorPicker colorPicker;
    @FXML
    ToggleGroup race_togglegroup;
    @FXML
    TextField name_textfield;


    /**
     * event handler wired into the fxml control thanks to the fxml file
     */
    @FXML
    public void handleFinishButtonAction(ActionEvent event) {
        if (validateForm()) {
            //can make calls on presenter//
            //notice that the presenter is already of the correct type, because it was injected into the View class.//
            presenter.finish(colorPicker.getValue(), name_textfield.getText());
        }
    }

    private boolean validateForm() {
        boolean allValid = true;

        if (race_togglegroup.getSelectedToggle() == null) {
            race_validation_label.setTextFill(red);
            allValid = false;
        } else {
            race_validation_label.setTextFill(transparent);
        }

        if (name_textfield.getText() == null || name_textfield.getText().trim().equals("")) {
            name_validation_label.setTextFill(red);
            allValid = false;
        } else {
            name_validation_label.setTextFill(transparent);
        }

        main_validation_label.setTextFill(allValid ? transparent : red);
        return allValid;
    }

    /**
     * called from the presenter
     */
    public void showColorAlreadyChosen() {
        color_validation_label.setTextFill(red);
        main_validation_label.setTextFill(red);
    }

    public void showNameAlreadyChosen() {
        name_validation_label.setTextFill(red);
        name_validation_label.setText("name already used!");
        main_validation_label.setTextFill(red);
    }
}
