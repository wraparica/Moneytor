package com.wraparica.moneytor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wraparica.moneytor.helper.ExpenseHelper
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.datalayer.entity.Expenses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ExpenseListViewModel(app: Application) : AndroidViewModel(app) {
    private val app = app
    private var job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val db: MoneytorDatabase = MoneytorDatabase.get(app)

    private val helper = ExpenseHelper(app)

    fun getLiveExpenses(): LiveData<List<Expenses>> {
        return helper.observableItems
    }
    fun getLiveAccountNames(): LiveData<List<String>> {
        return helper.observableAccountNames
    }
}