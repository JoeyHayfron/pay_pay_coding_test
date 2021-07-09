package com.example.paypay_coding_test.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paypay_coding_test.R

class RecyclerAdapter(val objectMap: Map<String, Any>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var rateCurrencyTextView: TextView = itemView.findViewById<TextView>(R.id.ratesCurrency)
        var rateAmountTextView: TextView = itemView.findViewById<TextView>(R.id.ratesAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rate_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ratesCurrency = objectMap.keys.toTypedArray()
        val ratesAmount = objectMap.values.toTypedArray() as Array<*>

        holder.rateAmountTextView.text = ratesAmount[position].toString()
        holder.rateCurrencyTextView.text = ratesCurrency[position]
    }

    override fun getItemCount(): Int {
        return objectMap.size
    }
}