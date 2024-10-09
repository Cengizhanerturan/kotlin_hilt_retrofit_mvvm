package com.example.hiltmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hiltmvvm.core.model.Photos
import com.example.hiltmvvm.databinding.PhotoItemBinding

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    class PhotoViewHolder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Photos>() {
        override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var differ = AsyncListDiffer(this, diffUtil)

    var photoList: List<Photos>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photoList[position]
        holder.binding.photo = photo
    }
}