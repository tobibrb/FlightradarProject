package dataanalyser;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Bartz, Tobias @Tobi-PC on 03.12.2015 at 14:56.
 */
public class FlightBo extends AFlightBo {

    //local DynamoDB installation
    //private static final String ENDPOINT = "http://localhost:8000";

    private static Logger logger = Logger.getLogger(FlightBo.class);

    protected static final String TABLENAME = "Flightdata";

    public FlightBo() {
        logger.debug("Connecting to DynamoDB");
        // local DynamoDB
        //client = new AmazonDynamoDBClient(new BasicAWSCredentials("Fake", "Fake"));
        //client.setEndpoint(ENDPOINT);
        // AWS DynamoDB
        client = new AmazonDynamoDBClient(new ProfileCredentialsProvider("dynamodb"));
        dynamoDB = new DynamoDB(client);
        mapper = new DynamoDBMapper(client);
        logger.debug("Successfully connected to DynamoDB");
        /*if (client.listTables().getTableNames().contains(TABLENAME)) {
            logger.debug("Table exists. Going to remove it.");
            client.deleteTable(TABLENAME);
        }*/
        if (!client.listTables().getTableNames().contains(TABLENAME)) {
            logger.debug("Table does not exist. Creating...");
            CreateTableRequest request = mapper.generateCreateTableRequest(Flight.class);
            request.setProvisionedThroughput(new ProvisionedThroughput(10L, 5L));
            table = dynamoDB.createTable(request);
            try {
                table.waitForActive();
            } catch (Exception e) {
                logger.error("Got Exception: " + e.getMessage());
            }
        }
    }

    public void createFlight(Flight flight) {
        logger.debug("Saving Flight: " + flight);
        mapper.save(flight);
    }

    public List<Flight> findAll() {
        logger.debug("Querying data from table " + TABLENAME);
        return mapper.scan(Flight.class, new DynamoDBScanExpression());
    }

}
