package dataanalyser;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class GeoDatenBoTest {

    private String testString= "Brandenburg";
    private float longitudeNotBb = 10.01f;
    private float longitudeInBb = 13.01f;
    private float latitudeNotBb = 54.10f;
    private float latitudeInBb = 52.00f;

    @Test
    public void getGeoNamesPerRestTestTrue() {
        assertTrue(GeoDatenBO.checkWithGeoNames(latitudeInBb, longitudeInBb, testString));
    }
    @Test
    public void getGeoNamePerRestTestFail(){
        assertFalse(GeoDatenBO.checkWithGeoNames(latitudeNotBb, longitudeNotBb, testString));
    }

}
