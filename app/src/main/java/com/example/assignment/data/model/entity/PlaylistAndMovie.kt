package com.example.assignment.data.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PlaylistAndMovies")
data class PlaylistAndMovie(
    @PrimaryKey(autoGenerate = true) @NonNull val id: Int,
    @ColumnInfo(name = "playlist_id") val playlist_id: Int,
    @ColumnInfo(name = "movie_id") val movie_id: Int){
    constructor(playlistId: Int, movieId: Int) : this(0, playlistId, movieId)
}

