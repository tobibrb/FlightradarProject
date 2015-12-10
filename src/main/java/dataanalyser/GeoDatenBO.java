package dataanalyser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Notebook on 03.12.2015.
 */
public class GeoDatenBO {
    private static Logger logger = Logger.getLogger(GeoDatenBO.class);
    static String USERNAME = "anderst";

    public static boolean isFlightOverBrandenburg(Flight flight) {
        boolean flightOverBrandenburg = false;
        boolean flightOverBerlin = false;

        if ((flight.getLongitude() >= 11.1605f && flight.getLongitude() <= 14.4605f) &&
                (flight.getLatitude() >= 51.2132f && flight.getLatitude() <= 53.3335f)) {
            flightOverBrandenburg = checkWithGeoNames(flight.getLatitude(), flight.getLongitude(), "Brandenburg");
            flightOverBerlin = checkWithGeoNames(flight.getLatitude(), flight.getLongitude(), "Berlin");
        }
        return (flightOverBrandenburg || flightOverBerlin);
    }

    static boolean checkWithGeoNames(float latitude, float longitude, String searchedRegion) {
        boolean isOverRegion = false;
        String jsonString = getGeoNamesPerRest(latitude, longitude);
        String regionName = "";
        Gson gson = new Gson();

        JsonObject object = gson.fromJson(jsonString, JsonObject.class);
        try {
            regionName = gson.fromJson(object.get("adminName1"), String.class);
            if (searchedRegion.compareTo(regionName) == 0) {
                isOverRegion = true;
            }
        } catch (NullPointerException e) {
            logger.error("Fehler in Geodaten API: " + e.getMessage());
        }
        return isOverRegion;
    }

    private static String getGeoNamesPerRest(float latitude, float longitude) {
        String response = "";
        String url = "http://api.geonames.org/countrySubdivisionJSON?formatted=true&lat=" + latitude + "&lng=" + longitude + "&username=" + USERNAME;
        System.out.println(url);

        InputStream is = null;
        try {
                is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            response = readAll(rd);
        } catch (IOException e) {
            logger.error("Got exception: " + e.getMessage());
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private static String readAll(Reader rd) throws IOException {

        BufferedReader reader = new BufferedReader(rd);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
