package com.example.assignment.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.data.model.entity.Playlist
import com.example.assignment.data.repository.MoviesRepository
import com.example.assignment.ui.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(private val mainRepository: MoviesRepository) :ViewModel(){
    private val response: MutableLiveData<List<Playlist>> = MutableLiveData()
    fun getResponse(): LiveData<List<Playlist>> = response

    private var addPlayListClicked:MutableLiveData<Unit> = SingleLiveEvent()
    fun getAddPLayListClicked():LiveData<Unit>{
        return addPlayListClicked
    }

    init {
        fetchPlaylists()
    }

    private fun fetchPlaylists() {
        viewModelScope.launch{
            val data = mainRepository.getPlaylists(Dispatchers.IO)
            handleResponse(data)
        }
    }

    private fun handleResponse(data: List<Playlist>) {
        response.postValue(data)
    }

    fun onAddPlayListClicked(){
        addPlayListClicked.postValue(Unit)
    }

    fun addItemToPlaylist(playlistName: String) {
        viewModelScope.launch {
            mainRepository.addToPlaylist(Dispatchers.IO, playlistName)
            val data = mainRepository.getPlaylists(Dispatchers.IO)
            handleResponse(data)
        }
    }
}