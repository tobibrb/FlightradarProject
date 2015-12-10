package dataanalyser;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tobi on 10.12.2015.
 */
public abstract class AFlightMapper {

    protected static Logger logger = Logger.getLogger(AFlightMapper.class);

    protected AmazonDynamoDBClient client;
    protected DynamoDB dynamoDB;
    protected Table table;
    protected DynamoDBMapper mapper;

    public AFlightMapper() {}

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

    public abstract List<Flight> findAll();
    public abstract void createFlight(Flight flight);
}
