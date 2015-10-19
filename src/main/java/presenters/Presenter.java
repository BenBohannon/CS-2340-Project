package presenters;

import com.google.inject.Inject;
import view.View;

/**
 * Superclass for logical drivers of the application that sit between the Model and the View
 * Receives {@link PresenterContext} singleton from dependency injector.
 * @see PresenterContext
 *
 * When a Presenter is specified as a fx:controller in a FXML file, the Presenter is instantiated
 * when the PresenterContext is told to show an xml file. Its dependencies are then instantiated and injected.
 *
 * If a View is specified as the fx:controller of an FXML file, then the View is instantiated first, and then
 * the Presenter, which is injected into the view. The PresenterContext then injects a reference to the View
 * into the Presenter as well.
 * @see View
 */
public class Presenter<T extends View> {
    @Inject
    public PresenterContext context;

    public T view;

    public void initialize() {

    }
}
