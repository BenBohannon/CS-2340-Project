package presenters;

import javassist.NotFoundException;
import view.InstructionsView;

/**
 * Created by abhisheksen on 17/11/15.
 */
public class InstructionsPresenter extends Presenter<InstructionsView> {

    public void finish(boolean start, boolean cancel) throws NotFoundException {
        if (start) {
            getContext().showScreen("map_grid_tile_select.fxml");
        } else if (cancel) {
            getContext().showScreen("home_screen.fxml");
        } else {
            throw new NotFoundException("You managed not to press a button, wtf");
        }
    }
}
