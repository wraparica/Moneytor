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
import com.wraparica.moneytor.datalayer.entity.Accounts

class AccountsAdapter(private val listener: OnItemClickedListener) :
    ListAdapter<Accounts, AccountsListViewHolder>(Accounts.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsListViewHolder =
        AccountsListViewHolder.from(parent)

    override fun onBindViewHolder(holder: AccountsListViewHolder, position: Int) =
        holder.bind(getItem(position), position, listener)


    override fun getItem(pos: Int): Accounts? {
        return super.getItem(pos)
    }

    fun getItemData(pos: Int) : Accounts? {
        return getItem(pos)
    }

    interface OnItemClickedListener {
        fun onItemClicked(account: Accounts)
    }

}

class AccountsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
    private val tvDetails: TextView = itemView.findViewById(R.id.tvDetails)

    fun bind(
        item: Accounts?,
        pos: Int,
        listener: AccountsAdapter.OnItemClickedListener) {
        item ?: return
        val amount = "Php " + item.amount.toString()
        tvAmount.text = amount
        tvDetails.text = item.details

        itemView.setOnClickListener { v: View? ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onItemClicked(item)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): AccountsListViewHolder =
            AccountsListViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_account_card_view,
                    parent,
                    false
                )
            )
    }
}