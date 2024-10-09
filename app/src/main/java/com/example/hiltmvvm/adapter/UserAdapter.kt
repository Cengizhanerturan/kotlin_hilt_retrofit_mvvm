package com.example.hiltmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hiltmvvm.core.model.Users
import com.example.hiltmvvm.databinding.UserItemBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var onClickItem: ((Users) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var differ = AsyncListDiffer(this, diffUtil)

    var userList: List<Users>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    fun setOnClick(listener: (Users) -> Unit) {
        onClickItem = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.user = user

        holder.itemView.setOnClickListener {
            onClickItem?.let {
                it(user)
            }
        }
    }
}