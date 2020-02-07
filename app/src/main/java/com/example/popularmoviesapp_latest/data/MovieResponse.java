package com.example.popularmoviesapp_latest.data;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MovieResponse {
    @Expose
    private Integer page;
    @Expose
    private Integer totalResults;
    @Expose
    private Integer totalPages;
    @Expose
    private List<Movies> results = null;

    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
        this.results = results;
    }
}
