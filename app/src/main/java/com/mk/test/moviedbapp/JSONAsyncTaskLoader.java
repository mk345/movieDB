package com.mk.test.moviedbapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Michael on 10/10/2017.
 */

public class JSONAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {

    private static String URLHit = "https://api.themoviedb.org/3/movie/upcoming?api_key=KEY&language=en-US&page=1";
    private static String TAG = "JSON AsyncTask Loader";

    private JSONObject mJSONObject;

    public JSONAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mJSONObject != null) {
            deliverResult(mJSONObject);
        } else {
            forceLoad();
        }
    }

    @Override
    public JSONObject loadInBackground() {

        URLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(URLHit);
            urlConnection = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            //return stringBuffer.toString();
            return new JSONObject(stringBuffer.toString());
        } catch(Exception ex) {
            Log.e(TAG, "AsyncTaskData", ex);
            return null;
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deliverResult(JSONObject jSONObject) {
        mJSONObject = jSONObject;
        super.deliverResult(jSONObject);
    }
}
