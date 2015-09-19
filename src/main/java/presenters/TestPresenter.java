package presenters;

import com.google.inject.Inject;
import map.Map;
import view.TestView;

/**
 * Created by brian on 9/19/15.
 */
public class TestPresenter extends Presenter<TestView> {

    @Inject
    Map map;

    public void doSomeThing() {
        System.out.println("presenter doing something");
        map.toString();
        view.changeFXML();
    }
}
