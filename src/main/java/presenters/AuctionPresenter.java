package presenters;

import view.AuctionView;

/**
 * Presenter that displays the auction screen where resources can be sold between players
 */
public class AuctionPresenter extends Presenter<AuctionView> {

    /**
     * Loads the input .fxml file and gives up control to it.
     * @param str the path of the fxml file relative to the presenters package (usually the name)
     */
    public final void switchPresenter(String str) {
//        if (isListening) {
//            turnService.removeTurnEndListener(listener);
//            isListening = false;
//        }
        getContext().showScreen(str);
    }
}
