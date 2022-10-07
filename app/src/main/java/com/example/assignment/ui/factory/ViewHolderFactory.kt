package com.example.assignment.ui.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.ui.viewModel.MoviesViewModel
import com.example.assignment.ui.viewholder.RecyclerBindingVH

object ViewHolderFactory {
    fun getViewHolder(resourceId: Int, parent: ViewGroup): RecyclerView.ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding = DataBindingUtil.inflate(inflater, resourceId, parent, false)
        return RecyclerBindingVH(dataBinding)
    }
    fun bindViewHolder(resourceId: Int, holder: RecyclerView.ViewHolder, obj: Any?, viewModel: MoviesViewModel){
        if(obj == null)return
        when(resourceId){
            R.layout.item_movie, R.layout.item_playlist -> {
                (holder as RecyclerBindingVH).bind(obj, viewModel)
            }
            else -> {
                // for other future implementing views
            }
        }
    }
}