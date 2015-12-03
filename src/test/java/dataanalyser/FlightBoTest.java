package dataanalyser;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Bartz, Tobias @Tobi-PC on 03.12.2015 at 19:06.
 */
public class FlightBoTest {

    private String jsonString = "{\"full_count\":11178,\"version\":4," +
            "\"827d225\":[\"\",2.3962,-46.9981,311,45000,366,\"0000\",\"F-SBBE3\",\"E55P\",\"\",1449150155,\"\",\"\",\"\",0,0,\"E55P\",0]\n" +
            ",\"827ecf1\":[\"\",7.1661,-79.3709,173,45000,440,\"1402\",\"F-MPEJ1\",\"CL30\",\"\",1449150152,\"\",\"\",\"\",0,-128,\"CL30\",0]\n" +
            ",\"8280888\":[\"\",36.7235,-86.7582,249,45000,380,\"3602\",\"T-KCKV1\",\"LJ45\",\"\",1449150154,\"\",\"\",\"\",0,-128,\"LJ45\",0]\n" +
            ",\"8280f33\":[\"\",35.5390,-77.1989,205,45000,320,\"1762\",\"F-KEWN2\",\"C25B\",\"\",1449150153,\"\",\"\",\"\",0,0,\"C25B\",0]\n" +
            ",\"8280f35\":[\"\",33.2068,-77.8896,195,45000,398,\"3362\",\"T-MLAT2\",\"HA4T\",\"\",1449150147,\"\",\"\",\"\",0,0,\"HA4T\",0]\n" +
            ",\"8280fa6\":[\"\",41.8702,-82.5570,262,45000,448,\"3477\",\"F-KBKL1\",\"GLF5\",\"\",1449150154,\"\",\"\",\"\",0,0,\"GLF5\",0]\n" +
            ",\"82810d5\":[\"\",40.5860,19.1537,271,45000,450,\"2340\",\"F-LGPZ1\",\"GLEX\",\"\",1449150154,\"\",\"\",\"\",0,0,\"GLEX\",0]\n" +
            ",\"828200a\":[\"\",35.7188,-104.5455,129,45000,490,\"6074\",\"T-MLAT2\",\"\",\"\",1449150147,\"\",\"\",\"\",0,0,\"BLOCKED\",0]\n" +
            ",\"828229e\":[\"\",45.7100,-70.8311,75,45000,523,\"5423\",\"T-MLAT2\",\"LJ45\",\"\",1449150150,\"\",\"\",\"\",0,0,\"LJ45\",0]\n" +
            ",\"8282835\":[\"\",31.6693,-99.4673,299,45000,384,\"2746\",\"T-MLAT2\",\"\",\"\",1449150153,\"\",\"\",\"\",0,0,\"BLOCKED\",0]\n" +
            ",\"8283b93\":[\"\",24.6452,57.4002,294,45000,440,\"4013\",\"T-MLAT\",\"GLEX\",\"\",1449150148,\"\",\"\",\"\",0,0,\"GLEX\",0]\n" +
            "}";

    @Test
    public void test() {
        assertTrue(FlightMapper.parseFlightsFromJson(jsonString).size() == 11);
    }
}
