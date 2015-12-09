package dataanalyser;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.google.gson.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Bartz, Tobias @Tobi-PC on 03.12.2015 at 14:56.
 */
public class FlightMapper {

    //local DynamoDB installation
    //private static final String ENDPOINT = "http://localhost:8000";

    private static Logger logger = Logger.getLogger(FlightMapper.class);

    private static final String TABLENAME = "Flightdata";

    private AmazonDynamoDBClient client;
    private DynamoDB dynamoDB;
    private Table table;
    private DynamoDBMapper mapper;

    public FlightMapper() {
        // local DynamoDB
        //client = new AmazonDynamoDBClient(new BasicAWSCredentials("Fake", "Fake"));
        //client.setEndpoint(ENDPOINT);
        // AWS DynamoDB
        client = new AmazonDynamoDBClient(new ProfileCredentialsProvider("dynamodb"));
        dynamoDB = new DynamoDB(client);
        mapper = new DynamoDBMapper(client);
        /*if (client.listTables().getTableNames().contains(TABLENAME)) {
            logger.debug("Table exists. Going to remove it.");
            client.deleteTable(TABLENAME);
        }*/
        if (!client.listTables().getTableNames().contains(TABLENAME)) {
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
        return mapper.scan(Flight.class, new DynamoDBScanExpression());
    }

    public static List<Flight> parseFlightsFromJson(String jsonString) {
        List<Flight> list = new ArrayList<>();
        Gson gson = new Gson();
        JsonObject object = gson.fromJson(jsonString, JsonObject.class);
        Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            try {
                JsonArray array = entry.getValue().getAsJsonArray();
                list.add(new Flight(entry.getKey(),                 // ID
                        gson.fromJson(array.get(0), String.class),  // Hexcode
                        gson.fromJson(array.get(1), Float.class),   // Latitude
                        gson.fromJson(array.get(2), Float.class),   // Longitude
                        gson.fromJson(array.get(3), Integer.class), // Track
                        gson.fromJson(array.get(4), Long.class),    // altitude
                        gson.fromJson(array.get(5), Integer.class), // speed
                        gson.fromJson(array.get(6), String.class),  // squawk
                        gson.fromJson(array.get(7), String.class),  // radarType
                        gson.fromJson(array.get(8), String.class),  // planeType
                        gson.fromJson(array.get(9), String.class),  // planeRegistration
                        gson.fromJson(array.get(10), Long.class),   // timestamp
                        gson.fromJson(array.get(11), String.class), // start
                        gson.fromJson(array.get(12), String.class), // destination
                        gson.fromJson(array.get(13), String.class), // flightNumber
                        gson.fromJson(array.get(14), Long.class),   // unknown1
                        gson.fromJson(array.get(15), Long.class),   // unknown2
                        gson.fromJson(array.get(16), String.class), // callsign
                        gson.fromJson(array.get(17), Long.class)    // unknown3
                ));
            } catch (IllegalStateException e) {
                logger.debug("JSON does not contain an Array. Ignoring...");
            }
        }
        return list;
    }


}
