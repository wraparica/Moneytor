package com.wraparica.moneytor.datalayer.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wraparica.moneytor.datalayer.`object`.AccountActivity
import com.wraparica.moneytor.datalayer.entity.Accounts

@Dao
interface AccountsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpdateAccounts(tagging: Accounts?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpdateAccounts(taggings: List<Accounts?>?)

    @Query("SELECT (COALESCE(MAX(id), 0) + 1) FROM txn_accounts ")
    fun getNextId(): Long

    @Query("SELECT details FROM txn_accounts ORDER BY details")
    fun getAccountName(): List<String>

    @Query("SELECT * FROM txn_accounts")
    fun getAccounts(): LiveData<List<Accounts?>?>?

    @Query("SELECT id FROM txn_accounts WHERE details = :accountName")
    fun getAccountID(accountName : String): Long?

    @Query("SELECT details FROM txn_accounts WHERE id = :id")
    fun getAccountNameById(id : Long?): String?

    @Query("UPDATE txn_accounts SET amount = amount + :income WHERE id = :id")
    fun updateAccountAddIncome(id : Long?, income : Double?)

    @Query("UPDATE txn_accounts SET amount = amount - :expenses WHERE id = :id")
    fun updateAccountDeductExpense(id : Long?, expenses : Double?)


    @Query("SELECT * FROM txn_accounts")
    fun checkHasAccounts() : Boolean

    @Query("SELECT 1 as txn_type, income.details, income.date, income.account_id, accounts.details as account_name, income.amount FROM txn_income income JOIN txn_accounts accounts " +
            "ON income.account_id = accounts.id " +
            " WHERE income.account_id = :accountId " +
            "UNION ALL " +
            "SELECT 2 as txn_type, expenses.details, expenses.date, expenses.account_id, accounts.details as account_name, expenses.amount FROM txn_expenses expenses JOIN txn_accounts accounts " +
            "ON expenses.account_id = accounts.id " +
            " WHERE expenses.account_id = :accountId ORDER BY date DESC")
    fun getAccountActivities(accountId : Long) : LiveData<List<AccountActivity?>?>?



}