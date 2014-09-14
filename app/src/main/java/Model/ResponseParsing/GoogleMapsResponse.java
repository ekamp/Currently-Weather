package Model.ResponseParsing;

import android.util.Log;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import Controller.Controller;
import Model.CommuteData;

/**
 * Created by erikkamp on 9/7/14.
 */
public class GoogleMapsResponse extends ResponseObject {
    private CommuteData commuteData;
    public static final String TAG_STATUS = "status";
    public static final String TAG_ROUTE = "route";
    public static final String TAG_SUMMARY = "summary";
    public static final String TAG_LEG = "leg";
    public static final String TAG_DURATION = "duration";
    public static final String TAG_DISTANCE = "distance";

    public GoogleMapsResponse(String XMLDataString) {
        super(XMLDataString);
    }


    @Override
    protected void pullDataDown() {
//        Log.e("Response Map",XMLDataString);

        Element response, summary, route, leg, distance, time;
        String timeText = null, distanceText = null, summaryText = null;
        try {
            Reader reader = new StringReader(XMLDataString);
            Document doc = builder.build(reader);
            response = doc.getRootElement();
            if (response != null && response.getChildText(TAG_STATUS).equals("OK")) {
                route = response.getChild(TAG_ROUTE);
                if (route != null) {
                    summary = route.getChild(TAG_SUMMARY);
                    leg = route.getChild(TAG_LEG);
                    if (summary != null) {
                        summaryText = summary.getText();
                    }
                    if (leg != null) {
                        distance = leg.getChild(TAG_DISTANCE);
                        time = leg.getChild(TAG_DURATION);
                        if (distance != null) {
                            distanceText = distance.getChildText("text");
                        }
                        if (time != null) {
                            timeText = time.getChildText("text");
                        }
                    }
                }
                if (distanceText != null && timeText != null && summaryText != null) {
                    commuteData = new CommuteData(summaryText, timeText, distanceText);
                }
            }
        } catch (IOException e) {
            Log.e("Parse Error ", e.toString());
        } catch (JDOMException jd) {
            Log.e("Parse Error Jdom2", jd.toString());
        }

        Log.e("Complete Maps Information ", commuteData.toString());
        //Send the information thru the bus back to our view
        Controller.getBus().post(commuteData);

    }
}
