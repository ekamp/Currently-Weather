package Controller;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.common.base.Strings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import Model.ResponseParsing.GetAddressEvent;
import Model.Utils.FormatUtils;

/**
 * Taken from https://developer.android.com/training/location/display-address.html
 * <p/>
 * A subclass of AsyncTask that calls getFromLocation() in the
 * background. The class definition has these generic types:
 * Location - A Location object containing
 * the current location.
 * Void     - indicates that progress units are not used
 * String   - An address passed to onPostExecute()
 */
public class GetAddressTask extends
        AsyncTask<Location, Void, String> {
    Context mContext;

    public GetAddressTask(Context context) {
        super();
        mContext = context;
    }

    /**
     * Get a Geocoder instance, get the latitude and longitude
     * look up the address, and return it
     *
     * @return A string containing the address of the current
     * location, or an empty string if no address can be found,
     * or an error message
     * @params params One or more Location objects
     */
    @Override
    protected String doInBackground(Location... params) {
        Geocoder geocoder =
                new Geocoder(mContext, Locale.getDefault());
        // Get the current location from the input parameter list
        Location loc = params[0];
        // Create a list to contain the result address
        List<Address> addresses = null;
        try {
            if(loc != null){
                addresses = geocoder.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
            }else{
                return "address not found";
            }
        } catch (IOException e1) {
            Log.e("LocationSampleActivity",
                    "IO Exception in getFromLocation()");
            e1.printStackTrace();
            return ("IO Exception trying to get address");
        } catch (Exception e2) {
            // Error message to post in the log
            String errorString = "Illegal arguments " +
                    Double.toString(loc.getLatitude()) +
                    " , " +
                    Double.toString(loc.getLongitude()) +
                    " passed to address service";
            Log.e("LocationSampleActivity", errorString);
            e2.printStackTrace();
            return errorString;
        }
        // If the reverse geocode returned an address
        if (addresses != null && addresses.size() > 0) {
            // Get the first address
            Address address = addresses.get(0);
                /*
                 * Format the first line of address (if available),
                 * city, and country name.
                 */
            String addressText = String.format(
                    "%s, %s, %s",
                    // If there's a street address, add it
                    address.getMaxAddressLineIndex() > 0 ?
                            address.getAddressLine(0) : "",
                    // Locality is usually a city
                    address.getLocality(),
                    // The country of the address
                    address.getCountryName());
            // Return the text
            return addressText;
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String address) {
        if(!Strings.isNullOrEmpty(address) && !address.equalsIgnoreCase("address not found")){
            Controller.getBus().post(new GetAddressEvent(FormatUtils.formatUserLocation(address)));
        }else{
            Controller.getBus().post(new GetAddressEvent(FormatUtils.formatUserLocation("")));
        }
    }
}
