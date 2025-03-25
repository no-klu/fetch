package com.fetch.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Map<Integer, List<Item>> groupedItems;

    public ItemAdapter(Map<Integer, List<Item>> groupedItems) {
        this.groupedItems = groupedItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int listId = (int) groupedItems.keySet().toArray()[position];
        List<Item> items = groupedItems.get(listId);

        holder.listIdTextView.setText("List ID: " + listId);
        StringBuilder names = new StringBuilder();
        for (Item item : items) {
            names.append(item.getName()).append("\n");
        }
        holder.namesTextView.setText(names.toString());
    }

    @Override
    public int getItemCount() {
        return groupedItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView listIdTextView;
        TextView namesTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            listIdTextView = itemView.findViewById(R.id.listIdTextView);
            namesTextView = itemView.findViewById(R.id.namesTextView);
        }
    }
}
