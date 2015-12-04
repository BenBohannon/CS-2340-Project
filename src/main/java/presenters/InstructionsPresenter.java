package presenters;

import javassist.NotFoundException;
import view.InstructionsView;

/**
 * Created by abhisheksen on 17/11/15.
 */
public class InstructionsPresenter extends Presenter<InstructionsView> {

    public void finish() {
        getContext().showScreen("map_grid_tile_select.fxml");
    }
}
