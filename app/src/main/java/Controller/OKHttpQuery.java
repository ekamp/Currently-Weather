package Controller;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import Model.ResponseParsing.ResponseObject;

/**
 * Created by erikkamp on 8/31/14.
 */
public abstract class OKHttpQuery extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... params) {

        Request request = new Request.Builder()
                .url(params[0])
                .build();
        Response response = null;
        try {
            response = Controller.getOkClientInstance().newCall(request).execute();
            return response.body().string();
        } catch (IOException io) {
            Log.e("IOEx while querying", io.toString());
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String serverResponse) {
        //call the parser from here
        parseResuts(serverResponse);
        //Log.e("Server Response ",serverResponse);
    }

    public abstract ResponseObject parseResuts(String result);
}
