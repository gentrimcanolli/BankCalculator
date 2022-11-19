package com.example.bankcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class TableRowAdapter(private var paymentArrayList: ArrayList<Payment>) :
    RecyclerView.Adapter<TableRowAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var idTv: TextView
        var paymentAmountTv: TextView
        var interestAmountTv: TextView
        var balanceOwnedTv: TextView
        var principalAmountTv: TextView

        init {
            idTv = view.findViewById(R.id.id_tv)
            paymentAmountTv = view.findViewById(R.id.payment_amount_tv)
            interestAmountTv = view.findViewById(R.id.interest_amount_tv)
            balanceOwnedTv = view.findViewById(R.id.balance_owned_tv)
            principalAmountTv = view.findViewById(R.id.principal_amount_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTv.text = paymentArrayList[position].id.toString()
        holder.paymentAmountTv.text = paymentArrayList[position].paymentAmount.toString()
        holder.interestAmountTv.text = paymentArrayList[position].interestAmount.toString()
        holder.balanceOwnedTv.text = paymentArrayList[position].balanceOwned.toString()
        holder.principalAmountTv.text = paymentArrayList[position].principalAmount.toString()
    }

    override fun getItemCount(): Int = paymentArrayList.size
}