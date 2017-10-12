package com.mk.test.moviedbapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Michael on 10/11/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>{

    Context context;
    private ArrayList<Movie> mMovieList;

    public MovieListAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        mMovieList = movieList;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.title.setText(mMovieList.get(position).getTitle());
        viewHolder.releaseDate.setText(mMovieList.get(position).getReleaseDate());
        viewHolder.popularity.setText(String.valueOf(mMovieList.get(position).getPopularity()));

        String imageURL = "https://image.tmdb.org/t/p/w500/" + mMovieList.get(position).getImageURL();
        new DownloadImageTask((ImageView) viewHolder.movieImage).execute(imageURL);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MovieDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("title", mMovieList.get(position).getTitle());
                bundle.putString("releaseDate", mMovieList.get(position).getReleaseDate());
                bundle.putDouble("popularity", mMovieList.get(position).getPopularity());
                bundle.putInt("votesCount", mMovieList.get(position).getVotesCount());
                bundle.putString("imageURL", mMovieList.get(position).getImageURL());
                bundle.putInt("id", mMovieList.get(position).getId());
                bundle.putBoolean("video", mMovieList.get(position).getVideo());
                bundle.putBoolean("adult", mMovieList.get(position).getAdult());
                bundle.putString("overview", mMovieList.get(position).getOverview());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;

        public TextView title;
        public TextView releaseDate;
        public TextView popularity;
        public ImageView movieImage;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);

            title = (TextView) view.findViewById(R.id.title);
            releaseDate = (TextView) view.findViewById(R.id.release_date);
            popularity = (TextView) view.findViewById(R.id.popularity);

            movieImage = (ImageView) view.findViewById(R.id.movieImage);
        }
    }


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

}
