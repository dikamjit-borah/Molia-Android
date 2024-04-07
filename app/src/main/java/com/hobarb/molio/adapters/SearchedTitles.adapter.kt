package com.hobarb.molio.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.hobarb.molio.R
import com.hobarb.molio.interfaces.OnItemClickListener
import com.hobarb.molio.models.schemas.SearchTitle


class SearchedTitlesAdapter<T>(
    private val listener: OnItemClickListener<T>,
    private val titlesList: JsonArray,
) : RecyclerView.Adapter<SearchedTitlesAdapter<T>.TitlesViewHolder>() {


    inner class TitlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_tv: TextView = itemView.findViewById(R.id.title_tv);
        val llTitle: LinearLayout = itemView.findViewById(R.id.ll_title)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitlesViewHolder {
        return TitlesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_title,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return titlesList.size()
    }

    override fun onBindViewHolder(holder: TitlesViewHolder, position: Int) {
        val item = titlesList[position]
        holder.title_tv.text = "" + titlesList[position]
        holder.llTitle.setOnClickListener {
            listener.onItemClick(item as T)
            titlesList.removeAll { true }
            notifyDataSetChanged()
        }
    }
}








