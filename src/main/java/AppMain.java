import dataanalyser.*;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.EmailRestService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * Created by Tobi on 03.12.2015.
 */

@Controller
@EnableAutoConfiguration
public class AppMain {
    private static AFlightBo flightBo;



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
        Timer timer = new Timer();
        flightBo = new FlightBo();
        /* ----------------------------------------------
         * Spring App starten
         * ----------------------------------------------
         */
        Object [] sources = new Object [] {EmailRestService.class, AppMain.class};
        SpringApplication.run(sources, args);

        /* ----------------------------------------------
         * Timer starten
         * ----------------------------------------------
         */
        timer.scheduleAtFixedRate(new UpdateFlightsTimerTask(), 1000, 15 * 60 * 1000);
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
