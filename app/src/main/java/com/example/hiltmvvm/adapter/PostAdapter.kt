package com.example.hiltmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hiltmvvm.core.model.Posts
import com.example.hiltmvvm.databinding.PostItemBinding

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    class PostViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var onClickItem: ((Posts) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var differ = AsyncListDiffer(this, diffUtil)

    var postList: List<Posts>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    fun setOnClickItem(listener: (Posts) -> Unit) {
        onClickItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.binding.post = post

        holder.itemView.setOnClickListener {
            onClickItem?.let {
                it(post)
            }
        }
    }
}