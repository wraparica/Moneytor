package com.wraparica.moneytor.helper;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.wraparica.moneytor.datalayer.database.MoneytorDatabase;
import com.wraparica.moneytor.datalayer.entity.Expenses;
import com.wraparica.moneytor.datalayer.object.AccountActivity;

import java.util.ArrayList;
import java.util.List;

public class AccountActivityHelper {

    private MoneytorDatabase db;
    private MediatorLiveData<List<AccountActivity>> items = new MediatorLiveData<>();
    private LiveData<List<AccountActivity>> liveItems;

    private MediatorLiveData<List<String>> accountNames = new MediatorLiveData<>();
    private LiveData<List<String>> liveAccountNames;

    private MutableLiveData<Long> accountId = new MutableLiveData<>();
    public AccountActivityHelper(Context context) {
        this.db = MoneytorDatabase.get(context);
        items.addSource(accountId, it -> refreshList());
        refreshList();
    }
    public void refreshList() {
        this.items.removeSource(liveItems);
        this.liveItems = db.accountsDao()
                .getAccountActivities(getAccountId());
        this.items.addSource(liveItems, list -> items.postValue(list));
    }

    public List<AccountActivity> getItems() {
        List<AccountActivity> items = this.items.getValue();
        return items == null ? new ArrayList<>() : items;
    }

    public Long getAccountId() {
        Long value = this.accountId.getValue();
        return value == null ? 0 : value;
    }

    public void setAccountId(Long accountId) {
        this.accountId.postValue(accountId);
    }

    public LiveData<List<AccountActivity>> getObservableItems() {
        return items;
    }

    public LiveData<List<String>> getObservableAccountNames() {
        return accountNames;
    }
}

