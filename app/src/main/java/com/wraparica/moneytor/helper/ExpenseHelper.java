package com.wraparica.moneytor.helper;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.wraparica.moneytor.datalayer.database.MoneytorDatabase;
import com.wraparica.moneytor.datalayer.entity.Expenses;

import java.util.ArrayList;
import java.util.List;

public class ExpenseHelper {

    private MoneytorDatabase db;
    private MediatorLiveData<List<Expenses>> items = new MediatorLiveData<>();
    private LiveData<List<Expenses>> liveItems;

    private MediatorLiveData<List<String>> accountNames = new MediatorLiveData<>();
    private LiveData<List<String>> liveAccountNames;

    public ExpenseHelper(Context context) {
        this.db = MoneytorDatabase.get(context);
        refreshList();
    }
    public void refreshList() {
        this.items.removeSource(liveItems);
        this.liveItems = db.expensesDao()
                .getAllExpenses();
        this.items.addSource(liveItems, list -> items.postValue(list));

        this.accountNames.removeSource(liveAccountNames);
        this.liveAccountNames = db.expensesDao()
                .getAccountNames();
        this.accountNames.addSource(liveAccountNames, list -> accountNames.postValue(list));
    }

    public List<Expenses> getItems() {
        List<Expenses> items = this.items.getValue();
        return items == null ? new ArrayList<>() : items;
    }

    public LiveData<List<Expenses>> getObservableItems() {
        return items;
    }

    public LiveData<List<String>> getObservableAccountNames() {
        return accountNames;
    }
}

