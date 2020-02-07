package com.example.popularmoviesapp_latest.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmoviesapp_latest.BuildConfig;
import com.example.popularmoviesapp_latest.data.MovieResponse;
import com.example.popularmoviesapp_latest.data.Movies;
import com.example.popularmoviesapp_latest.data.ReviewResponse;
import com.example.popularmoviesapp_latest.data.TrailerResponse;
import com.example.popularmoviesapp_latest.database.MovieDatabase;
import com.example.popularmoviesapp_latest.retrofit.RetrofitClient;
import com.example.popularmoviesapp_latest.retrofit.RetrofitInterface;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {
    private static final String TAG = "MoviesRepository";

    private final RetrofitInterface mRetrofitInterface;
    private Application application;
    private MovieDatabase mMovieDatabase;

    public MoviesRepository(Application application){
        this.application=application;
        mMovieDatabase=MovieDatabase.getInstance(application);
        mRetrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);

    }

    public LiveData<TrailerResponse> getTrailerResponse(String movieId){
        final MutableLiveData<TrailerResponse> trailerResponseMutableLiveData=new MutableLiveData<>();
        Call<TrailerResponse> call= mRetrofitInterface.getVideos(movieId, BuildConfig.API_KEY);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if(response.isSuccessful()){
                    TrailerResponse trailerResponse=response.body();
                    trailerResponseMutableLiveData.setValue(trailerResponse);
                }
            }
            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                trailerResponseMutableLiveData.setValue(null);
                Log.e(TAG, "Failed getting TrailerResponse: " + t.getMessage());
            }
        });

        return trailerResponseMutableLiveData;
    }

    public LiveData<ReviewResponse> getReviewResponse(String movieId){
        final MutableLiveData<ReviewResponse> reviewResponseMutableLiveData=new MutableLiveData<>();
        Call<ReviewResponse> call= mRetrofitInterface.getReviews(movieId,BuildConfig.API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()){
                    ReviewResponse reviewResponse=response.body();
                    reviewResponseMutableLiveData.setValue(reviewResponse);
                }
            }
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                reviewResponseMutableLiveData.setValue(null);
                Log.e(TAG, "Failed getting ReviewResponse: " + t.getMessage());
            }
        });
        return reviewResponseMutableLiveData;
    }

    public LiveData<MovieResponse> getPopularMoviesResponse(){
        final MutableLiveData<MovieResponse> popularMoviesResponseLiveData=new MutableLiveData<>();
        Call<MovieResponse> call= mRetrofitInterface.getPopularMovies(BuildConfig.API_KEY,1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse=response.body();
                popularMoviesResponseLiveData.setValue(movieResponse);

            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                popularMoviesResponseLiveData.setValue(null);
                Log.e(TAG, "Failed getting ReviewResponse: " + t.getMessage());
            }
        });
        return popularMoviesResponseLiveData;
    }

    public LiveData<MovieResponse> getTopratedMoviesResponse(){
        final MutableLiveData<MovieResponse> topratedMoviesResponseLiveData=new MutableLiveData<>();
        Call<MovieResponse> call= mRetrofitInterface.getTopRatedMovies(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse=response.body();
                topratedMoviesResponseLiveData.setValue(movieResponse);
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                topratedMoviesResponseLiveData.setValue(null);
                Log.e(TAG, "Failed getting ReviewResponse: " + t.getMessage());
            }
        });
        return topratedMoviesResponseLiveData;
    }

    public LiveData<List<Movies>> getFavoriteMovies() {
        return mMovieDatabase.movieDao().loadAllMovies();
    }

    public Movies getFavoriteMovieByMovieId(String movieId) {
        return mMovieDatabase.movieDao().loadMovieById(movieId);
    }

}
