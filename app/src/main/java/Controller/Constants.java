package Controller;

/**
 * Contains and formats query information.
 *
 * @author Erik Kamp
 * @since v1.0
 */
public class Constants {

    private static String APIKEY = "AIzaSyDn8eWf2Cnl8vZs3RKpGQ3NG1ge0BcDDLU";
    public static final String defaultCity = "New York";
    public static final String defaultStartAddress = "148+Spruce+Drive+Shrewsbury+NJ+07702";
    public static final String defaultEndAddress = "15+Corporate+Place+Piscataway+NJ";

    public static String gatherForecastData(String city) {
        return "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "&mode=xml&units=imperial&cnt=7";
    }

    public static String gatherCurrentWeatherData(String city) {
        return "http://api.openweathermap.org/data/2.5/weather?q="+city+"&mode=xml&units=imperial";
    }

    public static String getWeatherImage(String imageID) {
        return "http://openweathermap.org/img/w/" + imageID + ".png";
    }

    public static String gatherETAData(String origin, String destination) {
        return "https://maps.googleapis.com/maps/api/directions/xml?origin="+origin+"&destination="+destination+"&key="+APIKEY;
    }
}
