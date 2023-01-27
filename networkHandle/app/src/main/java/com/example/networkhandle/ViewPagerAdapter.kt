package com.example.networkhandle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.networkhandle.databinding.LayoutVpItemBinding

class ViewPagerAdapter(idolList: ArrayList<Int>) :
    RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var item = idolList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder(
        LayoutVpItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(item[position])
    }

    inner class PagerViewHolder(var binding: LayoutVpItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(res: Int) {
            binding.apply {
                itemImg.setImageResource(res)
            }
        }
    }
}