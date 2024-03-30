package com.hobarb.molio.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hobarb.molio.R
import com.hobarb.molio.models.TitlesModel


class TitlesAdapter(
    private val titlesList: List<TitlesModel>,
) : RecyclerView.Adapter<TitlesAdapter.TitlesViewHolder>() {


    inner class TitlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_tv: TextView = itemView.findViewById(R.id.title_tv);
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
        return titlesList.size
    }

    override fun onBindViewHolder(holder: TitlesViewHolder, position: Int) {
        val item = titlesList[position]
        holder.title_tv.text = "" + titlesList[position].title
    }
}








