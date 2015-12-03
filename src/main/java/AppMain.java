import dataanalyser.Flight;
import dataanalyser.FlightMapper;
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
    private static FlightMapper mapper;

    @RequestMapping("/")
    @ResponseBody
    String home() {

        /* ---------------------------------------------------------
         * Zeigen der Daten im Browser
         * ---------------------------------------------------------
         */

        StringBuilder builder = new StringBuilder();
        for (Flight flight : mapper.findAll()) {
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

        URL url = new URL("http://data.flightradar24.com/zones/fcgi/germany_all.json");
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
        List<Flight> flights = FlightMapper.parseFlightsFromJson(jsonString);
        mapper = new FlightMapper();
        for (Flight flight : flights) {
            mapper.createFlight(flight);
        }


        /* ----------------------------------------------
         * Spring App starten
         * ----------------------------------------------
         */
        SpringApplication.run(AppMain.class, args);
    }

}
