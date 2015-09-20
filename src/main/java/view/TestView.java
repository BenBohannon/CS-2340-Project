package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import presenters.TestPresenter;

/**
 * Created by brian on 9/19/15.
 */
public class TestView extends View<TestPresenter> {

    @FXML
    Button button;

    public void handleButtonClick(ActionEvent event) {
        System.out.println("button clicked");
        presenter.doSomeThing();
    }

    public void changeFXML() {
        System.out.println("view changing button text");
        button.setText(button.getText() + "..");
    }
}
