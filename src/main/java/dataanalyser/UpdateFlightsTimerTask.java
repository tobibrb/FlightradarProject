package dataanalyser;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Bartz, Tobias @Tobi-PC on 10.12.2015 at 14:10.
 */
public class UpdateFlightsTimerTask extends TimerTask {

    private static final String APIURL = "http://krk.data.fr24.com/zones/fcgi/feed.json?array=0&bounds=54.0,50.0,10.0,15.0";
    // private static final String APIURL = "http://data.flightradar24.com/zones/fcgi/full.json";
    // private static final String APIURL = "http://data.flightradar24.com/zones/fcgi/full_all.json";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private static FlightBo flightBo = new FlightBo();
    private static Logger logger = Logger.getLogger(UpdateFlightsTimerTask.class);

    public UpdateFlightsTimerTask() {}

    @Override
    public void run() {
        try {
            URL url = new URL(APIURL);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                stringBuilder.append(inputLine);
            in.close();
            String jsonString = stringBuilder.toString();
            List<Flight> flights = FlightBo.parseFlightsFromJson(jsonString);
            for (Flight flight : flights) {
                if (GeoDatenBO.isFlightOverBrandenburg(flight)) {
                    flightBo.createFlight(flight);
                }
            }
        } catch (IOException e) {
            logger.error("Got Exception: " + e.getMessage());
        }
    }
}
