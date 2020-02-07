package com.example.popularmoviesapp_latest.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movies{
    private static final String TAG = "Movies";

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String poster_path;
    private String overview;
    private String vote_average;
    private String release_date;

    public Movies(String id, String title, String poster_path, String overview, String vote_average, String release_date) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }
}
