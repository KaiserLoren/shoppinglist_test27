package com.example.shoppinglist_test27;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;

    public ShoppingListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("list_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("list_name"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("list_date"));

            holder.tvListName.setText(name);
            holder.tvListDate.setText(date);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ItemsActivity.class);
                intent.putExtra("listId", id);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvListName, tvListDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListName = itemView.findViewById(R.id.tvListName);
            tvListDate = itemView.findViewById(R.id.tvListDate);
        }
    }
}
