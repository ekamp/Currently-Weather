package Model.Utils;

import com.google.common.base.Strings;

/**
 * Created by Erik Kamp on 1/17/15.
 */
public class FormatUtils {
    public static String formatUserLocation(String mapsLocation) {
        if (!Strings.isNullOrEmpty(mapsLocation)) {
            return mapsLocation.replaceAll("\\s", "+");
        }
        return "";
    }
}
