package com.example.hiltmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hiltmvvm.core.model.Comments
import com.example.hiltmvvm.databinding.CommentItemBinding

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    class CommentViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var diffUtil = object : DiffUtil.ItemCallback<Comments>() {
        override fun areItemsTheSame(oldItem: Comments, newItem: Comments): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comments, newItem: Comments): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var differ = AsyncListDiffer(this, diffUtil)

    var commentList: List<Comments>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.binding.comment = comment
    }
}