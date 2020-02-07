package com.example.popularmoviesapp_latest.retrofit;

import com.example.popularmoviesapp_latest.data.MovieResponse;
import com.example.popularmoviesapp_latest.data.ReviewResponse;
import com.example.popularmoviesapp_latest.data.TrailerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getVideos(@Path("id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReviews(@Path("id") String movieId, @Query("api_key") String apiKey);
}
