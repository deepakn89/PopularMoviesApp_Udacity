package com.example.popularmoviesapp_latest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.popularmoviesapp_latest.data.Movies;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("select * from movies")
    LiveData<List<Movies>> loadAllMovies();

    @Insert
    void insertMovie(Movies movies);

    @Delete
    void deleteMovie(Movies movies);

    @Query("SELECT * FROM movies WHERE id = :movieId")
    Movies loadMovieById(String movieId);
}
