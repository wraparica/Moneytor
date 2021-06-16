package com.wraparica.moneytor.datalayer.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.wraparica.moneytor.constants.TableNames

@JsonClass(generateAdapter = true)
@Entity(tableName = TableNames.TABLE_INCOME)
data class Income @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Json(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "details")
    @Json(name = "details")
    var details: String?,
    @ColumnInfo(name = "date")
    @Json(name = "date")
    var date: String,
    @ColumnInfo(name = "account_id")
    @Json(name = "account_id")
    var accountId: Long? = 0,
    @ColumnInfo(name = "amount")
    @Json(name = "amount")
    var amount: Double? = 0.0,
    @ColumnInfo(name = "status")
    @Json(name = "status")
    var status: Int = 1
){
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Income>() {
            override fun areItemsTheSame(
                oldItem: Income,
                newItem: Income
            ):
                    Boolean {
                return oldItem.id == newItem.id
                        && oldItem.details == newItem.details
                        && oldItem.amount == newItem.amount
                        && oldItem.status == newItem.status
                        && oldItem.date == newItem.date
                        && oldItem.accountId == newItem.accountId
            }

            override fun areContentsTheSame(
                oldItem: Income,
                newItem: Income
            ):
                    Boolean {
                return oldItem.id == newItem.id
                        && oldItem.details == newItem.details
                        && oldItem.amount == newItem.amount
                        && oldItem.status == newItem.status
                        && oldItem.date == newItem.date
                        && oldItem.accountId == newItem.accountId
            }

        }
    }
}