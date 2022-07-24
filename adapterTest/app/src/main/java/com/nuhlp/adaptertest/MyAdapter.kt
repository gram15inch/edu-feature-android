package com.nuhlp.adaptertest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.adaptertest.databinding.MyholderBinding
import java.text.SimpleDateFormat

class MyAdapter
    : ListAdapter<MyAdapter.Schedule, MyAdapter.MyViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }
    data class Schedule(val id:Int)

    class MyViewHolder(private var binding: MyholderBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = MyViewHolder(
            MyholderBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

}