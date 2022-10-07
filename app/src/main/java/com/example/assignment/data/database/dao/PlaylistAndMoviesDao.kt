package com.example.assignment.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.data.model.entity.Movie
import com.example.assignment.data.model.entity.Playlist
import com.example.assignment.data.model.entity.PlaylistAndMovie

@Dao
interface PlaylistAndMoviesDao {
    @Insert
    fun insertMovieIntoPlaylist(playlistAndMovie: PlaylistAndMovie)

    @Query("SELECT * FROM PlaylistAndMovies")
    fun getMoviesForPlaylists():List<PlaylistAndMovie>
}