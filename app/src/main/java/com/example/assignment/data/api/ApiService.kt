package com.example.assignment.data.api

import com.example.assignment.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMovies(@Query("page")page: Int): MoviesResponse
}