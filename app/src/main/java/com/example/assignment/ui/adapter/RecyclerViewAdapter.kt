package com.example.assignment.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.model.entity.Movie
import com.example.assignment.data.model.entity.Playlist
import com.example.assignment.ui.factory.ViewHolderFactory
import com.example.assignment.ui.viewModel.MoviesViewModel

class RecyclerViewAdapter(private var mList:List<Any>?, private val viewModel: MoviesViewModel):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        if(mList == null)return -1
        return when (mList!![position]) {
            is Movie -> {
                R.layout.item_movie
            }
            is Playlist -> {
                R.layout.item_playlist
            }
            else->{
                throw IllegalArgumentException("Layout is missing for the drawing view holder")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.getViewHolder(viewType, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ViewHolderFactory.bindViewHolder(getItemViewType(position), holder, mList?.get(position), viewModel)
    }

    override fun getItemCount() = mList?.size ?: 0

    fun updateData(list:List<Any>?){
        val prevCount:Int = itemCount
        mList = list
        notifyItemRangeChanged(prevCount, itemCount)
    }
}