package view;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import presenters.Presenter;


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
    public T presenter;
}
