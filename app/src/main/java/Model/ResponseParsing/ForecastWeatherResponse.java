package Model.ResponseParsing;

import android.nfc.FormatException;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.common.base.Strings;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Controller.Controller;
import Model.DayWeather;

/**
 * Created by erikkamp on 9/7/14.
 */
public class ForecastWeatherResponse extends ResponseObject {
    public static String TAG_FORECAST = "forecast";
    public static String TAG_SYMBOL = "symbol";
    public static String TAG_VAR = "var";
    public static String TAG_MIN = "min";
    public static String TAG_MAX = "max";
    public static String TAG_WEATHER = "name";
    public static String TAG_CURRENT_TEMP = "day";
    public static String TAG_TIME = "time";

    public ForecastWeatherResponse(String XMLDataString) {
        super(XMLDataString);
    }

    Element response, forecast, symbol, temperature, time;

    private List<Element> forecastList;
    private ArrayList<DayWeather> weeklyWeatherInformation;
    DayWeather dayWeather;
    private String maxTemp, minTemp, symbolText, weatherStirng, currentTemp, day;


    @Override
    protected void pullDataDown() {

        try {
            if (!Strings.isNullOrEmpty(XMLDataString)) {
                Reader reader = new StringReader(XMLDataString);
                Document doc = builder.build(reader);
                response = doc.getRootElement();
                if (response != null) {
                    forecast = response.getChild(TAG_FORECAST);
                    if (forecast != null) {
                        forecastList = forecast.getChildren();
                        if (forecastList != null) {
                            weeklyWeatherInformation = new ArrayList<DayWeather>();
                            for (Element weather : forecastList) {
                                day = weather.getAttributeValue(TAG_CURRENT_TEMP);

                                symbol = weather.getChild(TAG_SYMBOL);
                                if (symbol != null) {
                                    symbolText = symbol.getAttributeValue(TAG_VAR);
                                    weatherStirng = symbol.getAttributeValue(TAG_WEATHER);
                                }
                                temperature = weather.getChild(TAG_TEMP);
                                if (temperature != null) {
                                    minTemp = temperature.getAttributeValue(TAG_MIN);
                                    maxTemp = temperature.getAttributeValue(TAG_MAX);
                                    currentTemp = temperature.getAttributeValue(TAG_CURRENT_TEMP);
                                }
                                dayWeather = new DayWeather(symbolText, weatherStirng, minTemp, maxTemp, currentTemp, getDayFromDate(day));
                                weeklyWeatherInformation.add(dayWeather);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("Parse Error ", e.toString());
        } catch (JDOMException jd) {
            Log.e("Parse Error Jdom2", jd.toString());
        }

        if(weeklyWeatherInformation != null){
            Controller.getBus().post(weeklyWeatherInformation);
        }
    }

    private String getDayFromDate(String dateText) {
        //2014-09-21
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateText);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            switch (dayOfWeek) {
                case 1:
                    return "Sunday";
                case 2:
                    return "Monday";
                case 3:
                    return "Tuesday";
                case 4:
                    return "Wednesday";
                case 5:
                    return "Thursday";
                case 6:
                    return "Friday";
                case 7:
                    return "Saturday";
                default:
                    return "";
            }

        } catch (Exception e) {
            Log.e(getClass().getName(), "Format ERROR: " + e.toString());
        }
        return null;
    }
}
