package com.hobarb.molia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hobarb.molia.models.schemas.SearchedTitle
import com.hobarb.molia.R
import com.hobarb.molia.interfaces.OnItemClickListener


class SearchedTitlesAdapter(
    var context: Context,
    private val listener: OnItemClickListener,
    private val titlesList: List<SearchedTitle>,
) : RecyclerView.Adapter<SearchedTitlesAdapter.TitlesViewHolder>() {


    inner class TitlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster);
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle);
        val tvYear: TextView = itemView.findViewById(R.id.tvYear);
        val tvType: TextView = itemView.findViewById(R.id.tvType);
        val llTitle: LinearLayout = itemView.findViewById(R.id.llTitle)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitlesViewHolder {
        return TitlesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_searched_title,
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
        holder.tvTitle.text = item.Title
        holder.tvYear.text = item.Year
        holder.tvType.text = item.Type
        Glide
            .with(context)
            .load(item.Poster)
            .centerCrop()
            .placeholder(R.drawable.ic_loader)
            .into(holder.ivPoster);

        holder.llTitle.setOnClickListener {
            listener.onSearchedTitleItemClick(item)
        }
    }
}








