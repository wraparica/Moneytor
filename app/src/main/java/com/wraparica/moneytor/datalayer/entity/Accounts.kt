package com.wraparica.moneytor.datalayer.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.wraparica.moneytor.constants.TableNames

@JsonClass(generateAdapter = true)
@Entity(tableName = TableNames.TABLE_ACCOUNTS)
data class Accounts @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Json(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "details")
    @Json(name = "details")
    var details: String?,
    @ColumnInfo(name = "amount")
    @Json(name = "amount")
    var amount: Double? = 0.0,
    @ColumnInfo(name = "status")
    @Json(name = "status")
    var status: Int = 1
){
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Accounts>() {
            override fun areItemsTheSame(
                oldItem: Accounts,
                newItem: Accounts):
                    Boolean {
                return oldItem.id == newItem.id
                        && oldItem.details == newItem.details
                        && oldItem.amount == newItem.amount
                        && oldItem.status == newItem.status
            }

            override fun areContentsTheSame(
                oldItem: Accounts,
                newItem: Accounts):
                    Boolean {
                return oldItem.id == newItem.id
                        && oldItem.details == newItem.details
                        && oldItem.amount == newItem.amount
                        && oldItem.status == newItem.status
            }

        }
    }
}