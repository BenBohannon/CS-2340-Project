package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javassist.NotFoundException;
import presenters.InstructionsPresenter;

/**
 * Created by abhisheksen on 17/11/15.
 */
public class InstructionsView extends View<InstructionsPresenter> {

    @FXML
    public final void handleFinishButtonAction(ActionEvent event) {
            getPresenter().finish();
    }

}
