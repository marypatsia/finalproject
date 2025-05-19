package com.example.movie.models1;

import com.google.gson.annotations.SerializedName;

public class Movie {

    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private float voteAverage;

    // Getters
    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getPosterPath() { return posterPath; }

    public String getOverview() { return overview; }

    public String getReleaseDate() { return releaseDate; }

    public float getVoteAverage() { return voteAverage; }
}

