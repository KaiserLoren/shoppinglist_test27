package com.example.shoppinglist_test27;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;
    private DatabaseHelper dbHelper;

    public ItemsAdapter(Context context, Cursor cursor, DatabaseHelper dbHelper) {
        this.context = context;
        this.cursor = cursor;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("item_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
            boolean isChecked = cursor.getInt(cursor.getColumnIndexOrThrow("is_checked")) == 1;

            holder.cbItem.setChecked(isChecked);
            holder.tvItemName.setText(name);

            holder.cbItem.setOnCheckedChangeListener((buttonView, isChecked1) ->
                    dbHelper.updateItemChecked(id, isChecked1));
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbItem;
        TextView tvItemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbItem = itemView.findViewById(R.id.cbItem);
            tvItemName = itemView.findViewById(R.id.tvItemName);
        }
    }
}
