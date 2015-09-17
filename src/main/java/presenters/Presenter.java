package presenters;

import com.google.inject.Inject;

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
