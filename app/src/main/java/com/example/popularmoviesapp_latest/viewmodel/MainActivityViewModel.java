package com.example.popularmoviesapp_latest.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.popularmoviesapp_latest.data.MovieResponse;
import com.example.popularmoviesapp_latest.data.Movies;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private final MoviesRepository moviesRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.moviesRepository = new MoviesRepository(application);
    }

    public LiveData<MovieResponse> getPopularMoviesResponse() {
        return moviesRepository.getPopularMoviesResponse();
    }

    public LiveData<MovieResponse> getTopratedMoviesResponse(){
        return moviesRepository.getTopratedMoviesResponse();
    }

    public LiveData<List<Movies>> getFavoriteMovies(){
        return moviesRepository.getFavoriteMovies();
    }
}
