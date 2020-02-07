package com.example.popularmoviesapp_latest.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.popularmoviesapp_latest.data.Movies;
import com.example.popularmoviesapp_latest.data.ReviewResponse;
import com.example.popularmoviesapp_latest.data.TrailerResponse;

public class DetailActivityViewModel extends AndroidViewModel {
    private static final String TAG = "DetailActivityViewModel";

    private final MoviesRepository moviesRepository;

    public DetailActivityViewModel(@NonNull Application application) {
        super(application);
        this.moviesRepository=new MoviesRepository(application);
    }

    public LiveData<ReviewResponse> getReviewResponse(String movieId){
        return moviesRepository.getReviewResponse(movieId);
    }

    public LiveData<TrailerResponse> getTrailerResponse(String movieId){
        return moviesRepository.getTrailerResponse(movieId);
    }

    public Movies getFavoriteMovieById(String movieId){
        return moviesRepository.getFavoriteMovieByMovieId(movieId);
    }

}
