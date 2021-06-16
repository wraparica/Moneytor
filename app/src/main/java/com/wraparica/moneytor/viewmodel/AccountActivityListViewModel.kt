package com.wraparica.moneytor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wraparica.moneytor.datalayer.`object`.AccountActivity
import com.wraparica.moneytor.helper.ExpenseHelper
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.datalayer.entity.Expenses
import com.wraparica.moneytor.helper.AccountActivityHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AccountActivityListViewModel(app: Application) : AndroidViewModel(app) {
    private val app = app
    private var job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val db: MoneytorDatabase = MoneytorDatabase.get(app)

    private val helper = AccountActivityHelper(app)

    fun getLiveAccountActivity(): LiveData<List<AccountActivity>> {
        return helper.observableItems
    }
    fun postAccountId(id: Long) {
        helper.accountId = id
    }
}