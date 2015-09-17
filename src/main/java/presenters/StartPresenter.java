package presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


/**
 * Created by brian on 9/10/15.
 */
public class StartPresenter extends Presenter {

    @FXML
    protected void handleStartEvent(ActionEvent event) {
        context.showScreen("config_screen.fxml");
        //this presenter's view will now be removed, and the config_screen's started//
    }
}
