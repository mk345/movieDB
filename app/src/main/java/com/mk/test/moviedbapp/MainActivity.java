package com.mk.test.moviedbapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMovieListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        return new JSONAsyncTaskLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                String title = jsonArray.getJSONObject(i).getString("title");
                String releaseDate = jsonArray.getJSONObject(i).getString("release_date");
                double popularity = jsonArray.getJSONObject(i).getDouble("popularity");
                int voteCount = jsonArray.getJSONObject(i).getInt("vote_count");
                String imageURL = jsonArray.getJSONObject(i).getString("backdrop_path");
                int id = jsonArray.getJSONObject(i).getInt("id");
                boolean video = jsonArray.getJSONObject(i).getBoolean("video");
                boolean adult = jsonArray.getJSONObject(i).getBoolean("adult");
                String overview = jsonArray.getJSONObject(i).getString("overview");

                Movie movie = new Movie(title, releaseDate, popularity, voteCount, imageURL, id, video, adult, overview);
                movieList.add(movie);
            }

            mMovieListAdapter = new MovieListAdapter(this, movieList);
            mRecyclerView.setAdapter(mMovieListAdapter);

        } catch (JSONException e) {
            Log.e("JSON", "JSON Exception " + e);
        }
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        //
    }
}
