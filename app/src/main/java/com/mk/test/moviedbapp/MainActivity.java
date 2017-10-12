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

    //ItemClickListener itemClickListener;

    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();

        //Button button0 = (Button) findViewById(R.id.button0);
        //button0.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
/*
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            //@Override
            //public void onLongClick(View view, int position) {
            //    Toast.makeText(MainActivity.this, "Long press on position :"+position,
            //            Toast.LENGTH_LONG).show();
            //}

        }));
*/
        //initMovieList();

        //mMovieListAdapter = new MovieListAdapter(movieList);
        //mRecyclerView.setAdapter(mMovieListAdapter);

        //new JSONAsyncTaskLoader(this).onStartLoading();
        //String results = "";

        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }
/*
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button0) {
            Log.e("CLICKED", "clicked");

            Intent intent = new Intent(this, MovieDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("String1", "TestString12345");
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }
*/
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
/*
    private void initMovieList() {
        Movie movie1 = new Movie("A", "B", 3.3, 5, "C");
        movieList.add(movie1);
        Movie movie2 = new Movie("Aa", "Ba", 4.3, 6, "Ca");
        movieList.add(movie2);
    }



    public static class MovieItemOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            Movie movie = movieList.get(itemPosition);
            //Log.e("TEST", movie.getTitle());
        }
    }

    private void launchActivity() {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("String1", "TestString12345");
        intent.putExtras(bundle);

        startActivity(intent);
    }
    */
/*
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ItemClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ItemClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                //@Override
                //public void onLongPress(MotionEvent e) {
                //    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                //    if(child!=null && clicklistener!=null){
                //        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                //    }
                //}

            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            // Do Nothing
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            // Do Nothing
        }
    }
    */
}
