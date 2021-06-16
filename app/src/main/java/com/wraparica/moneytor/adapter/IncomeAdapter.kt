package com.wraparica.moneytor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wraparica.moneytor.R
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.datalayer.entity.Income
import com.wraparica.moneytor.utils.DateUtils

class IncomeAdapter( private val context: Context) :
    ListAdapter<Income, IncomeListViewHolder>(Income.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeListViewHolder =
        IncomeListViewHolder.from(parent)

    override fun onBindViewHolder(holder: IncomeListViewHolder, position: Int) =
        holder.bind(getItem(position), context)


    override fun getItem(pos: Int): Income? {
        return super.getItem(pos)
    }

    fun getItemData(pos: Int) : Income? {
        return getItem(pos)
    }

}

class IncomeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
    private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    private val tvAccountName: TextView = itemView.findViewById(R.id.tvAccountName)
    private val tvDetails: TextView = itemView.findViewById(R.id.tvDetails)
    fun bind(
        item: Income?, context: Context) {
        item ?: return
        val amount = "Php " + item.amount.toString()
        tvAmount.text = amount
        tvDate.text = DateUtils.convert(
            DateUtils.TIMESTAMP_DATE,
            DateUtils.DATE,
            item.date
        )
        tvAccountName.text = MoneytorDatabase.get(context).accountsDao().getAccountNameById(item.accountId)
        tvDetails.text = item.details
    }

    companion object {
        fun from(parent: ViewGroup): IncomeListViewHolder =
            IncomeListViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_income_list,
//                    R.layout.item_account_card_view,
                    parent,
                    false
                )
            )
    }
}