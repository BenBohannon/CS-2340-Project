package view;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import presenters.Presenter;

import javafx.scene.text.Font;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.paint.Color;


/**
 * Manages what is presented to the user, such as animations.
 * It forwards info from user input to its {@link Presenter} for interacting
 * with the domain. The generic parameter it should be passed is the
 * type of its Presenter.
 *
 * When and FXML file defines a View to be its fx:controller, the {@link presenters.PresenterContext}
 * will instantiate the View first, then the presenter that the View has as its generic type param T
 * will be injected.
 *
 */
public abstract class View<T extends Presenter> {

    @Inject
    private T presenter;

    public final T getPresenter() {
        return presenter;
    }

    public final void setPresenter(T pPresenter) {
        presenter = pPresenter;
    }

    public void printTextToScreen(String string, double xloc, double yloc, Color color, long startTime, long endTime, Pane pane) {
        //startTime tells the timer when to start after being called. Measured in milliseconds
        Text text = new Text(string);
        Font font = new Font(12);
        text.setFont(font);
        if (color != null) { // pass in null to Paint parameter for text to be window default
            text.setFill(color);
        }
        text.setTranslateX(xloc);
        text.setTranslateY(yloc);
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() ->
                                {
                                    pane.getChildren().add(text);
                                    //OR replace pane with pane2
                                });
                            }
                        },
                startTime);
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() ->
                                {
                                    if(!pane.getChildren().isEmpty() && pane.getChildren().contains(text)) {
                                        pane.getChildren().remove(text);
                                    }
                                });
                            }
                        },
                endTime);
    }
}
