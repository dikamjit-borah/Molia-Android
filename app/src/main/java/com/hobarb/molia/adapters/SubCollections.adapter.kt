package com.hobarb.molia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.hobarb.molia.R
import com.hobarb.molia.interfaces.OnItemClickListener
import java.util.Locale


class SubCollectionsAdapter(
    private val listener: OnItemClickListener,
    private var subCollectionList: JsonArray,
) : RecyclerView.Adapter<SubCollectionsAdapter.SubCollectionViewHolder>(), Filterable {


    private val subCollectionListCopy: JsonArray = subCollectionList
    private lateinit var subCollectionListFiltered: JsonArray

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""

                if (charString.isEmpty()) subCollectionListFiltered = subCollectionListCopy else {
                    val filteredList = JsonArray()
                    for (subCollection in subCollectionListCopy) {
                        if (subCollection.asString.lowercase(Locale.ROOT)
                                .contains(charString.lowercase(Locale.ROOT))
                        ) {
                            filteredList.add(subCollection)
                        }
                    }

                    subCollectionListFiltered = filteredList

                }
                return FilterResults().apply { values = subCollectionListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                subCollectionList = if (results?.values == null)
                    JsonArray()
                else
                    results.values as JsonArray
                notifyDataSetChanged()
                //listener.onSubCollectionItemSearch(constraint)
            }
        }
    }

}








