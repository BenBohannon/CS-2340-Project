package presenters;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by brian on 9/16/15.
 * Singleton object that is injected into each presenter to allow facilitate
 * simple functions such as Scene swapping while using the same dependency
 * injector.
 * The PresenterContext should receive the dependency injector bindings and Stage from
 * the entry point, which should make for easier testing. A test entry point could
 * simply supply a different module to change the behavior of the program.
 * An instance of PresenterContext additionally binds itself to be injected as a
 * singleton into each {@link Presenter} it generates. This is so that the same
 * dependency injector configuration can be used for every new Presenter generated
 * for an fxml view.
 */
public class PresenterContext {
    private Injector guiceInjector;
    private Stage stage;

    public PresenterContext(Module module, Stage stage) {
        this.stage = stage;

        //create an injector that inserts this injects this instance of PresenterContext//
        guiceInjector = Guice.createInjector((Binder binder) ->
                binder.bind(PresenterContext.class).toInstance(PresenterContext.this));

        //make another injector that still injects this PresenterContext, but also uses a//
        //module from the entry point to guide injections//
        guiceInjector = guiceInjector.createChildInjector(module);
    }

    /**
     * Creates a new {@link Scene} from the fxml file indicated, and sets it as
     * the active Scene in the Stage. If a {@link Presenter} is defined in the FXML, the
     * Presenter is created by the dependency injector and returned.
     * @param fxmlFileName name of the desired fxml pile, relative to the resources/presenters
     *                     directory. For example: "home_screen.fxml"
     * @return The presenter defined in the fxml file, or null if none defined.
     */
    public Presenter showScreen(String fxmlFileName) {
        //create fxml loader for this fxml file//
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));

        //set our injector to generate the Presenter; it will inject this PresenterContext//
        loader.setControllerFactory(guiceInjector::getInstance);
        Parent root = null;

        try {
            root = loader.<Parent>load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root, 800, 800));
        stage.show();

        return loader.getController();
    }

    public Presenter showScreen(String fxmlFileName, int length, int height, boolean isResizable) {
        //create fxml loader for this fxml file//
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));

        //set our injector to generate the Presenter; it will inject this PresenterContext//
        loader.setControllerFactory(guiceInjector::getInstance);
        Parent root = null;

        try {
            root = loader.<Parent>load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root, length, height));
        stage.setResizable(isResizable);
        stage.show();

        return loader.getController();
    }
}
