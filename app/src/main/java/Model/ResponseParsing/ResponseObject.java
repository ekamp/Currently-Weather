package Model.ResponseParsing;

import org.jdom2.input.SAXBuilder;

/**
 * Created by erikkamp on 8/31/14.
 *
 * Parsing class for weather
 */
public abstract class ResponseObject {

    protected String XMLDataString;
    protected static String TAG_TEMP = "temperature", TAG_IMG = "weather";
    protected SAXBuilder builder;

    public ResponseObject(String XMLDataString) {
        this.XMLDataString = XMLDataString;
        builder = new SAXBuilder();
        pullDataDown();
    }

    protected abstract void pullDataDown();
}
