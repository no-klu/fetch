package com.fetch.app;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getItems().observe(this, items -> {
            if (items != null) {
                adapter = new ItemAdapter(groupAndSortItems(items));
                recyclerView.setAdapter(adapter);
            }
        });

        fetchData();
    }

    private void fetchData() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Item>> call = apiService.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    viewModel.setItems(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("MainActivity", "Error fetching data", t);
            }
        });
    }

    private Map<Integer, List<Item>> groupAndSortItems(List<Item> items) {
        // Filter out items with blank or null names
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName() != null && !item.getName().trim().isEmpty()) {
                filteredItems.add(item);
            }
        }

        // Sort by id
        Collections.sort(filteredItems, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if (o1.getId() == o2.getId()) {
                    return o1.getName().compareTo(o2.getName());
                }
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        // Group by listId
        Map<Integer, List<Item>> groupedItems = new HashMap<>();
        for (Item item : filteredItems) {
            if (!groupedItems.containsKey(item.getListId())) {
                groupedItems.put(item.getListId(), new ArrayList<>());
            }
            groupedItems.get(item.getListId()).add(item);
        }

        return groupedItems;
    }
}