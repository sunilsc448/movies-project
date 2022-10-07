package com.example.assignment.ui.viewModel

import androidx.lifecycle.*
import com.example.assignment.data.model.Status
import com.example.assignment.data.model.entity.Movie
import com.example.assignment.data.model.entity.Playlist
import com.example.assignment.data.repository.MoviesRepository
import com.example.assignment.ui.utils.SingleLiveEvent
import com.example.assignment.utils.RemoteMoviesDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEBOUNCE_PERIOD = 2000L
class MoviesViewModel(private val mainRepository: MoviesRepository): ViewModel() {
    /** For Pagination */
    private var page = 0
    var isLastPage:Boolean = false
        private set
    var blockContinuousPagination = false
        private set

    private val response:MutableLiveData<List<Movie>> = MutableLiveData()
    fun getResponse():LiveData<List<Movie>> = response
    private val dataStatus:MutableLiveData<Status> = MutableLiveData()
    fun getDataStatus():LiveData<Status> {
        return dataStatus
    }

    private var filterClicked:MutableLiveData<Unit> = SingleLiveEvent()
    fun getFilterClicked():LiveData<Unit>{
        return filterClicked
    }

    private var allPlayListClicked:MutableLiveData<Unit> = SingleLiveEvent()
    fun getAllPLayListClicked():LiveData<Unit>{
        return allPlayListClicked
    }

    private var selectedMovie:Movie? = null

    init {
        dataStatus.value = Status.EMPTY
        fetchMovies()
    }

    private fun fetchMovies(){
        page++
        dataStatus.value = Status.LOADING
        viewModelScope.launch{
            when(val response = mainRepository.getMovies(page, Dispatchers.IO)){
                is RemoteMoviesDataResponse.SuccessResponse -> {
                    val data = response.data
                    handleSuccessData(data)
                }
                is RemoteMoviesDataResponse.ErrorResponse ->{
                    handleErrorData(response)
                }
            }
        }
    }

    private fun handleErrorData(response: RemoteMoviesDataResponse.ErrorResponse) {
        page--
        when (response.status) {
            Status.NETWORK_ERROR -> dataStatus.value = Status.NETWORK_ERROR
            else -> dataStatus.value = Status.API_ERROR
        }
    }

    private fun handleSuccessData(data: List<Movie>) {
        dataStatus.value = Status.SUCCESS
        if(data.isNotEmpty()) {
            /** Pagination support
             * Check for already present data and stitch the new data to it*/
            if(response.value != null && response.value!!.isNotEmpty()){
                val newList:MutableList<Movie> = mutableListOf()
                newList.addAll(response.value!!)
                newList.addAll(data)
                response.value = newList
            }else{
                response.value = data
            }
        }else{
            /** Turn off Pagination support
             *  If data is empty for any particular page*/
            page--
            isLastPage = true
            if(response.value == null || response.value!!.isEmpty())
                dataStatus.value = Status.EMPTY
        }
    }

    fun loadMore(){
        /** blockContinuousLoad blocks the back to back pagination calls */
        if(!blockContinuousPagination) {
            blockContinuousPagination = true
            fetchMovies()
            viewModelScope.launch {
                delay(DEBOUNCE_PERIOD)
                blockContinuousPagination = false
            }
        }
    }

    fun onFilterIconClicked(){
        filterClicked.postValue(Unit)
    }

    fun onMovieStarClicked(movie: Movie){
        selectedMovie = movie
        filterClicked.postValue(Unit)
    }

    fun onPlayListClicked(playList: Playlist){
        viewModelScope.launch {
            if(selectedMovie != null){
                mainRepository.addMovieToPlaylist(Dispatchers.IO, selectedMovie!!, playList)
            }else{
                allPlayListClicked.postValue(Unit)
                val movies = mainRepository.getMoviesForPlaylist(Dispatchers.IO, playList)
                if(movies.isEmpty()){
                    dataStatus.value = Status.EMPTY
                }else{
                    dataStatus.value = Status.SUCCESS
                }
                isLastPage = true
                blockContinuousPagination = true
                response.value = movies
            }
        }

        dropSelectedMovie()
    }

    fun onAllPlayListClicked(){
        allPlayListClicked.postValue(Unit)
        reset()
        fetchMovies()
        dropSelectedMovie()
    }

    fun dropSelectedMovie(){
        selectedMovie = null
    }

    fun reset(){
        dataStatus.value = Status.EMPTY
        page=0
        isLastPage = false
        blockContinuousPagination = false
    }
}