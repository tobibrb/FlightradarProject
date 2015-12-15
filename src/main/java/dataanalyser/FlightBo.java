package dataanalyser;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FlightBo zum Schreiben und Lesen der Flüge aus der Datenbank.
 * <p/>
 * Created by Bartz, Tobias @Tobi-PC on 03.12.2015 at 14:56.
 *
 * @author Tobias Bartz
 */
public class FlightBo extends AFlightBo {

    /* Endpoint für lokale DynamoDB
    private static final String ENDPOINT = "http://localhost:8000";
    */
    private static Logger logger = Logger.getLogger(FlightBo.class);

    protected static final String TABLENAME = "Flightdata";

    /**
     * Default Konstruktor.
     * <p/>
     * Erstellt das DynamoDB Table bei AWS falls es nicht existiert.
     */
    public FlightBo() {
        logger.debug("Connecting to DynamoDB");

        /* Für lokale DynamoDB
        client = new AmazonDynamoDBClient(new BasicAWSCredentials("Fake", "Fake"));
        client.setEndpoint(ENDPOINT);
        */

        // AWS DynamoDB
        client = new AmazonDynamoDBClient(new ProfileCredentialsProvider("dynamodb"));
        dynamoDB = new DynamoDB(client);
        mapper = new DynamoDBMapper(client);
        logger.debug("Successfully connected to DynamoDB");

        // Falls Table nicht existiert -> erstellen
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

    /**
     * Flugdaten in Datenbank speichern
     *
     * @param flight Flugdaten die gespeichert werden sollen
     */
    public void createFlight(Flight flight) {
        logger.debug("Saving Flight: " + flight);
        mapper.save(flight);
    }

    public void createFlights(List<Flight> flights) {
        logger.debug("Writing " + flights.size() + " Flights");
        mapper.batchSave(flights);
    }

    /**
     * Alle Daten aus Datenbank holen
     *
     * @return Liste aller Flugdaten
     */
    public List<Flight> findAll() {
        logger.debug("Querying data from table " + TABLENAME);
        return mapper.scan(Flight.class, new DynamoDBScanExpression());
    }

    /**
     * Alle Flugdaten, die älter als 10 Minuten sind löschen
     */
    public void deleteOldFlights() {
        List<Flight> flights = mapper.scan(Flight.class, new DynamoDBScanExpression());
        List<Flight> oldFlights = new ArrayList<>();
        long oldDate = new Date().getTime() - (10 * 60 * 1000);
        for (Flight flight : flights) {
            long timestampFlight = flight.getTimestamp() * 1000;
            if (timestampFlight < oldDate) {
                logger.debug("Adding Flight to delete request: " + flight);
                oldFlights.add(flight);
            }
        }
        mapper.batchDelete(oldFlights);
    }
}
