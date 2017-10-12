package com.mk.test.moviedbapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
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

    private static String URLHit = "https://api.themoviedb.org/3/movie/upcoming?api_key=3e58c17f67f7e33ee9d262afaaf16fa5&language=en-US";
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
        int totalPages;

        try {
            JSONArray jsonObjectList = new JSONArray();

            URL url = new URL(URLHit);
            urlConnection = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            //return new JSONObject(stringBuffer.toString());

            JSONObject jsonObject =  new JSONObject(stringBuffer.toString());
            totalPages = jsonObject.getInt("total_pages");

            JSONObject jsonObjectOLD = new JSONObject(stringBuffer.toString());

            for (int i = 0; i <= totalPages; i++) {
                String urlWithPage = URLHit + "&page=" + (i + 1);
                urlConnection = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                stringBuffer = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                jsonObject =  new JSONObject(stringBuffer.toString());
                jsonObjectList = concatJSONArray(jsonObjectList, jsonObject.getJSONArray("results"));
            }

            JSONObject jo = new JSONObject();
            jo.put("results",jsonObjectList);

            return jo;

            //return jsonObjectOLD;

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

    private JSONArray concatJSONArray(JSONArray jsonArray1, JSONArray jsonArray2) throws JSONException {
        JSONArray resultJSONArray = new JSONArray();
        for (int i = 0; i < jsonArray1.length(); i++) {
            resultJSONArray.put(jsonArray1.get(i));
        }
        for (int i = 0; i < jsonArray2.length(); i++) {
            resultJSONArray.put(jsonArray2.get(i));
        }
        return resultJSONArray;
    }
}
