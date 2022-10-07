package com.example.assignment.data.repository

import android.accounts.NetworkErrorException
import android.database.sqlite.SQLiteConstraintException
import com.example.assignment.data.api.ApiService
import com.example.assignment.data.database.MoviesRoomDataBase
import com.example.assignment.data.model.MoviesResponse
import com.example.assignment.data.model.Status
import com.example.assignment.data.model.entity.Movie
import com.example.assignment.data.model.entity.Playlist
import com.example.assignment.data.model.entity.PlaylistAndMovie
import com.example.assignment.utils.RemoteMoviesDataResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: ApiService,
                                           private val roomDataBase: MoviesRoomDataBase) {
    suspend fun getMovies(page:Int, dispatcher: CoroutineDispatcher): RemoteMoviesDataResponse {
        return withContext(dispatcher) {
            try{
                val data = apiService.getMovies(page)
                addMoviesToDB(dispatcher, data.movies)
                RemoteMoviesDataResponse.SuccessResponse(data = data.movies)
            }catch (throwable: Throwable){
                when(throwable){
                    is NetworkErrorException, is UnknownHostException ->
                        RemoteMoviesDataResponse.ErrorResponse(status =  Status.NETWORK_ERROR)
                    else -> RemoteMoviesDataResponse.ErrorResponse(status = Status.API_ERROR)
                }
            }
        }
    }

    suspend fun getPlaylists(dispatcher: CoroutineDispatcher): List<Playlist> {
        return withContext(dispatcher) {
            roomDataBase.playlistsDao().getPlaylists()
        }
    }

    suspend fun addToPlaylist(dispatcher: CoroutineDispatcher, name:String){
        withContext(dispatcher) {
            roomDataBase.playlistsDao().insertPlaylist(Playlist(name))
        }
    }

    private suspend fun addMoviesToDB(dispatcher: CoroutineDispatcher, movies: List<Movie>){
        withContext(dispatcher) {
            try {
                roomDataBase.moviesDao().insertAllPopularMovies(movies)
            }catch(exception:java.lang.Exception){}
        }
    }

    suspend fun addMovieToPlaylist(dispatcher: CoroutineDispatcher,movie: Movie, playlist: Playlist){
        withContext(dispatcher){
            try {
                val playlistAndMovie = PlaylistAndMovie(playlist.id, movie.id)
                roomDataBase.playlistAndMoviesDao().insertMovieIntoPlaylist(playlistAndMovie)
            }catch(exception:Exception){}
        }
    }

    suspend fun getMoviesForPlaylist(dispatcher: CoroutineDispatcher,playlist: Playlist):List<Movie>{
        return withContext(dispatcher){
            try {
                val savedMoviesForPlaylists = roomDataBase.playlistAndMoviesDao().getMoviesForPlaylists()
                val filteredPlaylist = savedMoviesForPlaylists.filter { it.playlist_id ==  playlist.id}
                val movies = fetchMoviesForFilteredPlaylist(dispatcher, filteredPlaylist)
                movies
            }catch(exception:Exception){
                emptyList()
            }
        }
    }

    private suspend fun fetchMoviesForFilteredPlaylist(dispatcher: CoroutineDispatcher,
                                               filteredPlaylist: List<PlaylistAndMovie>): List<Movie> {
        return withContext(dispatcher){
            val movies = roomDataBase.moviesDao().getPopularMovies()
            val resultMovies = mutableListOf<Movie>()
            movies.forEach { movieItem ->
                filteredPlaylist.forEach{
                    if(it.movie_id == movieItem.id){
                        resultMovies.add(movieItem)
                    }
                }
            }
            resultMovies
//            val result = movies.flatMap{ item -> filteredPlaylist.flatMap { item2 ->  arrayListOf(item.id item2.movie_id)}}
//            result
        }
    }
}