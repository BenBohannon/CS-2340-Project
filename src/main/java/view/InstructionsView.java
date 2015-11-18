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
    private Button start;
    @FXML
    private Button cancel;

    @FXML
    public final void handleFinishButtonAction(ActionEvent event) throws NotFoundException {
        getPresenter().finish(start.isPressed(), cancel.isPressed());
    }
}
