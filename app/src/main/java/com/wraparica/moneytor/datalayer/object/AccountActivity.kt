package com.wraparica.moneytor.datalayer.`object`

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo

data class AccountActivity @JvmOverloads constructor(
    @ColumnInfo(name = "txn_type")
    var txnType: Int,
    @ColumnInfo(name = "details")
    var details: String?,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "account_id")
    var accountId: Long? = 0,
    @ColumnInfo(name = "account_name")
    var accountName: String? ,
    @ColumnInfo(name = "amount")
    var amount: Double? = 0.0
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AccountActivity>() {
            override fun areItemsTheSame(
                oldItem: AccountActivity,
                newItem: AccountActivity
            ):
                    Boolean {
                return oldItem.txnType == newItem.txnType
                        && oldItem.details == newItem.details
                        && oldItem.amount == newItem.amount
                        && oldItem.date == newItem.date
                        && oldItem.accountId == newItem.accountId
                        && oldItem.accountName == newItem.accountName
            }

            override fun areContentsTheSame(
                oldItem: AccountActivity,
                newItem: AccountActivity
            ):
                    Boolean {
                return oldItem.txnType == newItem.txnType
                        && oldItem.details == newItem.details
                        && oldItem.amount == newItem.amount
                        && oldItem.date == newItem.date
                        && oldItem.accountId == newItem.accountId
                        && oldItem.accountName == newItem.accountName
            }

        }
    }
}