package presenters;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.View;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.IllegalFormatException;

/**
 * Created by brian on 9/16/15.
 * Singleton object that is injected into each presenter to allow facilitate
 * simple functions such as Scene swapping while using the same dependency
 * injector.
 * The PresenterContext should receive as constructor params the dependency injector
 * bindings and Stage from the entry point, which should make for easier testing. A
 * test entry point could simply supply a different module to change the behavior
 * of the program. An instance of PresenterContext additionally binds itself to be
 * injected as a singleton into each {@link Presenter} it generates. This is so that
 * the same dependency injector configuration can be used for every new Presenter
 * generated for an fxml view.
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
     * If a View is designated, the injected Presenter is given a reference to the {@link View}
     * and the presenter is returned
     *
     * The presenter is returned before its initialize() method has been called. you much call it manually.
     *
     * @param fxmlFileName name of the desired fxml file, relative to the resources/presenters
     *                     directory. For example: "home_screen.fxml"
     * @return The presenter defined in the fxml file, or in the View
     */
    public Presenter showScreenUninitialized(String fxmlFileName) {
        //create fxml loader for this fxml file//
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));

        //tell javafx to use our injector to generate controllers//
        //it will inject this PresenterContext and other dependencies//
        loader.setControllerFactory(guiceInjector::getInstance);

        Parent root = null;

        try {
            root = loader.<Parent>load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if root is null, an exception already will have been thrown by the loader //
        stage.setScene(new Scene(root, 890, 490));
        stage.show();

        //Can optionally use View and Presenter or just Presenter//
        //depending on what was specified in the fxml//
        Object handler = loader.getController();

        if (handler instanceof View) {
            //If fxml designated a view, give injected presenter a ref to the view//
            View view = (View) handler;
            if (view.getPresenter() == null) {
                throw new IllegalClassSetupException("View must use generic type of a valid presenter.");
            }
            view.getPresenter().setView(view);
            return view.getPresenter();
        } else {
            //If fxml designated a Presenter, simply return it//
            Presenter p = (Presenter) handler;
            return p;
        }
    }

    /**
     * Creates a new {@link Scene} from the fxml file indicated, and sets it as
     * the active Scene in the Stage. If a {@link Presenter} is defined in the FXML, the
     * Presenter is created by the dependency injector and returned.
     * If a View is designated, the injected Presenter is given a reference to the {@link View}
     * and the presenter is returned.
     * Before it is returned, the presenter's initialize() method is called.
     * @param fxmlFileName name of the desired fxml file, relative to the resources/presenters
     *                     directory. For example: "home_screen.fxml"
     * @return The presenter defined in the fxml file, or in the View
     */
    public Presenter showScreen(String fxmlFileName) {
        Presenter p = showScreenUninitialized(fxmlFileName);
        if (p.getView() != null) {
            p.initialize();
        }
        return p;
    }
}
