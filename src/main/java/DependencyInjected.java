/**
 * Created by brian on 9/12/15.
 */
public class DependencyInjected {

    private static int count;

    private int countAtInst;

    public DependencyInjected() {
        countAtInst = count++;
    }

    public int getCountAtInst() {
        return countAtInst;
    }
}
