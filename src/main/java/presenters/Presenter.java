package presenters;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by brian on 9/16/15.
 * Intended superclass for all presenters.
 * Receives {@link PresenterContext} singleton from dependency injector.
 * See {@link PresenterContext}
 */
public class Presenter {
    @Inject
    public PresenterContext context;
}
