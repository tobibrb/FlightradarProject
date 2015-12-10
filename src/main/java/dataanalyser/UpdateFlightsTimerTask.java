package dataanalyser;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * TimerTask zum Holen der Daten und Schreiben in die Datenbank
 * <p/>
 * Created by Bartz, Tobias @Tobi-PC on 10.12.2015 at 14:10.
 *
 * @author Tobias Bartz
 */
public class UpdateFlightsTimerTask extends TimerTask {

    // Flightradar API URL
    private static final String APIURL = "http://krk.data.fr24.com/zones/fcgi/feed.json?array=0&bounds=54.0,50.0,10.0,15.0";
    // private static final String APIURL = "http://data.flightradar24.com/zones/fcgi/full.json";
    // private static final String APIURL = "http://data.flightradar24.com/zones/fcgi/full_all.json";

    // User-Agent zum Faken eines Browsers
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private static FlightBo flightBo = new FlightBo();
    private static Logger logger = Logger.getLogger(UpdateFlightsTimerTask.class);

    /**
     * Default Konstruktor
     */
    public UpdateFlightsTimerTask() {
    }

    @Override
    public void run() {
        try {
            URL url = new URL(APIURL);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);

            // Lese die Daten von der URLConnection
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                stringBuilder.append(inputLine);
            in.close();

            // Daten als JSON String speichern
            String jsonString = stringBuilder.toString();

            // Flüge aus JSON String parsen
            List<Flight> flights = FlightBo.parseFlightsFromJson(jsonString);

            // alte Flüge löschen
            flightBo.deleteOldFlights();

            // prüfen ob Flüge über Berlin/Brandenburg
            List<Flight> flightsOverBrandenburg = new ArrayList<>();
            for (Flight flight : flights) {
                if (GeoDatenBO.isFlightOverBrandenburg(flight)) {
                    // In Datenbank schreiben
                    flightsOverBrandenburg.add(flight);
                }
            }
            // Flüge speichern
            flightBo.createFlights(flightsOverBrandenburg);


        } catch (IOException e) {
            logger.error("Got Exception: " + e.getMessage());
        }
    }
}
