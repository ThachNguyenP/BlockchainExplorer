package com.example.blockexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BlockAdapter(private val listener: (Block) -> Unit) : RecyclerView.Adapter<BlockAdapter.ViewHolderItem>() {
    val items = mutableListOf<Block>()

    inner class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(block: Block) {
            itemView.findViewById<TextView>(R.id.tv_height).text = block.height.toString()
            itemView.findViewById<TextView>(R.id.tv_timestamp).text = ApiHelper.getDate(block.timestamp)
            itemView.findViewById<TextView>(R.id.tv_transaction).text = block.transaction.toString()
            itemView.findViewById<TextView>(R.id.tv_size).text = block.size.toString()
            itemView.findViewById<TextView>(R.id.tv_weight).text = block.weight.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        return ViewHolderItem(
            LayoutInflater.from(parent.context).inflate(R.layout.block_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }
}