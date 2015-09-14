import com.google.inject.*;
import map.Locatable;
import map.LocationDatasource;
import map.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by brian on 9/13/15.
 */
public class MapTests {

    Map map;
    Injector injector;
    TestLocationDatasource lds;

    static int idCount = 0;
    public static int getId() {
        return idCount++;
    }

    @Before
    public void setUp() {
        lds = new TestLocationDatasource();
        Module testModule = new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(LocationDatasource.class).toInstance(lds);
            }
        };
        injector = Guice.createInjector(testModule);
    }

    @Test
    public void testBasicSetup() {
        map = injector.getInstance(Map.class);

        isDataEqual(map, lds.locationData);
    }

    @Test
    public void testAdding() {
        map = injector.getInstance(Map.class);
        for (int i = 0; i < lds.locationData.length; i++) {
            for (int j = 0; j < lds.locationData[0].length; j++) {
                if (Math.random() > .1f) {
                    LocatableA a = new LocatableA();
                    lds.locationData[i][j].add(a);
                    map.add(a, i, j);
                }
                if (Math.random() > .1f) {
                    LocatableB b = new LocatableB();
                    lds.locationData[i][j].add(b);
                    map.add(b, i, j);
                }
                if (Math.random() > .1f) {
                    LocatableC c = new LocatableC();
                    lds.locationData[i][j].add(c);
                    map.add(c, i, j);
                }
            }
        }
        isDataEqual(map, lds.locationData);
    }

    @Test
    public void testRemoval() {
        map = injector.getInstance(Map.class);
        for (int i = 0; i < lds.locationData.length; i++) {
            for (int j = 0; j < lds.locationData[0].length; j++) {
                if (Math.random() > .3f && lds.locationData[i][j].size() > 0) {
                    Locatable locatable = lds.locationData[i][j].iterator().next();
                    lds.locationData[i][j].remove(locatable);
                    map.remove(locatable);
                }
            }
        }
        isDataEqual(map, lds.locationData);
    }

    @Test
    public void testMoving() {
        map = injector.getInstance(Map.class);

        for (int i = 0; i < lds.locationData.length; i++) {
            for (int j = 0; j < lds.locationData[0].length; j++) {
                if (Math.random() > .3f && lds.locationData[i][j].size() > 0) {
                    Locatable locatable = lds.locationData[i][j].iterator().next();
                    int newRow = (int) (Math.random() * lds.locationData.length);
                    int newCol = (int) (Math.random() * lds.locationData[0].length);
                    lds.locationData[i][j].remove(locatable);
                    lds.locationData[newRow][newCol].add(locatable);

                    map.move(locatable, newRow, newCol);
                }
            }
        }
        isDataEqual(map, lds.locationData);
    }

    @Test
    public void testGetOccupantsTyped() {
        map = injector.getInstance(Map.class);

        for (int i = 0; i < lds.locationData.length; i++) {
            for (int j = 0; j < lds.locationData[0].length; j++) {
                LocatableA[] as = map.getOccupants(i, j, LocatableA.class);
                LocatableB[] bs = map.getOccupants(i, j, LocatableB.class);
                LocatableC[] cs = map.getOccupants(i, j, LocatableC.class);

                Collection<Locatable> expecteds = lds.get(i,j);
                for (Locatable e : expecteds) {
                    if (e instanceof LocatableA) {
                        Assert.assertTrue("getOccupants(LocatableA) does not contain a LocatableA that it should", arrayContains(as, e));
                    }
                    if (e instanceof LocatableB) {
                        Assert.assertTrue("getOccupants(LocatableB) does not contain a LocatableB that it should", arrayContains(bs, e));
                    }
                    if (e instanceof LocatableC) {
                        Assert.assertTrue("getOccupants(LocatableC) does not contain a LocatableC that it should", arrayContains(cs, e));
                    }
                }

                for (Locatable a : as) {
                    Assert.assertTrue(a instanceof LocatableA);
                }
                for (Locatable b : bs) {
                    Assert.assertTrue(b instanceof LocatableB);
                }
                for (Locatable c : cs) {
                    Assert.assertTrue(c instanceof LocatableC);
                }
            }
        }
    }

    private boolean arrayContains(Locatable[] array, Locatable e) {
        for (Locatable loc : array) {
            if (e.equals(loc)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDataEqual(Map map,  Collection<Locatable>[][] locationData) {

        for (int i = 0; i < locationData.length; i++) {
            for (int j = 0; j < locationData[0].length; j++) {
                Locatable[] locatables = map.getOccupants(i, j);
                Assert.assertEquals(locationData[i][j].size(), locatables.length);
                for (Locatable locatable : locatables) {
                    Collection<Locatable> expecteds = locationData[i][j];
                    boolean foundIt = false;
                    for (Locatable expected : expecteds) {
                        if (expected.equals(locatable)) {
                            foundIt = true;
                            break;
                        }
                    }
                    Assert.assertTrue(String.format("problem at [%d][%d]", i, j), foundIt);
                }
            }
        }
        return true;
    }

}
