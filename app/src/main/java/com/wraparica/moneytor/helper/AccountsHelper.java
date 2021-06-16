package com.wraparica.moneytor.helper;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.wraparica.moneytor.datalayer.database.MoneytorDatabase;
import com.wraparica.moneytor.datalayer.entity.Accounts;

import java.util.ArrayList;
import java.util.List;

public class AccountsHelper {

    private MoneytorDatabase db;
    private MediatorLiveData<List<Accounts>> items = new MediatorLiveData<>();
    private LiveData<List<Accounts>> liveItems;

    public AccountsHelper(Context context) {
        this.db = MoneytorDatabase.get(context);
        refreshList();
    }
    public void refreshList() {
        this.items.removeSource(liveItems);

        this.liveItems = db.accountsDao()
                .getAccounts();

        this.items.addSource(liveItems, list -> items.postValue(list));
    }

    public List<Accounts> getItems() {
        List<Accounts> items = this.items.getValue();
        return items == null ? new ArrayList<>() : items;
    }

    public LiveData<List<Accounts>> getObservableItems() {
        return items;
    }
}

