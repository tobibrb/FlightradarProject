package dataanalyser;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class GeoDatenBoTest {

    private Flight testFlight = new Flight("test","",52.00f,13.01f,null, 56L, 300, null,
            null, null, null,null, "TXL", "SXF",
            null, null, null,
            "C34", null);
    private Flight testFlightNotInBRB = new Flight("test","",55.00f,13.01f,null, 56L, 300, null,
            null, null, null,null, "TXL", "SXF",
            null, null, null,
            "C34", null);
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
    @Test
    public void isFlightOverBrandenburgTest(){
        assertTrue(GeoDatenBO.isFlightOverBrandenburg(this.testFlight));
    }
    @Test
    public void isFlightOverBrandenburgTestFail(){
        assertFalse(GeoDatenBO.isFlightOverBrandenburg(this.testFlightNotInBRB));
    }

}
