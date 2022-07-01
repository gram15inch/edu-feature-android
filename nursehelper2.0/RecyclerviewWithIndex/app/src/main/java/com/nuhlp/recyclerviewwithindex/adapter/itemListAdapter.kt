package com.nuhlp.recyclerviewwithindex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.recyclerviewwithindex.data.doc
import com.nuhlp.recyclerviewwithindex.databinding.ItemListItemBinding

class ItemListAdapter (private val onItemClicked: (doc) -> Unit) :
    ListAdapter<doc, ItemListAdapter.ItemViewHolder>(DiffCallback){

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<doc>() {
            override fun areItemsTheSame(oldDoc: doc, newDoc: doc): Boolean {
                return oldDoc == newDoc
            }

            override fun areContentsTheSame(oldDoc: doc, newDoc: doc): Boolean {
                return oldDoc.docTitle == newDoc.docTitle
            }
        }
    }

    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doc: doc) {
            binding.apply {
                title.text = doc.docTitle
                content.text = doc.docContents
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.
            inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }
}