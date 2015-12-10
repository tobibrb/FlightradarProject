package dataanalyser;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.michelboudreau.alternator.AlternatorDB;
import com.michelboudreau.alternatorv2.AlternatorDBClientV2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Tobi on 10.12.2015.
 */
public class FlightBoIT {

    private static AlternatorDBClientV2 client;
    private static DynamoDBMapper mapper;
    private static AlternatorDB db;

    @Before
    public void setUp() throws Exception {
        client = new AlternatorDBClientV2();
        mapper = new DynamoDBMapper(client);
        db = new AlternatorDB().start();

        CreateTableRequest request = mapper.generateCreateTableRequest(Flight.class);
        request.setProvisionedThroughput(new ProvisionedThroughput(10L, 5L));

        client.createTable(request);
    }

    @After
    public void tearDown() throws Exception {
        db.stop();
    }

    @Test
    public void createFlightsIntegrationTest(){
        FlightBoMock mapperMock = new FlightBoMock();
        List<Flight> flights = FlightBoMock.parseFlightsFromJson(FlightBoTest.jsonString);
        assertTrue(flights.size() == 11);
        for (Flight flight : flights) {
            mapperMock.createFlight(flight);
        }
        assertTrue(mapperMock.findAll().size() == 11);
    }

    private static class FlightBoMock extends FlightBo {

        public FlightBoMock() {
            this.mapper = FlightBoIT.mapper;
        }
    }
}
