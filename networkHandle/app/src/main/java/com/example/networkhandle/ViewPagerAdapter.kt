package com.example.networkhandle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.networkhandle.databinding.LayoutVpItemBnBinding
import com.example.networkhandle.databinding.LayoutVpItemMgBinding

class ViewPagerAdapter(idolList: ArrayList<Int>,val type: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var item = idolList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        return if(type ==1)
        BannerPagerViewHolder(
            LayoutVpItemBnBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) else MarginPagerViewHolder(
            LayoutVpItemMgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 10000

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pos = position % item.size
        if(type==1)
            (holder as BannerPagerViewHolder).bind(item[pos])
        else
            (holder as MarginPagerViewHolder).bind(item[pos])
    }

    inner class BannerPagerViewHolder(var binding: LayoutVpItemBnBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(res: Int) {
            binding.apply {
                itemImg.setImageResource(res)
            }
        }
    }
    inner class MarginPagerViewHolder(var binding :LayoutVpItemMgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(res: Int) {
            binding.apply {
                itemImg.setImageResource(res)
            }
        }
    }
}