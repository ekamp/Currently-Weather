package Model.ResponseParsing;

import android.util.Log;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import Controller.Controller;
import Model.DayWeather;

/**
 * Created by erikkamp on 9/7/14.
 */
public class CurrentWeatherResponse extends ResponseObject {
    private DayWeather weather;
    private Element response, temperature, weatherElement;
    private String minTemp, maxTemp, currentTemp, weatherText, weatherIcon,
            TAG_WEATHER = "weather", TAG_VALUE = "value", TAG_ICON = "icon",
            TAG_MIN = "min", TAG_MAX = "max", TAG_CURRENT = "value";


    public CurrentWeatherResponse(String response) {
        super(response);
    }

    @Override
    protected void pullDataDown() {
        try {
            Reader reader = new StringReader(XMLDataString);
            Document doc = builder.build(reader);
            Element response = doc.getRootElement();

            if (response != null) {
                temperature = response.getChild(TAG_TEMP);
                if (temperature != null) {
                    currentTemp = temperature.getAttributeValue("value");
                    minTemp = temperature.getAttributeValue("min");
                    maxTemp = temperature.getAttributeValue("max");
                }
                weatherElement = response.getChild("weather");
                if (weatherElement != null) {
                    weatherIcon = weatherElement.getAttributeValue("icon");
                    weatherText = weatherElement.getAttributeValue("value");
                }
            }
            if (weatherIcon != null && weatherText != null && currentTemp != null && minTemp != null && maxTemp != null) {
                weather = new DayWeather(weatherIcon, weatherText, minTemp, maxTemp, currentTemp,"");
            }

        } catch (IOException e) {
            Log.e("Parse Error ", e.toString());
        } catch (JDOMException jd) {
            Log.e("Parse Error Jdom2", jd.toString());
        }
        Log.e("Complete Weather Information ", weather.toString());
        Controller.getBus().post(weather);
    }
}
