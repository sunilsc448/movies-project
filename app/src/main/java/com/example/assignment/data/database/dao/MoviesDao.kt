package com.example.assignment.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.data.model.entity.Movie

@Dao
interface MoviesDao {
    @Insert
    fun insertPopularMovie(movie:Movie)

    @Insert
    fun insertAllPopularMovies(movies: List<Movie>)

    @Query("DELETE FROM Movies")
    fun deleteAllMovies()

    @Query("SELECT * FROM Movies")
    fun getPopularMovies():List<Movie>
}