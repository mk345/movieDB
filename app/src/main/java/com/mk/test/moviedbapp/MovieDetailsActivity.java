package com.mk.test.moviedbapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by Michael on 10/10/2017.
 */

public class MovieDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap>{

    ImageView movieImageIV;
    TextView titleTV;
    TextView releaseDateTV;
    TextView popularityTV;
    TextView votesCountTV;
    TextView idTV;
    TextView videoTV;
    TextView adultTV;
    TextView overviewTV;

    String title;
    String releaseDate;
    Double popularity;
    int votesCount;
    String imageURL;
    int id;
    boolean video;
    boolean adult;
    String overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieImageIV = (ImageView) findViewById(R.id.movieImage);
        titleTV = (TextView) findViewById(R.id.title);
        releaseDateTV = (TextView) findViewById(R.id.release_date);
        popularityTV = (TextView) findViewById(R.id.popularity);
        votesCountTV = (TextView) findViewById(R.id.votes_count);
        idTV = (TextView) findViewById(R.id.id);
        videoTV = (TextView) findViewById(R.id.video);
        adultTV = (TextView) findViewById(R.id.adult);
        overviewTV = (TextView) findViewById(R.id.overview);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            title = bundle.getString("title");
            releaseDate = bundle.getString("releaseDate");
            popularity = bundle.getDouble("popularity");
            votesCount = bundle.getInt("votesCount");
            imageURL = "https://image.tmdb.org/t/p/w500/" + bundle.getString("imageURL");
            id = bundle.getInt("id");
            video = bundle.getBoolean("video");
            adult = bundle.getBoolean("adult");
            overview = bundle.getString("overview");

            //Log.e("TEST", imageURL);
            //movieImageIV.set


        }

        titleTV.setText(title);
        releaseDateTV.setText(releaseDate);
        popularityTV.setText(popularity.toString());
        votesCountTV.setText(String.valueOf(votesCount));
        idTV.setText(String.valueOf(id));
        if (video) {
            videoTV.setText("Video");
        } else {
            videoTV.setText("Non Video");
        }
        if (adult) {
            adultTV.setText("Adult");
        } else {
            adultTV.setText("Non Adult");
        }
        overviewTV.setText(String.valueOf(overview));

        //new DownloadImageTask((ImageView) findViewById(R.id.movieImage)).execute(imageURL);

        getSupportLoaderManager().initLoader(1, null, this).forceLoad();


    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new ImageAsyncTaskLoader(MovieDetailsActivity.this, imageURL);
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
        movieImageIV.setImageBitmap(bitmap);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {
        //
    }
/*
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    */
}
