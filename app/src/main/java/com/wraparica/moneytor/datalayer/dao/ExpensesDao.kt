package com.wraparica.moneytor.datalayer.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wraparica.moneytor.datalayer.entity.Expenses
import com.wraparica.moneytor.datalayer.entity.Income

@Dao
interface ExpensesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpdateExpenses(tagging: Expenses?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpdateExpenses(tagging: List<Expenses?>?)

    @Query("SELECT * FROM txn_expenses ORDER BY date DESC")
    fun getAllExpenses(): LiveData<List<Expenses?>?>?

    @Query("SELECT accounts.details FROM txn_income income JOIN txn_accounts accounts ON income.account_id = accounts.id ORDER BY income.date DESC")
    fun getAccountNames(): LiveData<List<String?>?>?

    @Query("SELECT (COALESCE(MAX(id), 0) + 1) FROM txn_expenses ")
    fun getNextId(): Long
//    @Query("SELECT * FROM txn_gps_tagging WHERE id = :id")
//    fun getById(id: Long): StoreGpsTagging?
//
//

}