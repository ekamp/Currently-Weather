package Controller;

import Model.ResponseParsing.CurrentWeatherResponse;
import Model.ResponseParsing.ForecastWeatherResponse;
import Model.ResponseParsing.ResponseObject;

/**
 * Created by erikkamp on 8/31/14.
 */
public class QueryOpenWeatherForecast extends OKHttpQuery {

    @Override
    public ResponseObject parseResuts(String result) {
        return new ForecastWeatherResponse(result);
    }
}
