package com.wraparica.moneytor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wraparica.moneytor.helper.AccountsHelper
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.datalayer.entity.Accounts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AccountsListViewModel(app: Application) : AndroidViewModel(app) {
    private val app = app
    private var job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    private val db: MoneytorDatabase = MoneytorDatabase.get(app)

    private val helper = AccountsHelper(app)

    fun getLiveSoSHeader(): LiveData<List<Accounts>> {
        return helper.observableItems
        //shareOfShelfProvider.getLiveSoSHeader(ownerId)
//        return Transformations.map(db.formTransactionsDao().getLiveAttendanceFormItems(attendanceId), Function {
//            return@Function getAttendanceForms(it)
//        })
    }
}