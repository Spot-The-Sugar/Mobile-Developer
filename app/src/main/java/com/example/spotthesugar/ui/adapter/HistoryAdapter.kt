package com.example.spotthesugar.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotthesugar.R
import com.example.spotthesugar.data.source.response.DataItem
import com.example.spotthesugar.ui.history.HistoryDetailActivity
import org.w3c.dom.Text

class HistoryAdapter(private var histories:List<DataItem>)
    :RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val productImg: ImageView = itemView.findViewById(R.id.productImg)
        val productNameTextView: TextView = itemView.findViewById(R.id.productNameView)
        val sugarBtnView:TextView = itemView.findViewById(R.id.sugarBtn)
        val categoryText: TextView = itemView.findViewById(R.id.categoriesBtn)
    }

    fun setData(newHistories: List<DataItem>){
        histories = newHistories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return histories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = histories[position]
        Glide.with(holder.itemView.context)
            .load(history.imageUrl)
            .into(holder.productImg)
        holder.productNameTextView.text = history.productName
        holder.sugarBtnView.text = "Sugar " + history.totalSugar.toString() + "g"
        holder.categoryText.text = history.productGrade

        holder.itemView.setOnClickListener {
            Log.d("HistoryAdapter", "Clicked item at position: $position")
            Log.d("HistoryAdapter", "History ID: ${history.scanId}")
            val intent = Intent(holder.itemView.context,HistoryDetailActivity::class.java).apply {
                putExtra(HistoryDetailActivity.EXTRA_HISTORY_ID,history.scanId)
            }
            holder.itemView.context.startActivity(intent)
        }
    }
}