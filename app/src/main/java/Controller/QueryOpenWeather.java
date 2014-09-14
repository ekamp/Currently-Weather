package Controller;

import Model.ResponseParsing.ResponseObject;
import Model.ResponseParsing.CurrentWeatherResponse;

/**
 * Created by erikkamp on 8/31/14.
 */
public class QueryOpenWeather extends OKHttpQuery {

    @Override
    public ResponseObject parseResuts(String result) {
        return new CurrentWeatherResponse(result);
    }
}
