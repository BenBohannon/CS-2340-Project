import javafx.scene.paint.Color;
import model.entity.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by brian on 11/9/15.
 */
public class TestColorConverter {

    private Player.ColorConverter colorConverter;
    private Color[] testColors;
    private int[] testInts;

    @Before
    public void setUp() {
        colorConverter = new Player.ColorConverter();
        Color[] colors = {Color.WHEAT, Color.ALICEBLUE, Color.OLDLACE, Color.ANTIQUEWHITE, Color.AQUA};
        testColors = colors;

        testInts = new int[colors.length];
        // Color#hashcode() produces the int representation of a color //
        for (int colorIndex = 0; colorIndex < colors.length; colorIndex++) {
            testInts[colorIndex] = testColors[colorIndex].hashCode();
        }
    }

    @Test
    public void testToDatabaseColumn() {
        int[] observedInts = new int[testColors.length];

        for (int colorIndex = 0; colorIndex < testColors.length; colorIndex++) {
            observedInts[colorIndex] = colorConverter.convertToDatabaseColumn(testColors[colorIndex]);
        }

        Assert.assertArrayEquals("results not equal!", testInts, observedInts);
    }

    @Test
    public void testToEntityAttribute() {
        Color[] observedColors = new Color[testColors.length];

        for (int colorIndex = 0; colorIndex < testColors.length; colorIndex++) {
            observedColors[colorIndex] = colorConverter.convertToEntityAttribute(testInts[colorIndex]);
        }

        Assert.assertArrayEquals("results not equal!", testColors, observedColors);
    }

    @Test
    public void testIntegrated() {
        for (int colorIndex = 0; colorIndex < testColors.length; colorIndex++) {
            int converted = colorConverter.convertToDatabaseColumn(testColors[colorIndex]);

            Color convertedBack = colorConverter.convertToEntityAttribute(converted);

            Assert.assertEquals("not equal!", testColors[colorIndex], convertedBack);
        }
    }
}
