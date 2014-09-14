package Model.ResponseParsing;

import android.util.Log;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import Controller.Controller;
import Model.DayWeather;

/**
 * Created by erikkamp on 9/7/14.
 */
public class ForecastWeatherResponse extends ResponseObject {
    public static String TAG_FORECAST = "forecast";
    public static String TAG_SYMBOL = "symbol";
    public static String TAG_VAR =  "var";
    public static String TAG_MIN =  "min";
    public static String TAG_MAX =  "max";
    public static String TAG_WEATHER =  "name";
    public static String TAG_CURRENT_TEMP = "day";
    public ForecastWeatherResponse(String XMLDataString) {
        super(XMLDataString);
    }

    Element response,forecast,symbol,temperature;
    private List<Element> forecastList;
    private ArrayList<DayWeather> weeklyWeatherInformation;
    DayWeather dayWeather;
    private String maxTemp, minTemp, symbolText, weatherStirng, currentTemp;


    @Override
    protected void pullDataDown() {

        try {
            Reader reader = new StringReader(XMLDataString);
            Document doc = builder.build(reader);
            response = doc.getRootElement();
            if(response != null)
            {
                forecast = response.getChild(TAG_FORECAST);
                if(forecast != null)
                {
                    forecastList = forecast.getChildren();
                    if(forecastList != null)
                    {
                        weeklyWeatherInformation = new ArrayList<DayWeather>();
                        for(Element weather : forecastList)
                        {
                            symbol = weather.getChild(TAG_SYMBOL);
                            if(symbol != null)
                            {
                                symbolText = symbol.getAttributeValue(TAG_VAR);
                                weatherStirng = symbol.getAttributeValue(TAG_WEATHER);
                            }
                            temperature = weather.getChild(TAG_TEMP);
                            if(temperature != null)
                            {
                                minTemp = temperature.getAttributeValue(TAG_MIN);
                                maxTemp = temperature.getAttributeValue(TAG_MAX);
                                currentTemp = temperature.getAttributeValue(TAG_CURRENT_TEMP);
                            }
                            dayWeather = new DayWeather(symbolText,weatherStirng,minTemp,maxTemp,currentTemp);
                            weeklyWeatherInformation.add(dayWeather);
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("Parse Error ", e.toString());
        } catch (JDOMException jd) {
            Log.e("Parse Error Jdom2", jd.toString());
        }
        Log.e("Complete Forecast Weather Information ", weeklyWeatherInformation.toString());

        Controller.getBus().post(weeklyWeatherInformation);

    }
}
