package view;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import presenters.Presenter;


/**
 * Created by brian on 9/18/15.
 * Manages what is presented to the user, such as animations.
 * It forwards info from user input to its {@link Presenter} for interacting
 * with the domain. The generic parameter it should be passed is the
 * type of its Presenter.
 */
public abstract class View<T extends Presenter> {

    @Inject
    public T presenter;
}
