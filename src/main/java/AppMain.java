import dataanalyser.Flight;
import dataanalyser.FlightBo;
import dataanalyser.GeoDatenBO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Tobi on 03.12.2015.
 */

@Controller
@EnableAutoConfiguration
public class AppMain {
    private static FlightBo flightBo;

    private static final String APIURL = "http://krk.data.fr24.com/zones/fcgi/feed.json?array=0&bounds=54.0,50.0,10.0,15.0";
    // private static final String APIURL = "http://data.flightradar24.com/zones/fcgi/full.json";
    // private static final String APIURL = "http://data.flightradar24.com/zones/fcgi/full_all.json";

    @RequestMapping("/")
    @ResponseBody
    String home() {

        /* ---------------------------------------------------------
         * Zeigen der Daten im Browser
         * ---------------------------------------------------------
         */

        List<Flight> flights = flightBo.findAll();
        StringBuilder builder = new StringBuilder();
        builder.append("Anzahl: ");
        builder.append(flights.size());
        builder.append("<br>");
        for (Flight flight : flights) {
            builder.append(flight);
            builder.append("<br>");
        }
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {

        /* --------------------------------------------------------
         * TEST: Holen und Speichern
         * --------------------------------------------------------
         */

        URL url = new URL(APIURL);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            stringBuilder.append(inputLine);
        in.close();
        String jsonString = stringBuilder.toString();
        List<Flight> flights = FlightBo.parseFlightsFromJson(jsonString);
        flightBo = new FlightBo();
        for (Flight flight : flights) {
            if (GeoDatenBO.isFlightOverBrandenburg(flight)) {
                flightBo.createFlight(flight);
            }
        }


        /* ----------------------------------------------
         * Spring App starten
         * ----------------------------------------------
         */
        SpringApplication.run(AppMain.class, args);
    }
/*
TODO
 @RequestMapping("/subscribe") mit Queryparameter ?email=email-adresse
 -respone mit Messaage
 fügt E-Mail Adresse zu E-mail Liste auf S3 hinzu.
  @RequestMapping("/unsubscribe") mit Queryparameter ?email=email-adresse
  -response mit Message
  liest E-Mail liste von S3 und löscht E-Mail--> neue Datei hochladen zu S3
 */
}
