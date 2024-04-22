package com.hobarb.molia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.hobarb.molia.R
import com.hobarb.molia.interfaces.OnItemClickListener


class SubCollectionsAdapter(
    private val listener: OnItemClickListener,
    private val subCollectionList: JsonArray,
) : RecyclerView.Adapter<SubCollectionsAdapter.SubCollectionViewHolder>() {


    inner class SubCollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSubCollection: TextView = itemView.findViewById(R.id.tvSubCollection);
        val llSubCollection: LinearLayout = itemView.findViewById(R.id.llSubCollection);
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCollectionViewHolder {
        return SubCollectionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_sub_collection_title, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return subCollectionList.size()
    }

    override fun onBindViewHolder(holder: SubCollectionViewHolder, position: Int) {
        val item = subCollectionList[position]
        holder.tvSubCollection.text = item.asString
        holder.llSubCollection.setOnClickListener {
            listener.onSubCollectionItemClick(item)
        }
    }
}








