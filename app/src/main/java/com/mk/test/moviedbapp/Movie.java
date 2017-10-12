package com.mk.test.moviedbapp;

/**
 * Created by Michael on 10/10/2017.
 */

public class Movie {

    private String title;
    private String releaseDate;
    private double popularity;
    private int votesCount;
    private String imageURL;

    private int id;
    private boolean video;
    private boolean adult;
    private String overview;

    public Movie(String title, String releaseDate, double popularity, int votesCount, String imageURL, int id, boolean video, boolean adult, String overview) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.votesCount = votesCount;
        this.imageURL = imageURL;
        this.id = id;
        this.video = video;
        this.adult = adult;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean getVideo() {
        return video;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }
}
