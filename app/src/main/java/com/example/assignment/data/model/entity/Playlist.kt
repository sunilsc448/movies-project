package com.example.assignment.data.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Playlists")
data class Playlist(
    @PrimaryKey(autoGenerate = true) @NonNull val id: Int,
    @ColumnInfo(name = "title") val title: String?){
    constructor(name: String) : this(0, name)
}