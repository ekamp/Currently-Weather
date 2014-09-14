package Controller;

import Model.ResponseParsing.GoogleMapsResponse;
import Model.ResponseParsing.ResponseObject;

/**
 * Created by erikkamp on 8/31/14.
 */
public class QueryGoogleMaps extends OKHttpQuery{
    @Override
    public ResponseObject parseResuts(String result) {

        return new GoogleMapsResponse(result);
    }
}
