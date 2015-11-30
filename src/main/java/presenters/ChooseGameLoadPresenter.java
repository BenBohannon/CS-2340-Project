package presenters;

import view.ChooseGameLoadView;

/**
 * Created by brian on 11/29/15.
 */
public class ChooseGameLoadPresenter extends Presenter<ChooseGameLoadView> {



    public void onBackToHome() {
        getContext().showScreen("home_screen.fxml");
    }

    public void onLoadSavedGame(int savedGameIndex) {

    }
}
