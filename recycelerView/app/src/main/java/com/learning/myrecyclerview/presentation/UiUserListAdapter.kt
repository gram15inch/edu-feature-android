package com.learning.myrecyclerview.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learning.myrecyclerview.UiUser
import com.learning.myrecyclerview.databinding.LayoutHolderUiUserBinding

class UiUserListAdapter :ListAdapter<UiUser, UiUserListAdapter.MarsPhotoViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<UiUser>() {
        override fun areItemsTheSame(oldItem: UiUser, newItem: UiUser): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: UiUser, newItem: UiUser): Boolean {
            return oldItem.name == newItem.name
        }
    }

    class MarsPhotoViewHolder (private var binding: LayoutHolderUiUserBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UiUser){
            binding.user = user
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MarsPhotoViewHolder {
        return MarsPhotoViewHolder(LayoutHolderUiUserBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

}