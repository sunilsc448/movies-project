package com.example.assignment.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.data.model.entity.Movie
import com.example.assignment.data.model.entity.Playlist

@Dao
interface PlaylistsDao {
    @Insert
    fun insertPlaylist(playlist: Playlist)

    @Query("SELECT * FROM Playlists")
    fun getPlaylists():List<Playlist>
}