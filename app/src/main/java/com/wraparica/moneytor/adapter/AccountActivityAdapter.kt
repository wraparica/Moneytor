package com.wraparica.moneytor.adapter

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wraparica.moneytor.R
import com.wraparica.moneytor.datalayer.`object`.AccountActivity
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.datalayer.entity.Expenses
import com.wraparica.moneytor.utils.DateUtils

class AccountsActivityAdapter( private val context: Context) :
    ListAdapter<AccountActivity, AccountsActivityListViewHolder>(AccountActivity.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsActivityListViewHolder =
        AccountsActivityListViewHolder.from(parent)

    override fun onBindViewHolder(holder: AccountsActivityListViewHolder, position: Int) =
        holder.bind(getItem(position), context)


    override fun getItem(pos: Int): AccountActivity? {
        return super.getItem(pos)
    }

    fun getItemData(pos: Int) : AccountActivity? {
        return getItem(pos)
    }

}

class AccountsActivityListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
    private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    private val tvAccountName: TextView = itemView.findViewById(R.id.tvAccountName)
    private val tvDetails: TextView = itemView.findViewById(R.id.tvDetails)
    private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)

    fun bind(
        item: AccountActivity?, context: Context) {
        item ?: return

        when (item.txnType){
            1 -> {
                ivIcon.setImageResource(R.drawable.ic_income)
                ivIcon.setBackgroundResource(R.drawable.bg_circle_icon_primary)
                tvAccountName.setTextColor(Color.parseColor("#03DAC5"))
            }

            2 -> {
                ivIcon.setImageResource(R.drawable.ic_expenses)
                ivIcon.setBackgroundResource(R.drawable.bg_circle_icon_red)
                tvAccountName.setTextColor(Color.parseColor("#EF5350"))
            }
        }

        val amount = "Php " + item.amount.toString()
        tvAmount.text = amount
        tvDate.text = DateUtils.convert(
            DateUtils.TIMESTAMP_DATE,
            DateUtils.DATE,
            item.date
        )
//        tvAccountName.text = MoneytorDatabase.get(context).accountsDao().getAccountNameById(item.accountId)
        tvAccountName.text = item.accountName
        tvDetails.text = item.details
    }

    companion object {
        fun from(parent: ViewGroup): AccountsActivityListViewHolder =
            AccountsActivityListViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_account_activity_list,
//                    R.layout.item_account_card_view,
                    parent,
                    false
                )
            )
    }
}