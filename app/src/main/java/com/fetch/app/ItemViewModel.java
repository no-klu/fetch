package com.fetch.app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();

    public LiveData<List<Item>> getItems() {
        return itemsLiveData;
    }

    public void setItems(List<Item> items) {
        itemsLiveData.setValue(items);
    }
}
