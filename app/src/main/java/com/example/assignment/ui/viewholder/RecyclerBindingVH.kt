package com.example.assignment.ui.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.BR
import com.example.assignment.data.model.entity.Movie
import com.example.assignment.data.model.entity.Playlist
import com.example.assignment.ui.viewModel.MoviesViewModel

class RecyclerBindingVH(private val binding: ViewDataBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Any?, viewModel: MoviesViewModel){
        when(item){
            is Movie, is Playlist -> {
                binding.setVariable(BR.item, item)
                binding.setVariable(BR.viewModel, viewModel)
            }else -> {
            //else if for other future implementing views
            }
        }
    }
}