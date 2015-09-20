package presenters;

import com.google.inject.Inject;
import view.View;

/**
 * Created by brian on 9/16/15.
 * Intended superclass for all presenters.
 * Receives {@link PresenterContext} singleton from dependency injector.
 * See {@link PresenterContext}
 */
public class Presenter<T extends View> {
    @Inject
    public PresenterContext context;

    public T view;
}
