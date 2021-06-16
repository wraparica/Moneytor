package com.wraparica.moneytor.helper;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.wraparica.moneytor.datalayer.database.MoneytorDatabase;
import com.wraparica.moneytor.datalayer.entity.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeHelper {

    private MoneytorDatabase db;
    private MediatorLiveData<List<Income>> items = new MediatorLiveData<>();
    private LiveData<List<Income>> liveItems;

    private MediatorLiveData<List<String>> accountNames = new MediatorLiveData<>();
    private LiveData<List<String>> liveAccountNames;

    public IncomeHelper(Context context) {
        this.db = MoneytorDatabase.get(context);
        refreshList();
    }
    public void refreshList() {
        this.items.removeSource(liveItems);
        this.liveItems = db.incomeDao()
                .getAllIncome();
        this.items.addSource(liveItems, list -> items.postValue(list));

        this.accountNames.removeSource(liveAccountNames);
        this.liveAccountNames = db.incomeDao()
                .getAccountNames();
        this.accountNames.addSource(liveAccountNames, list -> accountNames.postValue(list));
    }

    public List<Income> getItems() {
        List<Income> items = this.items.getValue();
        return items == null ? new ArrayList<>() : items;
    }

    public LiveData<List<Income>> getObservableItems() {
        return items;
    }

    public LiveData<List<String>> getObservableAccountNames() {
        return accountNames;
    }
}

