package com.example.assignment.ui.binding

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.api.POSTER_IMAGE_PATH_PREFIX
import com.example.assignment.data.model.Status
import com.example.assignment.data.model.entity.Movie
import com.example.assignment.data.model.entity.Playlist
import com.example.assignment.ui.adapter.RecyclerViewAdapter
import com.example.assignment.ui.utils.PaginationScrollListener
import com.example.assignment.ui.viewModel.MoviesViewModel
import com.squareup.picasso.Picasso

object BindingDataAdapter {
    @BindingAdapter("viewModel", "movies")
    @JvmStatic
    fun bindMovies(recyclerView: RecyclerView, viewModel: MoviesViewModel?, movies:List<Movie>?){
        if (viewModel != null && movies != null) {
            var adapter = recyclerView.adapter
            if (adapter == null){
                adapter = RecyclerViewAdapter(movies, viewModel)
                recyclerView.adapter = adapter

                val linearLayoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                recyclerView.addOnScrollListener(object: PaginationScrollListener(linearLayoutManager) {
                    override fun loadMoreItems(){
                        viewModel.loadMore()
                    }

                    override fun isLastPage(): Boolean {
                        return viewModel.isLastPage
                    }

                    override fun isLoading(): Boolean {
                        return viewModel.blockContinuousPagination
                    }
                })
            }else{
                (adapter as RecyclerViewAdapter).updateData(movies)
            }
        }
    }

    @BindingAdapter("viewModel", "playlists")
    @JvmStatic
    fun bindPlaylists(recyclerView: RecyclerView, viewModel: MoviesViewModel?, playlists:List<Playlist>?){
        if (viewModel != null && playlists != null) {
            var adapter = recyclerView.adapter
            if (adapter == null){
                adapter = RecyclerViewAdapter(playlists, viewModel)
                recyclerView.adapter = adapter
            }else{
                (adapter as RecyclerViewAdapter).updateData(playlists)
            }
        }
    }

    @BindingAdapter("setProgressBar")
    @JvmStatic
    fun setProgressBarVisibility(progressBar: ProgressBar, dataStatus: Status){
        progressBar.visibility = when(dataStatus){
            Status.LOADING -> View.VISIBLE
            else ->  View.GONE
        }
    }

    @BindingAdapter("loadImage")
    @JvmStatic
    fun setImage(imageView: ImageView, url: String?) {
        url?.let {
            Picasso.with(imageView.context).load(POSTER_IMAGE_PATH_PREFIX+url).placeholder(R.drawable.placeholder_image).into(imageView)
        }
    }

    @BindingAdapter("rating")
    @JvmStatic
    fun setRatingText(textView: TextView, rating: Float?) {
        rating?.let {
            textView.text = String.format(textView.context.resources.getString(R.string.rating_text),rating.toString())
        }
    }

    @BindingAdapter("setDataStatusText", "list")
    @JvmStatic
    fun setDataStatusText(textView: TextView, dataStatus: Status, list: List<Movie>?){
        list?.let {
            if(it.isNotEmpty()){
                textView.visibility = View.GONE
                return
            }
        }
        val text = when(dataStatus){
            Status.LOADING -> {
                textView.visibility = View.VISIBLE
                textView.resources.getString(R.string.please_wait)
            }
            Status.NETWORK_ERROR ->
            {
                textView.visibility = View.VISIBLE
                textView.resources.getString(R.string.no_network_error_txt)
            }
            Status.API_ERROR ->
            {
                textView.visibility = View.VISIBLE
                textView.resources.getString(R.string.error_txt)
            }
            Status.EMPTY -> {
                textView.visibility = View.VISIBLE
                textView.resources.getString(R.string.no_items_text)
            }
            else -> {
                textView.visibility = View.GONE
                ""
            }
        }
        textView.text = text
    }
}