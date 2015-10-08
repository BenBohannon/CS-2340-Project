package presenters;

import view.AuctionView;

/**
 * Created by kylemurray on 10/7/15.
 */
public class AuctionPresenter extends Presenter<AuctionView> {

    /**
     * Loads the input .fxml file and gives up control to it.
     * @param str
     */
    public void switchPresenter(String str) {
//        if (isListening) {
//            turnService.removeTurnEndListener(listener);
//            isListening = false;
//        }
        context.showScreen(str);
    }
}
